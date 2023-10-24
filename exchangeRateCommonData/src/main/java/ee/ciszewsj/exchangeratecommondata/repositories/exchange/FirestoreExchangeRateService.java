package ee.ciszewsj.exchangeratecommondata.repositories.exchange;


import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;
import ee.ciszewsj.exchangeratecommondata.repositories.FirestoreCollections;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FirestoreExchangeRateService implements ExchangeRateFirestoreInterface {
	private final Firestore firestore;
	private final String EXCHANGE_RATE_COLLECTION = FirestoreCollections.EXCHANGE_RATES.toString();

	@Override
	public void addExchangeRate(ExchangeCurrencyRateEntity exchangeCurrencyRate) {
		CollectionReference collection = firestore.collection(EXCHANGE_RATE_COLLECTION);
		collection.document(exchangeCurrencyRate.getUuid()).set(exchangeCurrencyRate);
	}
}
