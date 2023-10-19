package ee.ciszewsj.exchangerateclient.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ciszewsj.exchangerateclient.data.response.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
public class ExchangeRateClientService implements ExchangeRateClient {

	private final ExchangeRateFeignFacade feignFacade;
	private final String apiKey;
	private final ObjectMapper objectMapper;

	public ExchangeRateClientService(ExchangeRateFeignFacade feignFacade,
	                                 String apiKey,
	                                 ObjectMapper objectMapper) {
		this.feignFacade = feignFacade;
		this.apiKey = apiKey;
		this.objectMapper = objectMapper;
	}


	@Override
	public StandardResponse standardRequest(@NotNull String currency) {
		var response = feignFacade.standardRequest(apiKey, currency);
		byte[] responseMsg;
		try {
			responseMsg = response.body().asInputStream().readAllBytes();
		} catch (IOException e) {
			log.error("Could not decode msg from StandardResponse {}", e, e);
			throw new IllegalStateException();
		}
		if (response.status() == 200) {
			try {
				return objectMapper.readValue(responseMsg, StandardResponse.class);
			} catch (IOException e) {
				log.error("Could not read value from StandardResponse {}", e, e);
				throw new IllegalStateException();
			}
		} else {
			String result = "UNKNOWN";
			String errorType = "UNKNOWN";
			try {
				ErrorResponse errorResponse = objectMapper.readValue(responseMsg, ErrorResponse.class);
				result = errorResponse.getResult();
				errorType = errorResponse.getErrorType();
			} catch (IOException e) {
				log.error("Could not read error from StandardResponse {}", e, e);
			}
			log.warn("Received status {} from StandardResponse due to result=[{}] errorType=[{}]", response.status(), result, errorType);

			throw new IllegalStateException();
		}
	}

	@Override
	public HistoricalResponse historicalDataRequest(@NotNull String currency, @NotNull String year, @NotNull String
			month, @NotNull String day) {
		feignFacade.historicalDataRequest(apiKey,
				currency,
				year,
				month,
				day);
		return null;
	}

	@Override
	public SupportedCodeResponse supportedCodes() {
		feignFacade.supportedCodes(apiKey);
		return null;
	}

	@Override
	public ApiRequestQuotaResponse apiRequestQuota() {
		feignFacade.apiRequestQuota(apiKey);
		return null;
	}
}
