package ee.ciszewsj.exchangeratecommondata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ee.ciszewsj.exchangeratecommondata.dto.notification.settings.AbstractNotificationSettings;
import lombok.*;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

	public void setType_name(NOTIFICATION_TYPES typeName) {
		this.typeName = typeName;
	}

	public NOTIFICATION_TYPES getType_name() {
		return typeName;
	}

	public enum NOTIFICATION_TYPES {
		TIME,
		PERCENT,
		VALUE
	}

}
