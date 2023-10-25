package ee.ciszewsj.exchangeratecommondata.rabbit;

import ee.ciszewsj.exchangeratecommondata.documents.CurrencyExchangeRateDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RabbitCurrencyDocumentWrapper {

	private String mainCurrency;
	private String secondaryCurrency;
	private CurrencyExchangeRateDocument document;

}
