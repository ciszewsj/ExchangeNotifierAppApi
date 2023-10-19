package ee.ciszewsj.exchangerateclient.data.response;

import lombok.Data;

@Data
public class ErrorResponse {
	private String result;
	private String errorType;
}
