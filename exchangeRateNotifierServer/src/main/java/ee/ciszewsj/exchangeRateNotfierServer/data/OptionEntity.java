package ee.ciszewsj.exchangeRateNotfierServer.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OptionEntity {
	public String name;
	public FieldType type;
	public List<String> possibleValues;

	public enum FieldType {
		HOUR_PICKER,
		DATE_PICKER,
		DOUBLE_VALUE,
		PERCENT_VALUE,
		OPTIONS_VALUE
	}
}
