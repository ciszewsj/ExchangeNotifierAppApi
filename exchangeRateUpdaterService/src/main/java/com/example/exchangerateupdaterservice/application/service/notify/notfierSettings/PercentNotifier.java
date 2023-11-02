package com.example.exchangerateupdaterservice.application.service.notify.notfierSettings;

import com.example.exchangerateupdaterservice.application.service.UserDeviceSettingsService;
import com.example.exchangerateupdaterservice.application.service.notify.NotifierInterface;
import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.dto.notification.settings.PercentNotificationSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PercentNotifier implements NotifierInterface {

	private final UserDeviceSettingsService deviceSettingsService;

	@Override
	public void onCurrencyRateUpdate(CurrencyExchangeRateDocument document) {
		deviceSettingsService.getNotificationsSettingsByType(NotificationTypeEntity.NOTIFICATION_TYPES.PERCENT).forEach(
				notificationSettingsDto ->
						notificationSettingsDto.getNotificationSettingEntities()
								.forEach(notificationSettingEntity ->
										notificationSettingEntity.getNotificationTypes().forEach(
												notificationTypeEntity -> {
													PercentNotificationSettings percentNotificationSettings = (PercentNotificationSettings) notificationTypeEntity.getOptions();

													Date oldestDate;
													Calendar calendar = Calendar.getInstance();

													switch (percentNotificationSettings.getPeriod()) {
														case MONTH -> {
															calendar.add(Calendar.MONTH, -1);
															oldestDate = calendar.getTime();
														}
														case WEEK -> {
															calendar.add(Calendar.DATE, -7);
															oldestDate = calendar.getTime();
														}
														case DAY -> {
															calendar.add(Calendar.DATE, -1);
															oldestDate = calendar.getTime();
														}
														case HOUR -> {
															calendar.add(Calendar.HOUR, -1);
															oldestDate = calendar.getTime();
														}
														default -> {
															calendar.add(Calendar.YEAR, -1);
															oldestDate = calendar.getTime();
														}
													}

													Optional<ExchangeCurrencyRateEntity> max = document.getExchangeRates().stream().filter(exchangeCurrencyRateEntity -> exchangeCurrencyRateEntity.getDate().compareTo(oldestDate) > 0).max(Comparator.comparing(ExchangeCurrencyRateEntity::getRate));
													Optional<ExchangeCurrencyRateEntity> min = document.getExchangeRates().stream().filter(exchangeCurrencyRateEntity -> exchangeCurrencyRateEntity.getDate().compareTo(oldestDate) > 0).min(Comparator.comparing(ExchangeCurrencyRateEntity::getRate));

													Optional<ExchangeCurrencyRateEntity> newest = document.getExchangeRates().stream().max(Comparator.comparing(ExchangeCurrencyRateEntity::getDate));
													if (newest.isEmpty()) {
														return;
													}
													if (percentNotificationSettings.getPercent() == 0) {

														// TODO: Notify

														// TODO: Turn off notification
													} else if (percentNotificationSettings.getPercent() < 0) {
														if (max.isPresent() && (newest.get().getRate() - max.get().getRate()) / max.get().getRate() >= percentNotificationSettings.getPercent()) {
															// TODO: Notify
															// TODO: Turn off notification
														}
													} else {
														if (min.isPresent() && (newest.get().getRate() - min.get().getRate()) / min.get().getRate() <= percentNotificationSettings.getPercent()) {
															// TODO: Notify
															// TODO: Turn off notification
														}
													}
												}
										))
		);
	}
}
