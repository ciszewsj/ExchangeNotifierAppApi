package ee.ciszewsj.exchangeratecommondata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateEntity {
	private Date time;
	private CurrencyEntity currency;
}
