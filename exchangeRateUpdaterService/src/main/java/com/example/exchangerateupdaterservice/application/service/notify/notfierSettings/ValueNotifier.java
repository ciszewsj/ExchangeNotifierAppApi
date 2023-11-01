package com.example.exchangerateupdaterservice.application.service.notify.notfierSettings;

import com.example.exchangerateupdaterservice.application.service.UserDeviceSettingsService;
import com.example.exchangerateupdaterservice.application.service.notify.NotifierInterface;
import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.dto.notification.settings.ValueNotificationSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValueNotifier implements NotifierInterface {

	private final UserDeviceSettingsService deviceSettingsService;

	@Override
	public void onCurrencyRateUpdate(CurrencyExchangeRateDocument document) {
		Optional<ExchangeCurrencyRateEntity> currencyRate = document.getExchangeRates().stream().max(Comparator.comparing(ExchangeCurrencyRateEntity::getDate));
		if (currencyRate.isEmpty()) {
			throw new IllegalStateException();
		}

		Double rate = currencyRate.get().getRate();

		deviceSettingsService.getNotificationsSettingsByType(NotificationTypeEntity.NOTIFICATION_TYPES.VALUE).forEach(
				notificationSettingsDto ->
						notificationSettingsDto.getNotificationSettingEntities().forEach(settings ->
								settings.getNotificationTypes().forEach(notificationType -> {
									ValueNotificationSettings valueNotificationSettings = (ValueNotificationSettings) notificationType.getOptions();

									switch (valueNotificationSettings.getType()) {
										case LOWER -> {
											if (valueNotificationSettings.getValue() > rate) {
												log.info("Send notification");
												// TODO: Notify user about changes

												// TODO: Turn notification off
											}
										}
										case HIGHER -> {
											if (valueNotificationSettings.getValue() < rate) {
												log.info("Send notification");
												// TODO: Notify user about changes

												// TODO: Turn notification off
											}
										}
									}
								}))
		);
	}
}
