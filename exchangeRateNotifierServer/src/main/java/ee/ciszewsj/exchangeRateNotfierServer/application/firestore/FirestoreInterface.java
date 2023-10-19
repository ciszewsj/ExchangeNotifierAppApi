package ee.ciszewsj.exchangeRateNotfierServer.application.firestore;

import ee.ciszewsj.exchangeRateNotfierServer.data.SettingsEntity;

public interface FirestoreInterface {
	void createSettings(SettingsEntity settings);
}
