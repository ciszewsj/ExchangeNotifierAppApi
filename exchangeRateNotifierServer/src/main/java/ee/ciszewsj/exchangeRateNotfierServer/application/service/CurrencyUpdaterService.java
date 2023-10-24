package ee.ciszewsj.exchangeRateNotfierServer.application.service;

import ee.ciszewsj.exchangerateclient.client.ExchangeRateClient;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateDataException;
import ee.ciszewsj.exchangerateclient.data.response.HistoricalResponse;
import ee.ciszewsj.exchangerateclient.data.response.StandardResponse;
import ee.ciszewsj.exchangeratecommondata.dto.CurrencyEntity;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;
import ee.ciszewsj.exchangeratecommondata.repositories.exchange.ExchangeRateFirestoreInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CurrencyUpdaterService {
	private final ExchangeRateClient exchangeRateClient;
	private final ExchangeRateEntityConverterService converterService;
	private final ExchangeRateFirestoreInterface exchangeRateDb;

	private final List<String> activeCurrencySymbols = new ArrayList<>();

	public CurrencyUpdaterService(ExchangeRateClient exchangeRateClient,
	                              ExchangeRateEntityConverterService converterService,
	                              ExchangeRateFirestoreInterface exchangeRateDb) {
		this.exchangeRateClient = exchangeRateClient;
		this.converterService = converterService;
		this.exchangeRateDb = exchangeRateDb;
	}


//	@EventListener(CurrencyChangedEvent.class)
//	public void ss(CurrencyChangedEvent event) {
//		CurrencyEntity entity = event.getEntity();
//		log.info("Received event [CurrencyChangedEvent] with body : [{}]", entity);
//		if (entity.isMain() && !activeCurrencySymbols.contains(entity.getCurrency())) {
//			activeCurrencySymbols.add(entity.getCurrency());
//		} else {
//			activeCurrencySymbols.remove(entity.getCurrency());
//		}
//	}

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
