package ee.ciszewsj.exchangeratecommondata.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CurrenciesSettingsEntity {
	private List<CurrencyEntity> currencies;
}
