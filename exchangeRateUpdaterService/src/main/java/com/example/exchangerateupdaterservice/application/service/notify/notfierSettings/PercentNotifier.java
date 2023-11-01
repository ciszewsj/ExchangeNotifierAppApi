package com.example.exchangerateupdaterservice.application.service.notify.notfierSettings;

import com.example.exchangerateupdaterservice.application.service.UserDeviceSettingsService;
import com.example.exchangerateupdaterservice.application.service.notify.NotifierInterface;
import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.dto.notification.settings.PercentNotificationSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
												}
										))
		);
	}
}
