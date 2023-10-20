package ee.ciszewsj.exchangeRateNotfierServer.application.firestore;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.database.annotations.NotNull;
import ee.ciszewsj.exchangeRateNotfierServer.data.CurrenciesSettingsEntity;
import ee.ciszewsj.exchangeRateNotfierServer.data.CurrencyEntity;
import ee.ciszewsj.exchangeRateNotfierServer.data.NotificationTypeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static ee.ciszewsj.exchangeRateNotfierServer.application.firestore.FirestoreStaticDocumentsIndex.CURRENCIES_SETTINGS;
import static ee.ciszewsj.exchangeRateNotfierServer.application.firestore.FirestoreStaticDocumentsIndex.NOTIFICATIONS_TYPE_DOCUMENT;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirestoreService implements FirestoreInterface {
	private final Firestore firestore;

	private final String COLLECTION = FirestoreCollections.SETTINGS.toString();
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
				.map(el -> CurrencyEntity.builder().symbol(el.get(1)).currency(el.get(0)).build())
				.filter(el -> !existingCurrencies.contains(el.getCurrency()))
				.forEach(el -> {
					DocumentReference newEntryRef = reference.document(el.getCurrency());
					newEntryRef.set(el);
				});
	}

	@Override
	public void updateNotifierSettings(List<NotificationTypeEntity> notificationTypeEntities) throws ExecutionException, InterruptedException {
		DocumentReference document = firestore.collection(COLLECTION).document(NOTIFICATIONS_TYPE_DOCUMENT);
		DocumentSnapshot future = document.get().get();

//		document.update()
	}

	@Override
	public void setCurrenciesIsMainVariable(String currency, boolean isMain) throws ExecutionException, InterruptedException {

	}

	private <T> Optional<T> getDocumentIfExist(String documentName, Class<T> className) throws ExecutionException, InterruptedException {
		DocumentReference document = firestore.collection(COLLECTION).document(documentName);
		ApiFuture<DocumentSnapshot> future = document.get();
		DocumentSnapshot snapshot = future.get();
		if (snapshot.exists()) {
			return Optional.ofNullable(snapshot.toObject(className));
		} else {
			return Optional.empty();
		}

	}

	private void saveData(@NotNull String collection, @NotNull String documentId, @NotNull Object data) {
		DocumentReference docRef = firestore.collection(collection).document(documentId);
		docRef.set(data);
	}
}
