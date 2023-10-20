package ee.ciszewsj.exchangerateclient.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRequestQuotaResponse {
	@JsonProperty("result")
	private String result;
	@JsonProperty("documentation")
	private String documentation;
	@JsonProperty("terms_of_use")
	private String termsOfUse;
	@JsonProperty("plan_quota")
	private Integer planQuota;
	@JsonProperty("requests_remaining")
	private Integer requestsRemaining;
	@JsonProperty("refresh_day_of_month")
	private Integer refreshDayOfMonth;

}
