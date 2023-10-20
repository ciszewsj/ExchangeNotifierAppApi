package ee.ciszewsj.exchangeRateNotfierServer.application.service;

import ee.ciszewsj.exchangeRateNotfierServer.application.firestore.exchange.ExchangeRateFirestoreInterface;
import ee.ciszewsj.exchangeRateNotfierServer.data.ExchangeCurrencyRateEntity;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateClient;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateDataException;
import ee.ciszewsj.exchangerateclient.data.response.HistoricalResponse;
import ee.ciszewsj.exchangerateclient.data.response.StandardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyUpdaterService {
	private final ExchangeRateClient exchangeRateClient;
	private final ExchangeRateEntityConverterService converterService;
	private final ExchangeRateFirestoreInterface exchangeRateDb;


	public ExchangeCurrencyRateEntity loadNewCurrencyExchangeRate(String currencySymbol) throws ExchangeRateDataException {
		StandardResponse response = exchangeRateClient.standardRequest(currencySymbol);

		ExchangeCurrencyRateEntity entity = converterService.convert(response);

		exchangeRateDb.addExchangeRate(entity);

		return entity;
	}

	public ExchangeCurrencyRateEntity loadHistoricalCurrencyExchangeRate(String currencySymbol,
	                                                                     int year,
	                                                                     int month,
	                                                                     int day) throws ExchangeRateDataException {
		HistoricalResponse response = exchangeRateClient.historicalDataRequest(currencySymbol, year, month, day);

		ExchangeCurrencyRateEntity entity = converterService.convert(response);

		exchangeRateDb.addExchangeRate(entity);

		return entity;
	}
}
