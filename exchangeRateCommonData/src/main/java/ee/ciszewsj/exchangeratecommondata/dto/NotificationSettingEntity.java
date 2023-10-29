package ee.ciszewsj.exchangeratecommondata.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NotificationSettingEntity {
	private String currencySymbol;
	private String secondCurrencySymbol;
	private List<NotificationTypeEntity> notificationTypes;
}
