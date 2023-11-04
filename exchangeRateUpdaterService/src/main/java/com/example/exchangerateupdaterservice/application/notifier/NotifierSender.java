package com.example.exchangerateupdaterservice.application.notifier;

import java.util.List;
import java.util.Map;

public interface NotifierSender {

	void sendNotification(List<String> devices,
	                      String title,
	                      String body,
	                      Map<String, String> data);

	void sendNotification(String device,
	                      String title,
	                      String body,
	                      Map<String, String> data);
}
