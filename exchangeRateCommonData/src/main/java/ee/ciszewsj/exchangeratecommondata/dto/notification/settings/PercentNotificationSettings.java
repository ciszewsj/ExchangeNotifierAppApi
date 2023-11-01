package ee.ciszewsj.exchangeratecommondata.dto.notification.settings;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PercentNotificationSettings extends AbstractNotificationSettings {
	private Double percent;
	private PeriodTime period;

	public enum PeriodTime {
		DAY,
		WEEK,
		MONTH
	}
}
