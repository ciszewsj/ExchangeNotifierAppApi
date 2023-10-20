package ee.ciszewsj.exchangerateclient.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandardResponse {
	@JsonProperty("result")
	private String result;
	@JsonProperty("documentation")
	private String documentation;
	@JsonProperty("terms_of_use")
	private String termsOfUse;
	@JsonProperty("time_last_update_unix")
	private Long timeLastUpdateUnix;
	@JsonProperty("time_last_update_utc")
	private String timeLastUpdateUtc;
	@JsonProperty("time_next_update_unix")
	private Long timeNextUpdateUnix;
	@JsonProperty("time_next_update_utc")
	private String timeNextUpdateUtc;
	@JsonProperty("base_code")
	private String baseCode;
	@JsonProperty("conversion_rates")
	private Map<String, Double> conversionRates;
}
