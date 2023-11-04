package com.example.exchangerateupdaterservice.application.notifier;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifierSenderService implements NotifierSender {

	private final FirebaseMessaging firebaseMessaging;

	@Override
	public void sendNotification(List<String> devices,
	                             String title,
	                             String body,
	                             Map<String, String> data) {
		devices.forEach(device -> this.sendNotification(device, title, body, data));
	}

	@Override
	public void sendNotification(String device, String title, String body, Map<String, String> data) {
		Notification notification = Notification.builder()
				.setTitle(title)
				.setBody(body)
				.build();

		Message message = Message.builder()
				.setToken(device)
				.setNotification(notification)
				.putAllData(data)
				.build();

		try {
			firebaseMessaging.send(message);
		} catch (FirebaseMessagingException e) {
			log.error("Could not send message due to:", e);
		}
	}
}
