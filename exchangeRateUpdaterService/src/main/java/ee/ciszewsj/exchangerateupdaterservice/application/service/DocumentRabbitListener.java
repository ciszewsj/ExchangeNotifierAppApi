package ee.ciszewsj.exchangerateupdaterservice.application.service;

import ee.ciszewsj.exchangerateupdaterservice.application.service.notify.NotifierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentRabbitListener {

	private final ObjectMapper objectMapper;
	private final NotifierService notifierService;

	@RabbitListener
	public void listener(Message message) {
		try {
			CurrencyExchangeRateDocument rateDocument = objectMapper.readValue(message.getBody(), CurrencyExchangeRateDocument.class);
			notifierService.exchangeCurrencyUpdate(rateDocument);
		} catch (Exception e) {
			log.error("Could not process rabbit message due to {}", e, e);
		}
	}
}
