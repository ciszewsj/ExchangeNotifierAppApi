package ee.ciszewsj.exchangerateupdaterservice.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.FirebaseMessaging;
import ee.ciszewsj.exchangeratecommondata.repositories.usersettings.FirestoreUserSettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FirebaseProperties.class)
public class FirestoreConfig {

	private final FirebaseProperties firebaseProperties;

	@Bean
	public FirebaseApp firebaseInit() throws IOException {
		FileInputStream serviceAccount =
				new FileInputStream(firebaseProperties.getServiceAccountKey());

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setProjectId(firebaseProperties.getProjectId())
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl(firebaseProperties.getDatabaseUrl())
				.build();

		return FirebaseApp.initializeApp(options);
	}

	@Bean
	public Firestore firestore(FirebaseApp app) {
		return FirestoreClient.getFirestore(app);
	}

	@Bean
	public FirestoreUserSettingsService exchangeRateFirestoreInterface(Firestore firestore) {
		return new FirestoreUserSettingsService(firestore);
	}

	@Bean
	public FirebaseMessaging firebaseMessaging(FirebaseApp app) {
		return FirebaseMessaging.getInstance(app);
	}
}
