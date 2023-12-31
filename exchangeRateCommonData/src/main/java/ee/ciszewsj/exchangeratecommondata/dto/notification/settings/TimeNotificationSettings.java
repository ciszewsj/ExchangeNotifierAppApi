package ee.ciszewsj.exchangeratecommondata.dto.notification.settings;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class TimeNotificationSettings extends AbstractNotificationSettings {
	private Integer hour;
	private Integer minute;
}
