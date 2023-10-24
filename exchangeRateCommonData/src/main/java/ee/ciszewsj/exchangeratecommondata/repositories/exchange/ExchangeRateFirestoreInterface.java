package ee.ciszewsj.exchangeratecommondata.repositories.exchange;

import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;

import java.util.concurrent.ExecutionException;

public interface ExchangeRateFirestoreInterface {
	void addExchangeRate(String mainCurrency, String secondCurrency, ExchangeCurrencyRateEntity exchangeCurrencyRate) throws ExecutionException, InterruptedException;
}
