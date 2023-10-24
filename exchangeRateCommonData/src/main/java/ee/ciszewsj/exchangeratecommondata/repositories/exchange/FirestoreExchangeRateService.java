package ee.ciszewsj.exchangeratecommondata.repositories.exchange;


import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
public record FirestoreExchangeRateService(Firestore firestore) implements ExchangeRateFirestoreInterface {
	private static final long yearInMillis = 31556952000L;

	@Override
	public void addExchangeRate(String mainCurrency, String secondCurrency, ExchangeCurrencyRateEntity exchangeCurrencyRate) throws ExecutionException, InterruptedException {
		CollectionReference collection = firestore.collection(mainCurrency);
		DocumentSnapshot document = collection.document(secondCurrency).get().get();

		if (document.exists()) {
			try {
				CurrencyExchangeRateDocument exchangeRateDocument = document.toObject(CurrencyExchangeRateDocument.class);
				List<ExchangeCurrencyRateEntity> rateEntities = Objects.requireNonNull(exchangeRateDocument).getExchangeRates().stream().filter(rate -> rate.getDate().getTime() >= (new Date().getTime() - yearInMillis)).collect(Collectors.toList());
				rateEntities.add(exchangeCurrencyRate);
				document.getReference().set(CurrencyExchangeRateDocument.builder().exchangeRates(rateEntities).build());
				return;
			} catch (Exception e) {
				log.error("Could not update currency exchange rate due to {}", e, e);
			}
		}
		document.getReference().set(CurrencyExchangeRateDocument.builder().exchangeRates(List.of(exchangeCurrencyRate)).build());
	}
}
