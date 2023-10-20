package ee.ciszewsj.exchangeRateNotfierServer.application.controller;


import ee.ciszewsj.exchangeRateNotfierServer.application.firestore.exchange.ExchangeRateFirestoreInterface;
import ee.ciszewsj.exchangeRateNotfierServer.application.firestore.settings.SettingsFirestoreInterface;
import ee.ciszewsj.exchangeRateNotfierServer.application.firestore.WrongQuerySizeException;
import ee.ciszewsj.exchangeRateNotfierServer.application.service.ExchangeRateEntityConverterService;
import ee.ciszewsj.exchangeRateNotfierServer.application.service.notify.NotifierService;
import ee.ciszewsj.exchangeRateNotfierServer.data.ExchangeCurrencyRateEntity;
import ee.ciszewsj.exchangeRateNotfierServer.data.NotificationTypeEntity;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateClient;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateDataException;
import ee.ciszewsj.exchangerateclient.data.response.HistoricalResponse;
import ee.ciszewsj.exchangerateclient.data.response.StandardResponse;
import ee.ciszewsj.exchangerateclient.data.response.SupportedCodeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdminController {
	private final ExchangeRateClient exchangeRateClient;
	private final NotifierService notifierService;
	private final ExchangeRateEntityConverterService converterService;
	private final SettingsFirestoreInterface db;
	private final ExchangeRateFirestoreInterface exchangeRateDb;

	@GetMapping("/loadCurrenciesList")
	public SupportedCodeResponse forceLoadCurrenciesFromApi() throws ExchangeRateDataException, ExecutionException, InterruptedException {
		SupportedCodeResponse response = exchangeRateClient.supportedCodes();
		db.updateCurrenciesList(response.getSupportedCodes());
		return response;

	}

	@GetMapping("/changeCurrencyToMain")
	public void setCurrencyToMain() throws WrongQuerySizeException, ExecutionException, InterruptedException {
		db.setCurrenciesIsMainVariable("USD", true);
	}

	@GetMapping("/loadNotificationsSettings")
	public void forceLoadNotificationSettings() throws ExecutionException, InterruptedException {
		List<NotificationTypeEntity> notificationTypes = notifierService.getNotificationSettings();
		db.updateNotifierSettings(notificationTypes);
	}

	@GetMapping("/loadExchangeRate")
	public ExchangeCurrencyRateEntity forceLoadExchangeRate() throws ExchangeRateDataException {
		StandardResponse response = exchangeRateClient.standardRequest("USD");

		ExchangeCurrencyRateEntity entity = converterService.convert(response);

		exchangeRateDb.addExchangeRate(entity);

		return entity;
	}

	@GetMapping("/loadHistoricalExchangeRate")
	public ExchangeCurrencyRateEntity forceLoadHistoricalExchangeRate() throws ExchangeRateDataException {
		HistoricalResponse response = exchangeRateClient.historicalDataRequest("USD", 2005, 6, 1);

		ExchangeCurrencyRateEntity entity = converterService.convert(response);

		exchangeRateDb.addExchangeRate(entity);

		return entity;
	}
}
