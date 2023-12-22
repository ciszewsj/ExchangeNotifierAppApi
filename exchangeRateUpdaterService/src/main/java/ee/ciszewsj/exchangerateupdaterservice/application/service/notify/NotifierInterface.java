package ee.ciszewsj.exchangerateupdaterservice.application.service.notify;

import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;

public interface NotifierInterface {
	void onCurrencyRateUpdate(CurrencyExchangeRateDocument document);
}
