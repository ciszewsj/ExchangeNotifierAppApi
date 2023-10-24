package ee.ciszewsj.exchangeratecommondata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@Builder
public class ExchangeCurrencyRateEntity {
	public static final String UUID = "uuid";
	public static final String SYMBOL = "symbol";
	public static final String DATE = "date";
	public static final String EXCHANGE_RATES = "exchange_rates";

	@JsonProperty(UUID)
	private String uuid;
	@JsonProperty(SYMBOL)
	private String symbol;
	@JsonProperty(DATE)
	private Date date;
	@JsonProperty(EXCHANGE_RATES)
	private Map<String, Double> exchangeRates;
}
