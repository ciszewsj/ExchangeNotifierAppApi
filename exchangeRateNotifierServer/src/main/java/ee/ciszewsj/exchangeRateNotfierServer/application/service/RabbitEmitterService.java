package ee.ciszewsj.exchangeRateNotfierServer.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import ee.ciszewsj.exchangeratecommondata.rabbit.RabbitCurrencyDocumentWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static ee.ciszewsj.exchangeRateNotfierServer.config.RabbitConfig.EXCHANGE_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitEmitterService {
	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;

	public void emitEvent(String mainCurrency, String secondaryCurrency, CurrencyExchangeRateDocument document) {
		RabbitCurrencyDocumentWrapper wrappedDocument = RabbitCurrencyDocumentWrapper.builder()
				.mainCurrency(mainCurrency)
				.secondaryCurrency(secondaryCurrency)
				.document(document)
				.build();

		try {
			String serializedDocument = objectMapper.writeValueAsString(wrappedDocument);
			rabbitTemplate.convertAndSend(EXCHANGE_NAME, mainCurrency, serializedDocument);
		} catch (Exception e) {
			log.error("Could not emit event due to  {}", e, e);
		}

	}
}
