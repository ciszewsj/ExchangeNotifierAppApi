package ee.ciszewsj.exchangerateclient.data.response;

import lombok.Data;

import java.util.Map;

@Data
public class HistoricalResponse {
	private String result;
	private String documentation;
	private String termsOfUse;
	private int year;
	private int month;
	private int day;
	private String baseCode;
	private Map<String, Double> conversionRates;
}
