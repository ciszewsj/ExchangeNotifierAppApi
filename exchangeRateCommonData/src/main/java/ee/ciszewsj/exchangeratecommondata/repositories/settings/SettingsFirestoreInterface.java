package ee.ciszewsj.exchangeratecommondata.repositories.settings;


import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;
import ee.ciszewsj.exchangeratecommondata.exceptions.WrongQuerySizeException;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SettingsFirestoreInterface {
	void updateCurrenciesList(List<List<String>> currencies) throws ExecutionException, InterruptedException;

	void updateNotifierSettings(List<NotificationTypeEntity> notificationTypeEntities) throws ExecutionException, InterruptedException;

	void setCurrenciesIsMainVariable(String currency, boolean isMain) throws ExecutionException, InterruptedException, WrongQuerySizeException;
}
