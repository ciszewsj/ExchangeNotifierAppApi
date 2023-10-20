package ee.ciszewsj.exchangeRateNotfierServer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateClient;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateClientService;
import ee.ciszewsj.exchangerateclient.client.ExchangeRateFeignFacade;
import ee.ciszewsj.exchangerateclient.data.props.ExchangeRateProperties;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class ExchangeRateApiConfig {

	@Bean
	public ExchangeRateClient exchangeRateClient(
			ExchangeRateFeignFacade feign,
			ObjectMapper feignObjectMapper,
			ExchangeRateProperties exchangeRateProperties
	) {
		return new ExchangeRateClientService(feign, exchangeRateProperties.getApiKey(), feignObjectMapper);
	}

	@Bean
	public ExchangeRateFeignFacade feign(ObjectMapper feignObjectMapper,
	                                     FeignProperties feignProperties,
	                                     ExchangeRateProperties exchangeRateProperties) {
		return Feign.builder()
				.client(new OkHttpClient(new okhttp3.OkHttpClient.Builder().build()))
				.encoder(new JacksonEncoder(feignObjectMapper))
				.decoder(new JacksonDecoder(feignObjectMapper))
				.retryer(new Retryer.Default.Default(100, SECONDS.toMillis(1), 2))
				.options(new Request.Options(feignProperties.getConnectTimeoutMillis(), feignProperties.getReadTimeoutMillis()))
				.logger(new Slf4jLogger(ExchangeRateFeignFacade.class))
				.logLevel(feignProperties.getLogLevel())
				.target(ExchangeRateFeignFacade.class, exchangeRateProperties.getUrl());
	}

	@Bean
	public ObjectMapper feignObjectMapper() {
		return new ObjectMapper();
	}

	@Bean
	@ConfigurationProperties("app.exchange-rate.api")
	public ExchangeRateProperties exchangeRateProperties() {
		return new ExchangeRateProperties();
	}

	@Bean
	@ConfigurationProperties("app.feign-config")
	public FeignProperties feignProperties() {
		return new FeignProperties();
	}
}
