package ee.ciszewsj.exchangeratecommondata.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CurrencyRateEntity {
	private Date time;
	private CurrencyEntity currency;
}
