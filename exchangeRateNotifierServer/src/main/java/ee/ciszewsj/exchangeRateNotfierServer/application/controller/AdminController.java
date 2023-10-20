package ee.ciszewsj.exchangeRateNotfierServer.application.controller;


import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateClient;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateDataException;
import ee.ciszewsj.exchangerateclient.data.response.SupportedCodeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdminController {
	private final Firestore firestore;
	private final ExchangeRateClient exchangeRateClient;

	@GetMapping("/loadCurrenciesList")
	public SupportedCodeResponse addMainCurrency() throws ExchangeRateDataException {
		try {
			SupportedCodeResponse response = exchangeRateClient.supportedCodes();
			log.info("response: {}", response);
			return response;
		} catch (ExchangeRateDataException e) {
			log.error("Could not get response due to {}", e, e);
			throw e;
		}
	}

	public void saveData(String collection, String documentId, Object data) {
		DocumentReference docRef = firestore.collection(collection).document(documentId);
		docRef.set(data);
	}
}
