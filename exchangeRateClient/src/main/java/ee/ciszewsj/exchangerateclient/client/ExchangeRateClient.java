package ee.ciszewsj.exchangerateclient.client;


import ee.ciszewsj.exchangerateclient.data.response.ApiRequestQuotaResponse;
import ee.ciszewsj.exchangerateclient.data.response.HistoricalResponse;
import ee.ciszewsj.exchangerateclient.data.response.StandardResponse;
import ee.ciszewsj.exchangerateclient.data.response.SupportedCodeResponse;

public interface ExchangeRateClient {
	StandardResponse standardRequest(String currency) throws ExchangeRateDataException;

	HistoricalResponse historicalDataRequest(String currency,
	                                         int year,
	                                         int month,
	                                         int day) throws ExchangeRateDataException;

	SupportedCodeResponse supportedCodes() throws ExchangeRateDataException;

	ApiRequestQuotaResponse apiRequestQuota() throws ExchangeRateDataException;
}
