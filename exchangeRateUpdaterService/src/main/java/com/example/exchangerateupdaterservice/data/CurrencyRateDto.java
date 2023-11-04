package com.example.exchangerateupdaterservice.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyRateDto {
	private String mainCurrency;
	private String secondCurrency;
	private Double rate;
}
