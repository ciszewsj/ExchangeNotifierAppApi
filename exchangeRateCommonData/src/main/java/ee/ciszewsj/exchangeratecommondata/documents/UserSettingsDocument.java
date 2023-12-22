package ee.ciszewsj.exchangeratecommondata.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationSettingEntity;
import ee.ciszewsj.exchangeratecommondata.dto.UserDeviceSettings;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsDocument {
	public static final String DEVICE_SETTINGS = "device_settings";
	public static final String NOTIFICATION_SETTINGS = "notification_settings";


	@Setter
	@JsonProperty(DEVICE_SETTINGS)
	List<UserDeviceSettings> userDeviceSettings;

	@Setter
	@JsonProperty(NOTIFICATION_SETTINGS)
	List<NotificationSettingEntity> notificationTypeEntities;

	public void setNotification_settings(List<NotificationSettingEntity> notification_settings) {
		this.notificationTypeEntities = notification_settings;
	}

	public List<NotificationSettingEntity> getNotification_settings() {
		return notificationTypeEntities;
	}

	public void setDevice_settings(List<UserDeviceSettings> deviceSettings) {
		this.userDeviceSettings = deviceSettings;
	}

	public List<UserDeviceSettings> getDevice_settings() {
		return userDeviceSettings;
	}

}
