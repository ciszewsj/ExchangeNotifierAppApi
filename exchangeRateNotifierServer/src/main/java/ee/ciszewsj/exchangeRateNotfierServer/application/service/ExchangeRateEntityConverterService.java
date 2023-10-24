package ee.ciszewsj.exchangeRateNotfierServer.application.service;

import ee.ciszewsj.exchangerateclient.data.response.HistoricalResponse;
import ee.ciszewsj.exchangerateclient.data.response.StandardResponse;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class ExchangeRateEntityConverterService {
	public ExchangeCurrencyRateEntity convert(StandardResponse response) {
		return ExchangeCurrencyRateEntity.builder()
				.uuid(UUID.randomUUID().toString())
				.symbol(response.getBaseCode())
				.date(new Date())
				.exchangeRates(
						response.getConversionRates()
				)
				.build();
	}

	public ExchangeCurrencyRateEntity convert(HistoricalResponse response) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, response.getYear());
		calendar.set(Calendar.MONTH, response.getMonth() - 1);
		calendar.set(Calendar.DAY_OF_MONTH, response.getDay());
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return ExchangeCurrencyRateEntity.builder()
				.uuid(UUID.randomUUID().toString())
				.symbol(response.getBaseCode())
				.date(new Date(calendar.getTimeInMillis()))
				.exchangeRates(
						response.getConversionRates()
				)
				.build();
	}
}
