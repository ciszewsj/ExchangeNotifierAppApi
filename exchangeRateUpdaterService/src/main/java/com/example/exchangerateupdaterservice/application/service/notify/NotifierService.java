package com.example.exchangerateupdaterservice.application.service.notify;

import com.example.exchangerateupdaterservice.application.service.notify.notfierSettings.TimeNotifier;
import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import ee.ciszewsj.exchangeratecommondata.documents.UserSettingsDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class NotifierService implements NotifierInterface {


	private final TimeNotifier timeNotifier;

	private final Map<String, UserSettingsDocument> userSettingsDocuments;
	private final Map<String, String> newestCurrencyValue;

	public NotifierService(TimeNotifier timeNotifier) {
		userSettingsDocuments = new HashMap<>();
		newestCurrencyValue = new HashMap<>();

		this.timeNotifier = timeNotifier;
	}

	@Override
	public void updateNotifierUserSettings(String documentId, UserSettingsDocument document) {
		userSettingsDocuments.put(documentId, document);
	}

	@Override
	public void exchangeCurrencyUpdate(CurrencyExchangeRateDocument document) {

	}

	@Override
	public void dateCurrencyUpdate() {
		timeNotifier.sendNotificationOnTimeProperty(newestCurrencyValue);
	}
}
