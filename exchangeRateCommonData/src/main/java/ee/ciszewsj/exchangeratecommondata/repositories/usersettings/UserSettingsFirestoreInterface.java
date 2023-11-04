package ee.ciszewsj.exchangeratecommondata.repositories.usersettings;

import ee.ciszewsj.exchangeratecommondata.documents.UserSettingsDocument;

import java.util.Map;

public interface UserSettingsFirestoreInterface {
	Map<String, UserSettingsDocument> getAllSettings();

	void turnNotificationOff(String document, String uuid);
}
