package ee.ciszewsj.exchangeRateNotfierServer.application.service.notify;

import ee.ciszewsj.exchangeRateNotfierServer.data.NotificationTypeEntity;

public interface NotifierInterface {
	NotificationTypeEntity toNotificationTypeEntity();
}
