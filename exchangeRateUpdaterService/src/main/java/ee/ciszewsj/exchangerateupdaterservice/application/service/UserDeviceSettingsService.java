package ee.ciszewsj.exchangerateupdaterservice.application.service;

import ee.ciszewsj.exchangerateupdaterservice.data.NotificationSettingsDto;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationSettingEntity;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.dto.UserDeviceSettings;
import ee.ciszewsj.exchangeratecommondata.repositories.usersettings.UserSettingsFirestoreInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDeviceSettingsService {

	private final UserSettingsFirestoreInterface userSettingsService;

	public Stream<NotificationSettingsDto> getNotificationsSettingsByType(NotificationTypeEntity.NOTIFICATION_TYPES notificationType) {
		Date currentDate = new Date();
		final List<NotificationSettingsDto> notificationSettingsDto = new ArrayList<>();
		userSettingsService.getAllSettings().forEach((s, document) -> {
			List<String> devices = document.getUserDeviceSettings().stream()
					.filter(notif -> currentDate.getTime() + 10000 < notif.getLastUpdateTime().getTime())
					.map(UserDeviceSettings::getToken)
					.collect(Collectors.toList());
			if (devices.size() == 0) {
				return;
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
				return;
			}
			notificationSettingsDto.add(NotificationSettingsDto.builder()
					.documentId(s)
					.devices(devices)
					.notificationSettingEntities(document.getNotificationTypeEntities())
					.build());
		});
		return notificationSettingsDto.stream();
	}

}
