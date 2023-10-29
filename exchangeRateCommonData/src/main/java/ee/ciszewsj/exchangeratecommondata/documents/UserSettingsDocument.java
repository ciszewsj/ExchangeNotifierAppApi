package ee.ciszewsj.exchangeratecommondata.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationSettingEntity;
import ee.ciszewsj.exchangeratecommondata.dto.UserDeviceSettings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsDocument {
	public static final String DEVICE_SETTINGS = "device_settings";
	public static final String NOTIFICATION_SETTINGS = "notification_settings";


	@JsonProperty(DEVICE_SETTINGS)
	List<UserDeviceSettings> userDeviceSettings;

	@JsonProperty(NOTIFICATION_SETTINGS)
	List<NotificationSettingEntity> notificationTypeEntities;
}
