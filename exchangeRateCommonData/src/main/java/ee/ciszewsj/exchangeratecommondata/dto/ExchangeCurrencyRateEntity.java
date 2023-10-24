package ee.ciszewsj.exchangeratecommondata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeCurrencyRateEntity {
	public static final String DATE = "date";
	public static final String EXCHANGE_RATES = "rate";

	@JsonProperty(DATE)
	private Date date;
	@JsonProperty(EXCHANGE_RATES)
	private Double rate;
}
