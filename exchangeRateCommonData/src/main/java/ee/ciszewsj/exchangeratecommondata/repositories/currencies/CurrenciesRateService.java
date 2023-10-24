package ee.ciszewsj.exchangeratecommondata.repositories.currencies;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import ee.ciszewsj.exchangeratecommondata.documents.CurrencyDocument;
import ee.ciszewsj.exchangeratecommondata.dto.CurrencyEntity;
import ee.ciszewsj.exchangeratecommondata.exceptions.WrongQuerySizeException;
import ee.ciszewsj.exchangeratecommondata.repositories.FirestoreCollections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CurrenciesRateService implements CurrenciesRateFirestoreInterface {
	private final Firestore firestore;

	private final String SETTINGS_COLLECTION = FirestoreCollections.SETTINGS.toString();

	private final String DOCUMENT_CURRENCY_NAME = "CURRENCIES";


	private DocumentSnapshot getDocument() throws ExecutionException, InterruptedException {
		DocumentReference reference = firestore.collection(SETTINGS_COLLECTION).document(DOCUMENT_CURRENCY_NAME);
		return reference.get().get();
	}

	@Override
	public void updateCurrenciesDocument(List<List<String>> currencies) throws ExecutionException, InterruptedException {
		DocumentSnapshot document = getDocument();

		List<CurrencyEntity> currencyEntities = currencies.stream()
				.filter(el -> el.size() == 2)
				.map(el -> CurrencyEntity.builder().symbol(el.get(0)).currency(el.get(1)).build())
				.collect(Collectors.toList());

		if (document.exists()) {
			try {
				CurrencyDocument currencyDocument = document.toObject(CurrencyDocument.class);
				if (currencyDocument == null) {
					throw new NullPointerException("Document is null");
				}
				List<String> newCurrSymbols = currencyEntities.stream().map(CurrencyEntity::getSymbol).collect(Collectors.toList());
				List<CurrencyEntity> newCurrencyEntityList =
						currencyDocument.getCurrenciesList().stream().filter(currency -> newCurrSymbols.contains(currency.getSymbol())).collect(Collectors.toList());
				List<String> addedCurrSymbols = newCurrencyEntityList.stream().map(CurrencyEntity::getSymbol).collect(Collectors.toList());
				currencyEntities.forEach(currency -> {
					if (!addedCurrSymbols.contains(currency.getSymbol())) {
						newCurrencyEntityList.add(currency);
					}
				});

				document.getReference().set(CurrencyDocument.builder().currenciesList(newCurrencyEntityList).build());
				return;
			} catch (Exception e) {
				log.error("Could not read CurrencyDocument: {}", e, e);
			}
		}

		document.getReference().set(CurrencyDocument.builder().currenciesList(currencyEntities).build());
	}

	@Override
	public void setCurrenciesIsMainVariable(Map<String, Boolean> props) throws ExecutionException, InterruptedException, WrongQuerySizeException {
		DocumentSnapshot document = getDocument();
		List<CurrencyEntity> currencyEntities = Objects.requireNonNull(document.toObject(CurrencyDocument.class)).getCurrenciesList();
		props.forEach((key, data) -> {
			Optional<CurrencyEntity> elem = currencyEntities.stream().filter(currencyEntity -> currencyEntity.getSymbol().equals(key)).findFirst();
			if (elem.isPresent()) {
				CurrencyEntity currency = elem.get();
				currencyEntities.remove(currency);
				currency.setMain(data);
				currencyEntities.add(currency);
			} else {
				log.error("Could not find element {} to set isMain to {}", key, data);
			}
		});
	}

	@Override
	public List<CurrencyEntity> getCurrencies() throws ExecutionException, InterruptedException {
		DocumentSnapshot document = getDocument();
		return Objects.requireNonNull(document.toObject(CurrencyDocument.class)).getCurrenciesList();
	}
}
