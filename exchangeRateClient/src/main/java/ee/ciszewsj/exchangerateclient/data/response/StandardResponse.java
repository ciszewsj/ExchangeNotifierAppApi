package ee.ciszewsj.exchangerateclient.data.response;

import lombok.Data;

import java.util.Map;

@Data
public class StandardResponse {
	private String result;
	private String documentation;
	private String termsOfUse;
	private Long timeLastUpdateUnix;
	private String timeLastUpdateUtc;
	private Long timeNextUpdateUnix;
	private String timeNextUpdateUtc;
	private String baseCode;
	private Map<String, Double> conversionRates;
}
