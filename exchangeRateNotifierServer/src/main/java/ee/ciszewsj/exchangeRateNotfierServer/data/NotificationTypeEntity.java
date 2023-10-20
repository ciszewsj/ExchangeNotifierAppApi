package ee.ciszewsj.exchangeRateNotfierServer.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NotificationTypeEntity {
	public static final String TYPE_NAME = "type_name";
	public static final String OPTIONS = "options";

	@JsonProperty(TYPE_NAME)
	private String typeName;
	@JsonProperty(OPTIONS)
	private List<OptionEntity> options;
}
