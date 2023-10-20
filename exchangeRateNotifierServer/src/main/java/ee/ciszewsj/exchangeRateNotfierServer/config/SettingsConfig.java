package ee.ciszewsj.exchangeRateNotfierServer.config;

import ee.ciszewsj.exchangeRateNotfierServer.application.firestore.FirestoreInterface;
import ee.ciszewsj.exchangeRateNotfierServer.application.service.notify.NotifierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SettingsConfig {
	private final FirestoreInterface firestoreService;

	private final NotifierService notifierService;

	@EventListener(ApplicationReadyEvent.class)
	public void onStartUp() {
//		log.info("On startUp");
//		SettingsEntity settings = SettingsEntity.builder()
//				.notificationTypes(notifierService.getNotificationSettings())
//				.build();
//
//		firestoreService.createSettings(settings);
	}
}
