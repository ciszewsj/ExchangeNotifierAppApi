package ee.ciszewsj.exchangeRateNotfierServer.application.firestore.exchange;

import ee.ciszewsj.exchangeRateNotfierServer.data.ExchangeCurrencyRateEntity;

public interface ExchangeRateFirestoreInterface {
	void addExchangeRate(ExchangeCurrencyRateEntity exchangeCurrencyRate);
}
