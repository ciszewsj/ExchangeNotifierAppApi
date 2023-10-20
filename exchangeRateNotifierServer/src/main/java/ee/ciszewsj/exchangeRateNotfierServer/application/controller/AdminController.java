package ee.ciszewsj.exchangeRateNotfierServer.application.controller;


import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdminController {
	private final Firestore firestore;

	@PostMapping("/loadCurrenciesList")
	public void addMainCurrency() {

	}

	public void saveData(String collection, String documentId, Object data) {
		DocumentReference docRef = firestore.collection(collection).document(documentId);
		docRef.set(data);

	}
}
