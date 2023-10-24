package ee.ciszewsj.exchangeratecommondata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OptionEntity {
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String POSSIBLE_VALUES = "possible_values";

	@JsonProperty(NAME)
	public String name;
	@JsonProperty(TYPE)
	public FieldType type;
	@JsonProperty(POSSIBLE_VALUES)
	public List<String> possibleValues;

	public enum FieldType {
		HOUR_PICKER,
		DATE_PICKER,
		DOUBLE_VALUE,
		PERCENT_VALUE,
		OPTIONS_VALUE
	}
}
