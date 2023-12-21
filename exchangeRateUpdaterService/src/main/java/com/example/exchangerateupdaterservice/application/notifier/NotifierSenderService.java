package com.example.exchangerateupdaterservice.application.notifier;

import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushTicket;
import io.github.jav.exposerversdk.PushClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifierSenderService implements NotifierSender {

	@Override
	public void sendNotification(List<String> devices,
	                             String title,
	                             String body,
	                             Map<String, String> data) {
		devices.forEach(device -> this.sendNotification(device, title, body, data));
	}

	@Override
	public void sendNotification(String device, String title, String body, Map<String, String> data) {
		ExpoPushMessage message = new ExpoPushMessage();

		if (!PushClient.isExponentPushToken(device)) {
			log.warn("Token:" + device + " is not a valid token.");
			return;
		}

		message.getTo().add(device);
		message.setTitle(title);
		message.setBody(body);
		message.setData(data);

		List<ExpoPushMessage> expoPushMessages = List.of(message);

		PushClient client = new PushClient();


		CompletableFuture<List<ExpoPushTicket>> ticket = client.sendPushNotificationsAsync(expoPushMessages);

		try {
			ticket.wait();
		} catch (InterruptedException e) {
			log.error("Error during execution of ticker", e);
		}
	}
}
