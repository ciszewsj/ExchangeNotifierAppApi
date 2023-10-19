package ee.ciszewsj.exchangeRateNotfierServer.application.service.notify.notfierSettings;

import ee.ciszewsj.exchangeRateNotfierServer.application.service.notify.NotifierInterface;
import ee.ciszewsj.exchangeRateNotfierServer.data.NotificationTypeEntity;
import ee.ciszewsj.exchangeRateNotfierServer.data.OptionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ValueLessThanNotifier implements NotifierInterface {

	public static final String TYPE_NAME = "Value is lower than";

	private static final String VALUE1 = "Value";

	@Override
	public NotificationTypeEntity toNotificationTypeEntity() {
		return NotificationTypeEntity.builder()
				.typeName(TYPE_NAME)
				.options(List.of(
						OptionEntity.builder()
								.name(VALUE1)
								.type(OptionEntity.FieldType.DOUBLE_VALUE)
								.build()
				))
				.build();
	}
}
