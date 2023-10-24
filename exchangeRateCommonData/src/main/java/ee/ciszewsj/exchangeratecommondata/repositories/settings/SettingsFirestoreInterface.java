package ee.ciszewsj.exchangeratecommondata.repositories.settings;


import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SettingsFirestoreInterface {

	void updateNotifierSettings(List<NotificationTypeEntity> notificationTypeEntities) throws ExecutionException, InterruptedException;

}
