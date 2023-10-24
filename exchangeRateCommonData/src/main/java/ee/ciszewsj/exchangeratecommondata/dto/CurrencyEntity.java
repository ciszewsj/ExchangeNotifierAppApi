package ee.ciszewsj.exchangeratecommondata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyEntity {

	public static final String SYMBOL = "symbol";
	public static final String CURRENCY = "currency";
	public static final String IS_MAIN = "main";

	@JsonProperty(SYMBOL)
	private String symbol;
	@JsonProperty(CURRENCY)
	private String currency;
	@JsonProperty(IS_MAIN)
	private boolean isMain;
}
