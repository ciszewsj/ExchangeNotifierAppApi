package ee.ciszewsj.exchangerateupdaterservice.data;

import ee.ciszewsj.exchangeratecommondata.dto.NotificationSettingEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NotificationSettingsDto {

	private String documentId;
	private List<String> devices;
	private List<NotificationSettingEntity> notificationSettingEntities;
}