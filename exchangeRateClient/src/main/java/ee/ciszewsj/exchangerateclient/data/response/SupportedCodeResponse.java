package ee.ciszewsj.exchangerateclient.data.response;

import lombok.Data;

import java.util.List;

@Data
public class SupportedCodeResponse {
	private String result;
	private String documentation;
	private String termsOfUse;
	private List<List<String>> supportedCodes;
}
