package ee.ciszewsj.exchangerateupdaterservice.controller;

import ee.ciszewsj.exchangerateupdaterservice.application.notifier.NotifierSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {
	private final NotifierSender sender;

	@PostMapping("/")
	public void send(@RequestBody String deviceId) {
		log.info("SENDING TEST PARAMETER TO {}", deviceId);
		sender.sendNotification(deviceId, "TEST", "TEST", Map.of("DUPA", "DUPA"));
	}
}
