package ee.ciszewsj.exchangeRateNotfierServer.application.controller;


import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdminController {
	private final Firestore firestore;

	@Data
	@Builder
	public static class Test {
		private String name;
		private String surname;
	}

	@GetMapping
	public void create() {
		saveData("TestCollection", "1", Test.builder().name("Name").surname("Kowalski").build());

	}

	@PutMapping
	public void addMainCurrency() {

	}

	public void saveData(String collection, String documentId, Object data) {
		DocumentReference docRef = firestore.collection(collection).document(documentId);
		docRef.set(data);

	}
}
