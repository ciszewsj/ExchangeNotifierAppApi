package ee.ciszewsj.exchangeratecommondata.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
import ee.ciszewsj.exchangeratecommondata.dto.ExchangeCurrencyRateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchangeRateDocument {
	public static final String EXCHANGE_RATE_NAME = "exchangeRates";

	@JsonProperty(EXCHANGE_RATE_NAME)
	private List<ExchangeCurrencyRateEntity> exchangeRates;
}
