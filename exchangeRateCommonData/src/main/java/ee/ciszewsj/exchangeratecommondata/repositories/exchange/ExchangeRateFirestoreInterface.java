package ee.ciszewsj.exchangeratecommondata.repositories.exchange;

import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;

import java.util.concurrent.ExecutionException;

public interface ExchangeRateFirestoreInterface {
	CurrencyExchangeRateDocument addExchangeRate(String mainCurrency, String secondCurrency, ExchangeCurrencyRateEntity exchangeCurrencyRate) throws ExecutionException, InterruptedException;
}
