package com.example.exchangerateupdaterservice.application.service.notify.notfierSettings;

import com.example.exchangerateupdaterservice.application.notifier.NotifierSenderService;
import com.example.exchangerateupdaterservice.application.service.UserDeviceSettingsService;
import com.example.exchangerateupdaterservice.application.service.notify.NotifierInterface;
import com.example.exchangerateupdaterservice.data.NotificationSettingsDto;
import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationSettingEntity;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.dto.notification.settings.PercentNotificationSettings;
import ee.ciszewsj.exchangeratecommondata.repositories.usersettings.UserSettingsFirestoreInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PercentNotifier implements NotifierInterface {

	private final UserDeviceSettingsService deviceSettingsService;
	private final NotifierSenderService notifierSenderService;
	private final UserSettingsFirestoreInterface userSettingsFirestore;

	private static final String notificationTemplate = "Exchange rate of %s has changed %s during %s period";
	private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");


	@Override
	public void onCurrencyRateUpdate(CurrencyExchangeRateDocument document) {
		Date currentDate = new Date();
		deviceSettingsService.getNotificationsSettingsByType(NotificationTypeEntity.NOTIFICATION_TYPES.PERCENT).forEach(
				notificationSettingsDto ->
						notificationSettingsDto.getNotificationSettingEntities()
								.forEach(notificationSettingEntity ->
										notificationSettingEntity.getNotificationTypes().forEach(
												notificationTypeEntity -> {
													PercentNotificationSettings percentNotificationSettings = (PercentNotificationSettings) notificationTypeEntity.getOptions();

													Date oldestDate;
													Calendar calendar = Calendar.getInstance();

													switch (percentNotificationSettings.getPeriod()) {
														case MONTH -> {
															calendar.add(Calendar.MONTH, -1);
															oldestDate = calendar.getTime();
														}
														case WEEK -> {
															calendar.add(Calendar.DATE, -7);
															oldestDate = calendar.getTime();
														}
														case DAY -> {
															calendar.add(Calendar.DATE, -1);
															oldestDate = calendar.getTime();
														}
														case HOUR -> {
															calendar.add(Calendar.HOUR, -1);
															oldestDate = calendar.getTime();
														}
														default -> {
															calendar.add(Calendar.YEAR, -1);
															oldestDate = calendar.getTime();
														}
													}

													Optional<ExchangeCurrencyRateEntity> max = document.getExchangeRates().stream().filter(exchangeCurrencyRateEntity -> exchangeCurrencyRateEntity.getDate().compareTo(oldestDate) > 0).max(Comparator.comparing(ExchangeCurrencyRateEntity::getRate));
													Optional<ExchangeCurrencyRateEntity> min = document.getExchangeRates().stream().filter(exchangeCurrencyRateEntity -> exchangeCurrencyRateEntity.getDate().compareTo(oldestDate) > 0).min(Comparator.comparing(ExchangeCurrencyRateEntity::getRate));

													Optional<ExchangeCurrencyRateEntity> newest = document.getExchangeRates().stream().max(Comparator.comparing(ExchangeCurrencyRateEntity::getDate));
													if (newest.isEmpty()) {
														return;
													}


													if (percentNotificationSettings.getPercent() == 0) {
														sendNotification(notificationSettingsDto.getDevices(),
																currenciesToKey(notificationSettingEntity.getCurrencySymbol(), notificationSettingEntity.getSecondCurrencySymbol()),
																"0.0",
																currentDate
														);
														userSettingsFirestore.turnNotificationOff(notificationSettingsDto.getDocumentId(), notificationTypeEntity.getUuid());
													} else if (percentNotificationSettings.getPercent() < 0) {
														checkIfCorrectAndSendNotification(currentDate, notificationSettingsDto, notificationTypeEntity.getUuid(), notificationSettingEntity, percentNotificationSettings, max, newest.get());
													} else {
														checkIfCorrectAndSendNotification(currentDate, notificationSettingsDto, notificationTypeEntity.getUuid(), notificationSettingEntity, percentNotificationSettings, min, newest.get());
													}
												}
										))
		);
	}

	private void checkIfCorrectAndSendNotification(Date currentDate, NotificationSettingsDto notificationSettingsDto, String uuid, NotificationSettingEntity notificationSettingEntity, PercentNotificationSettings percentNotificationSettings, Optional<ExchangeCurrencyRateEntity> min, ExchangeCurrencyRateEntity newest) {
		if (min.isPresent()) {
			double currentPercent = (newest.getRate() - min.get().getRate()) / min.get().getRate();
			if (currentPercent >= percentNotificationSettings.getPercent()) {
				sendNotification(notificationSettingsDto.getDevices(),
						currenciesToKey(notificationSettingEntity.getCurrencySymbol(), notificationSettingEntity.getSecondCurrencySymbol()),
						Double.toString(currentPercent),
						currentDate
				);
				userSettingsFirestore.turnNotificationOff(notificationSettingsDto.getDocumentId(), uuid);
			}
		}
	}

	private String currenciesToKey(String mainCurrency, String secondaryCurrency) {
		return "%s-%s".formatted(mainCurrency, secondaryCurrency);
	}

	private void sendNotification(List<String> devices,
	                              String title,
	                              String value,
	                              Date date
	) {
		notifierSenderService.sendNotification(devices,
				title,
				String.format(notificationTemplate, title, value, dateFormat.format(date)),
				Map.of()
		);
	}
}
