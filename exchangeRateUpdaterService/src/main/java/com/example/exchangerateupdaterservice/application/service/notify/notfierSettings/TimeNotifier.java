package com.example.exchangerateupdaterservice.application.service.notify.notfierSettings;

import com.example.exchangerateupdaterservice.application.service.UserDeviceSettingsService;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.dto.notification.settings.TimeNotificationSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeNotifier {

	private Date lastUpdate;
	private final UserDeviceSettingsService deviceSettingsService;

	public void sendNotificationOnTimeProperty(Map<String, String> newestCurrencyValue) {

		Date currentDate = new Date();

		deviceSettingsService.getNotificationsSettingsByType(NotificationTypeEntity.NOTIFICATION_TYPES.TIME)
				.forEach(object -> object.getNotificationSettingEntities().forEach(
						settings -> settings.getNotificationTypes().forEach(
								notifSettings -> {
									TimeNotificationSettings timeNotificationSettings = (TimeNotificationSettings) notifSettings.getOptions();

									

								}
						)
				));

		lastUpdate = currentDate;

	}


}
