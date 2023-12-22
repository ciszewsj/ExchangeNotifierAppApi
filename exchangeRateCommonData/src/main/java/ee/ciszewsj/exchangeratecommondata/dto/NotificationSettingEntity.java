package ee.ciszewsj.exchangeratecommondata.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationSettingEntity {
	private String currencySymbol;
	private String secondCurrencySymbol;
	private boolean enabled;
	private List<NotificationTypeEntity> notificationTypes;
}
