package ee.ciszewsj.exchangeRateNotfierServer.application.firestore;

import ee.ciszewsj.exchangeRateNotfierServer.data.NotificationTypeEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SettingsFirestoreInterface {
	void updateCurrenciesList(List<List<String>> currencies) throws ExecutionException, InterruptedException;

	void updateNotifierSettings(List<NotificationTypeEntity> notificationTypeEntities) throws ExecutionException, InterruptedException;

	void setCurrenciesIsMainVariable(String currency, boolean isMain) throws ExecutionException, InterruptedException, WrongQuerySizeException;
}
