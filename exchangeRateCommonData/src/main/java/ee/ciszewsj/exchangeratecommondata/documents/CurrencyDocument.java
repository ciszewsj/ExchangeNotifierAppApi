package ee.ciszewsj.exchangeratecommondata.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
import ee.ciszewsj.exchangeratecommondata.dto.CurrencyEntity;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDocument {
	public static final String CURRENCIES = "currencies";

	@JsonProperty(CURRENCIES)
	private List<CurrencyEntity> currenciesList;
}
