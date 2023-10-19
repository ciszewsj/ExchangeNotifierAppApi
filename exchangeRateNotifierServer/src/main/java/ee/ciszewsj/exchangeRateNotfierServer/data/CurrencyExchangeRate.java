package ee.ciszewsj.exchangeRateNotfierServer.data;

import lombok.Data;

import java.util.Date;

@Data
public class CurrencyExchangeRate {
	private String currencyMainSymbol;
	private String currencySecondSymbol;
	private Date date;
	private int rate;
}
