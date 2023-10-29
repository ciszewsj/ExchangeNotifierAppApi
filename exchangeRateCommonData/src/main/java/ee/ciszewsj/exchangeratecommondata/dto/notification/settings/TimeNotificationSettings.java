package ee.ciszewsj.exchangeratecommondata.dto.notification.settings;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeNotificationSettings extends AbstractNotificationSettings {
	private String hour;
	private String minute;
}
