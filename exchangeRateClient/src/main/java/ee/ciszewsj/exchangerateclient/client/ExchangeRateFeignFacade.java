package ee.ciszewsj.exchangerateclient.client;

import feign.*;

public interface ExchangeRateFeignFacade {

	@RequestLine("GET /v6/{api_key}/latest/{currency}")
	Response standardRequest(@Param("api_key") String apiKey,
	                         @Param("currency") String currency);

	@RequestLine("GET /v6/{api_key}/history/{currency}/{year}/{month}/{day}")
	Response historicalDataRequest(@Param("api_key") String apiKey,
	                               @Param("currency") String currency,
	                               @Param("year") int year,
	                               @Param("month") int month,
	                               @Param("day") int day);

	@RequestLine("GET /v6/{api_key}/codes")
	Response supportedCodes(@Param("api_key") String apiKey);

	@RequestLine("GET /v6/{api_key}/quota")
	Response apiRequestQuota(@Param("api_key") String apiKey);
}
