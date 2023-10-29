package com.example.exchangerateupdaterservice.application.service.notify;

import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import ee.ciszewsj.exchangeratecommondata.documents.UserSettingsDocument;

public interface NotifierInterface {
	void updateNotifierUserSettings(String documentId, UserSettingsDocument document);

	void exchangeCurrencyUpdate(CurrencyExchangeRateDocument document);

	void dateCurrencyUpdate();
}
