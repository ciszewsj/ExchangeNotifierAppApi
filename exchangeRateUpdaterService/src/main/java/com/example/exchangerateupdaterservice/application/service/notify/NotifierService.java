package com.example.exchangerateupdaterservice.application.service.notify;

import com.example.exchangerateupdaterservice.application.service.notify.notfierSettings.PercentNotifier;
import com.example.exchangerateupdaterservice.application.service.notify.notfierSettings.TimeNotifier;
import com.example.exchangerateupdaterservice.application.service.notify.notfierSettings.ValueNotifier;
import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NotifierService {
	private final List<NotifierInterface> notifiers;


	public NotifierService(TimeNotifier timeNotifier,
	                       PercentNotifier percentNotifier,
	                       ValueNotifier valueNotifier) {
		this.notifiers = List.of(
				timeNotifier,
				percentNotifier,
				valueNotifier
		);
	}

	public void exchangeCurrencyUpdate(CurrencyExchangeRateDocument document) {
		notifiers.forEach(notifier -> notifier.onCurrencyRateUpdate(document));
	}
}
