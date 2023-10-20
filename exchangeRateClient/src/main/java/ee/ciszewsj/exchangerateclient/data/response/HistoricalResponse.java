package ee.ciszewsj.exchangerateclient.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalResponse {
	@JsonProperty("result")
	private String result;
	@JsonProperty("documentation")
	private String documentation;
	@JsonProperty("terms_of_use")
	private String termsOfUse;
	@JsonProperty("year")
	private int year;
	@JsonProperty("month")
	private int month;
	@JsonProperty("day")
	private int day;
	@JsonProperty("base_code")
	private String baseCode;
	@JsonProperty("conversion_rates")
	private Map<String, Double> conversionRates;
}
