package ee.ciszewsj.exchangeratecommondata.repositories.usersettings;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import ee.ciszewsj.exchangeratecommondata.documents.UserSettingsDocument;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FirestoreUserSettingsService implements UserSettingsFirestoreInterface {

	private final Map<String, UserSettingsDocument> userSettingsDocumentMap = new HashMap<>();
	private final Firestore firestore;

	private static final String collectionName = "USERS_SETTINGS";

	public FirestoreUserSettingsService(Firestore firestore) {
		this.firestore = firestore;
	}

	@Override
	public Map<String, UserSettingsDocument> getAllSettings() {
		return userSettingsDocumentMap;
	}

	@Override
	public void turnNotificationOff(String document, String uuid) {

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
			for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
				String documentId = document.getId();
				UserSettingsDocument userSettings = document.toObject(UserSettingsDocument.class);
				userSettingsDocumentMap.put(documentId, userSettings);
			}
		});
	}
}
