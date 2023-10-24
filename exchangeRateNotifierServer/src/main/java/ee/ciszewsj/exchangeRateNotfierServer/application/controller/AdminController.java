package ee.ciszewsj.exchangeRateNotfierServer.application.controller;


import ee.ciszewsj.exchangeRateNotfierServer.application.service.CurrencyUpdaterService;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateClient;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateDataException;
import ee.ciszewsj.exchangerateclient.data.response.SupportedCodeResponse;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;
import ee.ciszewsj.exchangeratecommondata.exceptions.WrongQuerySizeException;
import ee.ciszewsj.exchangeratecommondata.repositories.currencies.CurrenciesRateFirestoreInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdminController {
	private final ExchangeRateClient exchangeRateClient;
	private final CurrenciesRateFirestoreInterface db;
	private final CurrencyUpdaterService updaterService;

	@GetMapping("/loadCurrenciesList")
	public SupportedCodeResponse forceLoadCurrenciesFromApi() throws ExchangeRateDataException, ExecutionException, InterruptedException {
		SupportedCodeResponse response = exchangeRateClient.supportedCodes();
		db.updateCurrenciesDocument(response.getSupportedCodes());
		return response;

	}

	@GetMapping("/changeCurrencyToMain")
	public void setCurrencyToMain() throws WrongQuerySizeException, ExecutionException, InterruptedException {
		db.setCurrenciesIsMainVariable(Map.of("USD", true));
	}

//	@GetMapping("/loadNotificationsSettings")
//	public void forceLoadNotificationSettings() throws ExecutionException, InterruptedException {
//		List<NotificationTypeEntity> notificationTypes = notifierService.getNotificationSettings();
//		db.updateNotifierSettings(notificationTypes);
//	}

	@GetMapping("/loadExchangeRate")
	public ExchangeCurrencyRateEntity forceLoadExchangeRate() throws ExchangeRateDataException {
		return updaterService.loadNewCurrencyExchangeRate("USD");
	}

	@GetMapping("/loadHistoricalExchangeRate")
	public ExchangeCurrencyRateEntity forceLoadHistoricalExchangeRate() throws ExchangeRateDataException {
		return updaterService.loadHistoricalCurrencyExchangeRate("USD", 2005, 6, 1);
	}
}
