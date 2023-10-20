package ee.ciszewsj.exchangeRateNotfierServer.application.controller;


import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import ee.ciszewsj.exchangeRateNotfierServer.application.firestore.FirestoreInterface;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateClient;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateDataException;
import ee.ciszewsj.exchangerateclient.data.response.SupportedCodeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdminController {
	private final Firestore firestore;
	private final ExchangeRateClient exchangeRateClient;
	private final FirestoreInterface db;

	@GetMapping("/loadCurrenciesList")
	public SupportedCodeResponse addCurrencies() {
		try {
			SupportedCodeResponse response = exchangeRateClient.supportedCodes();
			log.info("response: {}", response);
			db.updateCurrenciesList(response.getSupportedCodes());
			return response;
		} catch (ExchangeRateDataException | ExecutionException | InterruptedException e) {
			log.error("Could not get response due to {}", e, e);
			return null;
		}
	}

	public void saveData(String collection, String documentId, Object data) {
		DocumentReference docRef = firestore.collection(collection).document(documentId);
		docRef.set(data);
	}
}
