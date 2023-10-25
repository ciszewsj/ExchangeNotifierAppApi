package ee.ciszewsj.exchangeRateNotfierServer.config;

import ee.ciszewsj.exchangeRateNotfierServer.application.service.CurrencyUpdaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SchedulerCurrencyUpdaterConfig {

	public final CurrencyUpdaterService updaterService;

	@Scheduled(cron = "${app.exchange-rate.api.cron-updater}")
	public void updaterScheduler() {
		log.info("Start updater all currencies from scheduler ...");
		updaterService.updateAllActiveCurrencies();
		log.info("... finished updater all currencies from scheduler");

	}
}
