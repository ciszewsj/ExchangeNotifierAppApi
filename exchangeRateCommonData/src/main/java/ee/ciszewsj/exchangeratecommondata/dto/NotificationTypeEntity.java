package ee.ciszewsj.exchangeratecommondata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ee.ciszewsj.exchangeratecommondata.dto.notification.settings.AbstractNotificationSettings;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationTypeEntity {
	public static final String TYPE_NAME = "type_name";
	public static final String ENABLED = "enabled";
	public static final String OPTIONS = "options";
	public static final String UUID = "uuid";

	@JsonProperty(UUID)
	private String uuid;
	@JsonProperty(TYPE_NAME)
	private NOTIFICATION_TYPES typeName;
	@JsonProperty(ENABLED)
	private boolean enabled;
	@JsonProperty(OPTIONS)
	private AbstractNotificationSettings options;

	public enum NOTIFICATION_TYPES {
		TIME,
		PERCENT,
		VALUE
	}

}
