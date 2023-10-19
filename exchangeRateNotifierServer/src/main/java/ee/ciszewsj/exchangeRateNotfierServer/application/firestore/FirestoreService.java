package ee.ciszewsj.exchangeRateNotfierServer.application.firestore;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.database.annotations.NotNull;
import ee.ciszewsj.exchangeRateNotfierServer.data.SettingsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static ee.ciszewsj.exchangeRateNotfierServer.application.firestore.FirestoreStaticDocumentsIndex.SETTINGS_DOCUMENT;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirestoreService implements FirestoreInterface {
	private final Firestore firestore;

	@Override
	public void createSettings(SettingsEntity settings) {
		saveData(FirestoreCollections.SETTINGS.toString(), SETTINGS_DOCUMENT, settings);
	}

	private void saveData(@NotNull String collection, @NotNull String documentId, @NotNull Object data) {
		DocumentReference docRef = firestore.collection(collection).document(documentId);
		docRef.set(data);

	}
}
