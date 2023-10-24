package ee.ciszewsj.exchangeratecommondata.repositories.settings;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import ee.ciszewsj.exchangeratecommondata.dto.CurrencyEntity;
import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.exceptions.WrongQuerySizeException;
import ee.ciszewsj.exchangeratecommondata.repositories.FirestoreCollections;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static ee.ciszewsj.exchangeratecommondata.dto.CurrencyEntity.IS_MAIN;

@RequiredArgsConstructor
public class FirestoreSettingsService implements SettingsFirestoreInterface {
	private final Firestore firestore;

	private final String SETTINGS_COLLECTION = FirestoreCollections.SETTINGS.toString();

	@Override
	public void updateNotifierSettings(List<NotificationTypeEntity> notificationTypeEntities) throws ExecutionException, InterruptedException {
		CollectionReference collection = firestore.collection(SETTINGS_COLLECTION);
		QuerySnapshot querySnapshot = collection.get().get();
		querySnapshot.forEach(queryDocumentSnapshot -> queryDocumentSnapshot.getReference().delete());
		notificationTypeEntities.forEach(
				notificationTypeEntity -> collection.document(notificationTypeEntity.getTypeName()).set(notificationTypeEntity)
		);
	}

}
