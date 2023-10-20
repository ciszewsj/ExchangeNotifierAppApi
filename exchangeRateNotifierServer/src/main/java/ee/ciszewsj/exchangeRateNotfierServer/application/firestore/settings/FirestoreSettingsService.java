package ee.ciszewsj.exchangeRateNotfierServer.application.firestore.settings;

import com.google.cloud.firestore.*;
import ee.ciszewsj.exchangeRateNotfierServer.application.firestore.FirestoreCollections;
import ee.ciszewsj.exchangeRateNotfierServer.application.firestore.WrongQuerySizeException;
import ee.ciszewsj.exchangeRateNotfierServer.data.CurrencyEntity;
import ee.ciszewsj.exchangeRateNotfierServer.data.NotificationTypeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static ee.ciszewsj.exchangeRateNotfierServer.data.CurrencyEntity.IS_MAIN;
import static ee.ciszewsj.exchangeRateNotfierServer.data.CurrencyEntity.SYMBOL;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirestoreSettingsService implements SettingsFirestoreInterface {
	private final Firestore firestore;

	private final String SETTINGS_COLLECTION = FirestoreCollections.SETTINGS.toString();
	private final String CURRENCIES_COLLECTION = FirestoreCollections.CURRENCIES.toString();

	public void updateCurrenciesList(List<List<String>> currencies) throws ExecutionException, InterruptedException {
		CollectionReference reference = firestore.collection(CURRENCIES_COLLECTION);
		QuerySnapshot query = reference.get().get();

		List<String> existingCurrencies = query.getDocumentChanges().stream()
				.map(document -> document.getDocument().toObject(CurrencyEntity.class))
				.map(CurrencyEntity::getCurrency)
				.collect(Collectors.toList());


		currencies.stream()
				.filter(el -> el.size() == 2)
				.map(el -> CurrencyEntity.builder().symbol(el.get(0)).currency(el.get(1)).build())
				.filter(el -> !existingCurrencies.contains(el.getCurrency()))
				.forEach(el -> {
					DocumentReference newEntryRef = reference.document(el.getCurrency());
					newEntryRef.set(el);
				});
	}

	@Override
	public void updateNotifierSettings(List<NotificationTypeEntity> notificationTypeEntities) throws ExecutionException, InterruptedException {
		CollectionReference collection = firestore.collection(SETTINGS_COLLECTION);
		QuerySnapshot querySnapshot = collection.get().get();
		querySnapshot.forEach(queryDocumentSnapshot -> queryDocumentSnapshot.getReference().delete());
		notificationTypeEntities.forEach(
				notificationTypeEntity -> collection.document(notificationTypeEntity.getTypeName()).set(notificationTypeEntity)
		);
	}

	@Override
	public void setCurrenciesIsMainVariable(String currency, boolean isMain) throws ExecutionException, InterruptedException, WrongQuerySizeException {
		CollectionReference reference = firestore.collection(CURRENCIES_COLLECTION);
		QuerySnapshot query = reference.whereEqualTo(SYMBOL, currency).get().get();
		if (query.isEmpty() || query.getDocuments().size() != 1) {
			throw new WrongQuerySizeException();
		}
		DocumentReference documentReference = query.getDocuments().get(0).getReference();
		documentReference.update(IS_MAIN, isMain);
	}
}
