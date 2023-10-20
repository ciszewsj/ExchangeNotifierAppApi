package ee.ciszewsj.exchangeRateNotfierServer.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyEntity {
	private String symbol;
	private String currency;
	private boolean isMain;
}
