package ee.ciszewsj.exchangeratecommondata.repositories.exchange;

import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;

public interface ExchangeRateFirestoreInterface {
	void addExchangeRate(ExchangeCurrencyRateEntity exchangeCurrencyRate);
}
