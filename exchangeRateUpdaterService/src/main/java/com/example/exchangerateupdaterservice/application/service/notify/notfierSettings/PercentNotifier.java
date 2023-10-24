package com.example.exchangerateupdaterservice.application.service.notify.notfierSettings;

import com.example.exchangerateupdaterservice.application.service.notify.NotifierInterface;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.dto.OptionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PercentNotifier implements NotifierInterface {

	public static final String TYPE_NAME = "Percent has changed";

	private static final String VALUE1 = "Time";
	private static final String VALUE2 = "Period";

	private static final String PERIOD_1_HOUR = "1 Hour";
	private static final String PERIOD_6_HOUR = "6 Hour";
	private static final String PERIOD_12_HOUR = "12 Hour";
	private static final String PERIOD_1_Day = "1 Day";
	private static final String PERIOD_1_Month = "1 Month";

	@Override
	public NotificationTypeEntity toNotificationTypeEntity() {
		return NotificationTypeEntity.builder()
				.typeName(TYPE_NAME)
				.options(List.of(
						OptionEntity.builder()
								.name(VALUE1)
								.type(OptionEntity.FieldType.PERCENT_VALUE)
								.build(),
						OptionEntity.builder()
								.name(VALUE2)
								.type(OptionEntity.FieldType.OPTIONS_VALUE)
								.possibleValues(
										List.of(PERIOD_1_HOUR,
												PERIOD_6_HOUR,
												PERIOD_12_HOUR,
												PERIOD_1_Day,
												PERIOD_1_Month))
								.build()
				))
				.build();
	}
}
