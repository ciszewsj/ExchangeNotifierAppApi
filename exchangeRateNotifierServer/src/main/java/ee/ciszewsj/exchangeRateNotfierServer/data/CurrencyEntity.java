package ee.ciszewsj.exchangeRateNotfierServer.data;

import lombok.Data;

@Data
public class CurrencyEntity {
	private String symbol;
	private String currency;
	private Boolean isMain;
}
