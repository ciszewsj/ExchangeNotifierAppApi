package ee.ciszewsj.exchangerateclient.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ciszewsj.exchangerateclient.data.response.*;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
public record ExchangeRateClientService(ExchangeRateFeignFacade feignFacade,
                                        String apiKey,
                                        ObjectMapper objectMapper) implements ExchangeRateClient {

	@Override
	public StandardResponse standardRequest(@NotNull String currency) throws ExchangeRateDataException {
		try (Response response = feignFacade.standardRequest(apiKey, currency)) {
			byte[] responseMsg;
			try {
				responseMsg = response.body().asInputStream().readAllBytes();
			} catch (IOException e) {
				log.error("Could not decode msg from StandardResponse {}", e, e);
				throw new ExchangeRateDataException();
			}
			if (response.status() == 200) {
				try {
					return objectMapper.readValue(responseMsg, StandardResponse.class);
				} catch (IOException e) {
					log.error("Could not read value from StandardResponse {}", e, e);
				}
			} else {
				String result = "UNKNOWN";
				String errorType = "UNKNOWN";
				try {
					ErrorResponse errorResponse = objectMapper.readValue(responseMsg, ErrorResponse.class);
					result = errorResponse.getResult();
					errorType = errorResponse.getErrorType();
				} catch (IOException e) {
					log.error("Could not read error from ErrorResponse {}", e, e);
				}
				log.warn("Received status {} from StandardResponse due to result=[{}] errorType=[{}]", response.status(), result, errorType);
			}
		}
		throw new ExchangeRateDataException();
	}

	@Override
	public HistoricalResponse historicalDataRequest(@NotNull String currency, int year, int
			month, int day) throws ExchangeRateDataException {
		try (Response response = feignFacade.historicalDataRequest(apiKey, currency, year, month, day)) {
			byte[] responseMsg;
			try {
				responseMsg = response.body().asInputStream().readAllBytes();
			} catch (IOException e) {
				log.error("Could not decode msg from HistoricalResponse {}", e, e);
				throw new ExchangeRateDataException();
			}
			if (response.status() == 200) {
				try {
					return objectMapper.readValue(responseMsg, HistoricalResponse.class);
				} catch (IOException e) {
					log.error("Could not read value from HistoricalResponse {}", e, e);
				}
			} else {
				String result = "UNKNOWN";
				String errorType = "UNKNOWN";
				try {
					ErrorResponse errorResponse = objectMapper.readValue(responseMsg, ErrorResponse.class);
					result = errorResponse.getResult();
					errorType = errorResponse.getErrorType();
				} catch (IOException e) {
					log.error("Could not read error from ErrorResponse {}", e, e);
				}
				log.warn("Received status {} from HistoricalResponse due to result=[{}] errorType=[{}]", response.status(), result, errorType);
			}
		}
		throw new ExchangeRateDataException();
	}

	@Override
	public SupportedCodeResponse supportedCodes() throws ExchangeRateDataException {
		try (Response response = feignFacade.supportedCodes(apiKey)) {
			byte[] responseMsg;
			try {
				responseMsg = response.body().asInputStream().readAllBytes();
			} catch (IOException e) {
				log.error("Could not decode msg from SupportedCodeResponse {}", e, e);
				throw new ExchangeRateDataException();
			}
			if (response.status() == 200) {
				try {
					return objectMapper.readValue(responseMsg, SupportedCodeResponse.class);
				} catch (IOException e) {
					log.error("Could not read value from SupportedCodeResponse {}", e, e);
				}
			} else {
				String result = "UNKNOWN";
				String errorType = "UNKNOWN";
				try {
					ErrorResponse errorResponse = objectMapper.readValue(responseMsg, ErrorResponse.class);
					result = errorResponse.getResult();
					errorType = errorResponse.getErrorType();
				} catch (IOException e) {
					log.error("Could not read error from ErrorResponse {}", e, e);
				}
				log.warn("Received status {} from SupportedCodeResponse due to result=[{}] errorType=[{}]", response.status(), result, errorType);
			}
		}
		throw new ExchangeRateDataException();
	}

	@Override
	public ApiRequestQuotaResponse apiRequestQuota() throws ExchangeRateDataException {
		try (Response response = feignFacade.apiRequestQuota(apiKey)) {
			byte[] responseMsg;
			try {
				responseMsg = response.body().asInputStream().readAllBytes();
			} catch (IOException e) {
				log.error("Could not decode msg from ApiRequestQuotaResponse {}", e, e);
				throw new ExchangeRateDataException();
			}
			if (response.status() == 200) {
				try {
					return objectMapper.readValue(responseMsg, ApiRequestQuotaResponse.class);
				} catch (IOException e) {
					log.error("Could not read value from ApiRequestQuotaResponse {}", e, e);
				}
			} else {
				String result = "UNKNOWN";
				String errorType = "UNKNOWN";
				try {
					ErrorResponse errorResponse = objectMapper.readValue(responseMsg, ErrorResponse.class);
					result = errorResponse.getResult();
					errorType = errorResponse.getErrorType();
				} catch (IOException e) {
					log.error("Could not read error from ErrorResponse {}", e, e);
				}
				log.warn("Received status {} from ApiRequestQuotaResponse due to result=[{}] errorType=[{}]", response.status(), result, errorType);
			}
		}
		throw new ExchangeRateDataException();
	}
}
