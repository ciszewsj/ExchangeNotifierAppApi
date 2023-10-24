package com.example.exchangerateupdaterservice.application.service.notify.notfierSettings;

import com.example.exchangerateupdaterservice.application.service.notify.NotifierInterface;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.dto.OptionEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TimeNotifier implements NotifierInterface {
	public static final String TYPE_NAME = "Time is equal";

	private static final String VALUE = "Time";

	@Override
	public NotificationTypeEntity toNotificationTypeEntity() {
		return NotificationTypeEntity.builder()
				.typeName(TYPE_NAME)
				.options(List.of(
						OptionEntity.builder()
								.name(VALUE)
								.type(OptionEntity.FieldType.DATE_PICKER)
								.build()
				))
				.build();
	}
}
