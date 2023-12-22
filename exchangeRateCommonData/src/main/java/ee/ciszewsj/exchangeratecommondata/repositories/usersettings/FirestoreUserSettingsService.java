package ee.ciszewsj.exchangeratecommondata.repositories.usersettings;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import ee.ciszewsj.exchangeratecommondata.documents.UserSettingsDocument;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationSettingEntity;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class FirestoreUserSettingsService implements UserSettingsFirestoreInterface {

	private final Map<String, UserSettingsDocument> userSettingsDocumentMap = new HashMap<>();
	private final Firestore firestore;

	private static final String collectionName = "USERS_SETTINGS";

	public FirestoreUserSettingsService(Firestore firestore) {
		this.firestore = firestore;
		this.addSnapshotListener();
	}

	@Override
	public Map<String, UserSettingsDocument> getAllSettings() {
		return userSettingsDocumentMap;
	}

	@Override
	public void turnNotificationOff(String document, String uuid) {
		UserSettingsDocument documentBody = userSettingsDocumentMap.get(document);
		Optional<NotificationSettingEntity> settingsDocument = documentBody.getNotificationTypeEntities().stream()
				.filter(sEntity -> sEntity.getNotificationTypes().stream().anyMatch(s -> s.getUuid().equals(uuid) && s.isEnabled()))
				.findFirst();
		settingsDocument.ifPresentOrElse(settings -> {

			List<NotificationTypeEntity> entities = settings.getNotificationTypes().stream()
					.peek(sd -> {
						if (sd.getUuid().equals(uuid) && sd.isEnabled()) {
							sd.setEnabled(false);
						}
					}).collect(Collectors.toList());

			settings.setNotificationTypes(entities);
			List<NotificationSettingEntity> newNotificationSettingEntities = documentBody.getNotificationTypeEntities()
					.stream()
					.map(s -> {
						if (s.getCurrencySymbol().equals(settings.getCurrencySymbol()) &&
								s.getSecondCurrencySymbol().equals(settings.getSecondCurrencySymbol())) {
							return settings;
						}
						return s;
					})
					.toList();

			documentBody.setNotificationTypeEntities(newNotificationSettingEntities);
			firestore.collection(collectionName).document(document).set(documentBody);
			log.info("Successfully turn off notification with documentId [{}] and uuid [{}]", document, uuid);
		}, () -> log.error("Could not turn off notification with documentId [{}] and uuid [{}] because not found", document, uuid));
	}

	private void addSnapshotListener() {
		CollectionReference collection = firestore.collection(collectionName);

		collection.addSnapshotListener((queryDocumentSnapshots, e) -> {
			if (e != null && e.getStatus() != Status.OK) {
				log.error("Error occurred: " + e);
				return;
			} else if (queryDocumentSnapshots == null) {
				log.error("");
				return;
			}
			log.info("Updating...");
			for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
				String documentId = document.getId();
				UserSettingsDocument userSettings = document.toObject(UserSettingsDocument.class);
				userSettingsDocumentMap.put(documentId, userSettings);
			}
		});
	}
}
