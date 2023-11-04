package ee.ciszewsj.exchangeratecommondata.dto.notification.settings;


import lombok.Data;

@Data
public abstract class AbstractNotificationSettings {
	private Type notificationType;

	enum Type {
		VALUE_TYPE,
		TIME_TYPE,
		PERCENT_TYPE
	}
}
