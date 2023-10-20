package ee.ciszewsj.exchangeRateNotfierServer.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NotificationsSettingsEntity {
	private List<NotificationTypeEntity> notificationTypes;
}
