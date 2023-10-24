package ee.ciszewsj.exchangeRateNotfierServer.application.service;

import ee.ciszewsj.exchangerateclient.client.ExchangeRateClient;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateDataException;
import ee.ciszewsj.exchangerateclient.data.response.HistoricalResponse;
import ee.ciszewsj.exchangerateclient.data.response.StandardResponse;
import ee.ciszewsj.exchangeratecommondata.dto.CurrencyEntity;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;
import ee.ciszewsj.exchangeratecommondata.repositories.currencies.CurrenciesRateFirestoreInterface;
import ee.ciszewsj.exchangeratecommondata.repositories.exchange.ExchangeRateFirestoreInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CurrencyUpdaterService {
	private final ExchangeRateClient exchangeRateClient;
	private final ExchangeRateFirestoreInterface exchangeRateDb;

	private List<String> activeCurrencySymbols = new ArrayList<>();

	public CurrencyUpdaterService(ExchangeRateClient exchangeRateClient,
	                              ExchangeRateFirestoreInterface exchangeRateDb,
	                              CurrenciesRateFirestoreInterface currenciesDb) {
		this.exchangeRateClient = exchangeRateClient;
		this.exchangeRateDb = exchangeRateDb;

		try {
			updateActiveCurrencySymbols(currenciesDb.getCurrencies());
		} catch (ExecutionException | InterruptedException e) {
			log.warn("Could not load currencies from DB due to {}", e, e);
		}
	}

	public void updateActiveCurrencySymbols(List<CurrencyEntity> currencyEntities) {
		activeCurrencySymbols = currencyEntities.stream().filter(CurrencyEntity::isMain).map(CurrencyEntity::getSymbol).collect(Collectors.toList());
		log.info("Set {} currencies as active ", activeCurrencySymbols.size());
	}

	public void updateAllActiveCurrencies() {
		activeCurrencySymbols.forEach(symbol -> {
			try {
				loadNewCurrencyExchangeRate(symbol);
			} catch (Exception e) {
				log.error("Could not load new currency exchange rate due to {}", e, e);
			}
		});
	}


	public StandardResponse loadNewCurrencyExchangeRate(String currencySymbol) throws ExchangeRateDataException {
		StandardResponse response = exchangeRateClient.standardRequest(currencySymbol);
		log.info("Loading currency {}...", currencySymbol);
		response.getConversionRates().forEach((key, value) -> {
			try {
				exchangeRateDb.addExchangeRate(response.getBaseCode(),
						key,
						ExchangeCurrencyRateEntity
								.builder()
								.date(new Date(response.getTimeLastUpdateUnix() * 1000))
								.rate(value)
								.build());
				log.debug("Successfully load currency {} - {}", currencySymbol, key);

			} catch (ExecutionException | InterruptedException e) {
				log.error("Could not add {} - {} exchange rate due to {}", response.getBaseCode(), key, e, e);
			}
		});

		return response;
	}

	public HistoricalResponse loadHistoricalCurrencyExchangeRate(String currencySymbol,
	                                                             int year,
	                                                             int month,
	                                                             int day) throws ExchangeRateDataException {
		HistoricalResponse response = exchangeRateClient.historicalDataRequest(currencySymbol, year, month, day);

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, response.getYear());
		calendar.set(Calendar.MONTH, response.getMonth() - 1);
		calendar.set(Calendar.DAY_OF_MONTH, response.getDay());
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);


		response.getConversionRates().forEach((key, value) -> {
			try {
				exchangeRateDb.addExchangeRate(response.getBaseCode(),
						key,
						ExchangeCurrencyRateEntity
								.builder()
								.date(new Date(calendar.getTimeInMillis()))
								.rate(value)
								.build());
			} catch (ExecutionException | InterruptedException e) {
				log.error("Could not add exchange rate due to {}", e, e);
			}

		});

		return response;
	}
}
