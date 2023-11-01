package com.example.exchangerateupdaterservice.application.service;

import com.example.exchangerateupdaterservice.data.NotificationSettingsDto;
import ee.ciszewsj.exchangeratecommondata.documents.UserSettingsDocument;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationSettingEntity;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.dto.UserDeviceSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class UserDeviceSettingsService {

	private final List<UserSettingsDocument> userSettingsDocuments = new ArrayList<>();

	public Stream<NotificationSettingsDto> getNotificationsSettingsByType(NotificationTypeEntity.NOTIFICATION_TYPES notificationType) {
		Date currentDate = new Date();
		return userSettingsDocuments.stream()
				.map(document -> {
					List<String> devices = document.getUserDeviceSettings().stream()
							.filter(notif -> currentDate.getTime() + 10000 < notif.getLastUpdateTime().getTime())
							.map(UserDeviceSettings::getToken)
							.collect(Collectors.toList());
					if (devices.size() == 0) {
						return null;
					}
					List<NotificationSettingEntity> notificationSettingEntities
							= document.getNotificationTypeEntities().stream()
							.map(notif -> NotificationSettingEntity.builder()
									.currencySymbol(notif.getCurrencySymbol())
									.notificationTypes(notif.getNotificationTypes()
											.stream()
											.filter(type -> type.getTypeName().equals(notificationType))
											.toList())
									.build())
							.filter(notif -> notif.getNotificationTypes().size() > 0)
							.toList();
					if (notificationSettingEntities.size() == 0) {
						return null;
					}
					return NotificationSettingsDto.builder().devices(devices).build();
				}).filter(Objects::nonNull);
	}

}
