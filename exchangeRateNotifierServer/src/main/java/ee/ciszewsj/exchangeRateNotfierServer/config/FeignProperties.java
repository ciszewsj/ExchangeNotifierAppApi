package ee.ciszewsj.exchangeRateNotfierServer.config;

import feign.Logger;
import lombok.Data;

@Data
public class FeignProperties {
	private int connectTimeoutMillis;
	private int readTimeoutMillis;
	private Logger.Level logLevel;
}
