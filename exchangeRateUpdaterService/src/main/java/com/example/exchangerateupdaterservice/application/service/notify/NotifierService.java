package com.example.exchangerateupdaterservice.application.service.notify;

import com.example.exchangerateupdaterservice.application.service.notify.notfierSettings.PercentNotifier;
import com.example.exchangerateupdaterservice.application.service.notify.notfierSettings.TimeNotifier;
import com.example.exchangerateupdaterservice.application.service.notify.notfierSettings.ValueHigherThanNotifier;
import com.example.exchangerateupdaterservice.application.service.notify.notfierSettings.ValueLessThanNotifier;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotifierService {

	private final List<NotifierInterface> notifiers;

	public NotifierService() {
		notifiers = List.of(
				new PercentNotifier(),
				new TimeNotifier(),
				new ValueHigherThanNotifier(),
				new ValueLessThanNotifier()
		);
	}

	public List<NotificationTypeEntity> getNotificationSettings() {
		return notifiers.stream().map(NotifierInterface::toNotificationTypeEntity).collect(Collectors.toList());
	}
}
