package ee.ciszewsj.exchangeratecommondata.dto.notification.settings;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class ValueNotificationSettings extends AbstractNotificationSettings {
	private Double value;
	private Type type;

	public enum Type {
		LOWER,
		HIGHER
	}
}
