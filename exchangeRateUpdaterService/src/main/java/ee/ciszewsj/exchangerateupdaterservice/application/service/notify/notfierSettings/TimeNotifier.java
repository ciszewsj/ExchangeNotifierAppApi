package ee.ciszewsj.exchangerateupdaterservice.application.service.notify.notfierSettings;

import ee.ciszewsj.exchangerateupdaterservice.application.notifier.NotifierSenderService;
import ee.ciszewsj.exchangerateupdaterservice.application.service.CurrencyRepositoryService;
import ee.ciszewsj.exchangerateupdaterservice.application.service.UserDeviceSettingsService;
import ee.ciszewsj.exchangerateupdaterservice.application.service.notify.NotifierInterface;
import ee.ciszewsj.exchangerateupdaterservice.data.CurrencyRateDto;
import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.dto.notification.settings.TimeNotificationSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeNotifier implements NotifierInterface {

	private Date lastUpdate = new Date();
	private final UserDeviceSettingsService deviceSettingsService;
	private final NotifierSenderService notifierSenderService;
	private final CurrencyRepositoryService repositoryService;

	private final static String notificationTemplate = "Exchange rate of %s is %s at %s";

	public void sendNotificationOnTimeProperty() {

		Date currentDate = new Date();

		deviceSettingsService.getNotificationsSettingsByType(NotificationTypeEntity.NOTIFICATION_TYPES.TIME)
				.forEach(object -> object.getNotificationSettingEntities().forEach(
						settings -> {
							String key = currenciesToKey(settings.getCurrencySymbol(), settings.getSecondCurrencySymbol());

							settings.getNotificationTypes().forEach(
									notifSettings -> {
										TimeNotificationSettings timeNotificationSettings = (TimeNotificationSettings) notifSettings.getOptions();

										Date ntDate = getNotificationTime(timeNotificationSettings.getHour(), timeNotificationSettings.getMinute());

										if (ntDate.getTime() <= currentDate.getTime()
												&& ntDate.getTime() > lastUpdate.getTime()) {
											Optional<CurrencyRateDto> rate = repositoryService.getRateByCurrencies(settings.getCurrencySymbol(),
													settings.getSecondCurrencySymbol());

											rate.ifPresent(r -> {
												log.info("Send notification");
												sendNotification(object.getDevices(), key, currentDate, rate.get().getRate().toString());
											});
										}
									}
							);
						}
				));

		lastUpdate = currentDate;

	}

	@Override
	public void onCurrencyRateUpdate(CurrencyExchangeRateDocument document) {
		Optional<ExchangeCurrencyRateEntity> currencyRate = document.getExchangeRates().stream().max(Comparator.comparing(ExchangeCurrencyRateEntity::getDate));
		currencyRate.ifPresent(rate -> repositoryService.updateRateByCurrencies(document.getMainCurrency(), document.getSecondaryCurrency(), currencyRate.get().getRate()));
	}

	private String currenciesToKey(String mainCurrency, String secondaryCurrency) {
		return "%s-%s".formatted(mainCurrency, secondaryCurrency);
	}

	private Date getNotificationTime(@NotNull Integer hour, @NotNull Integer minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);

		if (calendar.getTimeInMillis() > new Date().getTime()) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		return calendar.getTime();
	}

	private void sendNotification(List<String> devices,
	                              String title,
	                              Date date,
	                              String value
	) {
		notifierSenderService.sendNotification(devices,
				title,
				String.format(notificationTemplate, title, value, date),
				Map.of()
		);
	}
}
