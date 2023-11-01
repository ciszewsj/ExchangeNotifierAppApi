package com.example.exchangerateupdaterservice.application.service.notify.notfierSettings;

import com.example.exchangerateupdaterservice.application.service.UserDeviceSettingsService;
import com.example.exchangerateupdaterservice.application.service.notify.NotifierInterface;
import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.dto.notification.settings.TimeNotificationSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeNotifier implements NotifierInterface {

	private Date lastUpdate = new Date();
	private final UserDeviceSettingsService deviceSettingsService;
	private final Map<String, Double> newestCurrencyValue;

	public void sendNotificationOnTimeProperty() {

		Date currentDate = new Date();

		deviceSettingsService.getNotificationsSettingsByType(NotificationTypeEntity.NOTIFICATION_TYPES.TIME)
				.forEach(object -> object.getNotificationSettingEntities().forEach(
						settings -> {
							String key = currenciesToKey(settings.getCurrencySymbol(), settings.getSecondCurrencySymbol());

							settings.getNotificationTypes().forEach(
									notifSettings -> {
										TimeNotificationSettings timeNotificationSettings = (TimeNotificationSettings) notifSettings.getOptions();

										Date ntDate = getNotificationTime(timeNotificationSettings.getHour(), timeNotificationSettings.getMinute());

										if (ntDate.getTime() <= currentDate.getTime()
												&& ntDate.getTime() > lastUpdate.getTime()
												&& newestCurrencyValue.containsKey(key)) {
											log.info("Send notification");
											// TODO: Notify user about changes
										}
									}
							);
						}
				));

		lastUpdate = currentDate;

	}

	@Override
	public void onCurrencyRateUpdate(CurrencyExchangeRateDocument document) {
		String key = currenciesToKey(document.getMainCurrency(), document.getSecondaryCurrency());
		Optional<ExchangeCurrencyRateEntity> currencyRate = document.getExchangeRates().stream().max(Comparator.comparing(ExchangeCurrencyRateEntity::getDate));
		currencyRate.ifPresent(rate -> newestCurrencyValue.put(key, rate.getRate()));
	}

	private String currenciesToKey(String mainCurrency, String secondaryCurrency) {
		return "%s-%s".formatted(mainCurrency, secondaryCurrency);
	}

	private Date getNotificationTime(@NotNull Integer hour, @NotNull Integer minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);

		if (calendar.getTimeInMillis() > new Date().getTime()) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		return calendar.getTime();
	}
}
