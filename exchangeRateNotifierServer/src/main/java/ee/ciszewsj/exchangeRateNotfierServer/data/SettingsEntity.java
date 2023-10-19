package ee.ciszewsj.exchangeRateNotfierServer.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SettingsEntity {
	private List<NotificationTypeEntity> notificationTypes;
}
