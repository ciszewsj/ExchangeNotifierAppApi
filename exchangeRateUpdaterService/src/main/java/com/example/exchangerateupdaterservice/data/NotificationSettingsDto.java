package com.example.exchangerateupdaterservice.data;

import ee.ciszewsj.exchangeratecommondata.dto.NotificationSettingEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NotificationSettingsDto {

	private List<String> devices;
	private List<NotificationSettingEntity> notificationSettingEntities;
}