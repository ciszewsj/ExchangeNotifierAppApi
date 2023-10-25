package ee.ciszewsj.exchangerateclient.data.props;

import lombok.Data;

@Data
public class ExchangeRateProperties {
	private String apiKey;
	private String url;
	private String cronUpdater;
}
