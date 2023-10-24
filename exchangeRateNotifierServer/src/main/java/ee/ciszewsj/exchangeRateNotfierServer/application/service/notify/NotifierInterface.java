package ee.ciszewsj.exchangeRateNotfierServer.application.service.notify;

import ee.ciszewsj.exchangeratecommondata.dto.NotificationTypeEntity;

public interface NotifierInterface {
	NotificationTypeEntity toNotificationTypeEntity();
}
