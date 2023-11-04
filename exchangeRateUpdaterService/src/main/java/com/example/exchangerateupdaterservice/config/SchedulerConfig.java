package com.example.exchangerateupdaterservice.config;

import com.example.exchangerateupdaterservice.application.service.notify.notfierSettings.TimeNotifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {

	private final TimeNotifier notifier;

	@Scheduled(cron = "${app.exchange-rate.notification.cron-updater}")
	public void timeNotifier() {
		notifier.sendNotificationOnTimeProperty();
	}
}
