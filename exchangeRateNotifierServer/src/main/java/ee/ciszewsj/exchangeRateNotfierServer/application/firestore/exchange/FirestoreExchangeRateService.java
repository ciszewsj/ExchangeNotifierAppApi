package ee.ciszewsj.exchangeRateNotfierServer.application.firestore.exchange;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import ee.ciszewsj.exchangeRateNotfierServer.application.firestore.FirestoreCollections;
import ee.ciszewsj.exchangeRateNotfierServer.data.ExchangeCurrencyRateEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
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
