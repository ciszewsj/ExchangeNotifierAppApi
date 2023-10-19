package ee.ciszewsj.exchangerateclient.data.response;

import lombok.Data;

@Data
public class ApiRequestQuotaResponse {
	private String result;
	private String documentation;
	private String termsOfUse;
	private Integer planQuota;
	private Integer requestsRemaining;
	private Integer refreshDayOfMonth;

}
