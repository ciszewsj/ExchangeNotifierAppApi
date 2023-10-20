package ee.ciszewsj.exchangerateclient.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
	@JsonProperty("result")
	private String result;
	@JsonProperty("error-type")
	private String errorType;
}
