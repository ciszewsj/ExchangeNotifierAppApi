package ee.ciszewsj.exchangerateclient.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SupportedCodeResponse {
	@JsonProperty("result")
	private String result;
	@JsonProperty("documentation")
	private String documentation;
	@JsonProperty("terms_of_use")
	private String termsOfUse;
	@JsonProperty("supported_codes")
	private List<List<String>> supportedCodes;
}
