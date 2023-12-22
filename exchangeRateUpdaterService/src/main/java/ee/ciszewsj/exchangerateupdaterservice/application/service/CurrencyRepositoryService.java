package ee.ciszewsj.exchangerateupdaterservice.application.service;

import ee.ciszewsj.exchangerateupdaterservice.data.CurrencyRateDto;
import ee.ciszewsj.exchangerateupdaterservice.data.db.NewestCurrencyRateEntity;
import ee.ciszewsj.exchangerateupdaterservice.data.db.NewestCurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyRepositoryService {
	private final NewestCurrencyRateRepository rateRepository;

	@Transactional
	public Optional<CurrencyRateDto> getRateByCurrencies(String mainCurrency, String secondCurrency) {
		Optional<NewestCurrencyRateEntity> rateEntity = rateRepository.findFirstByMainCurrencyAndSecondCurrency(mainCurrency, secondCurrency);
		Optional<CurrencyRateDto> response;

		if (rateEntity.isPresent()) {
			NewestCurrencyRateEntity entity = rateEntity.get();
			response = Optional.of(CurrencyRateDto.builder().mainCurrency(entity.getMainCurrency())
					.secondCurrency(entity.getSecondCurrency())
					.rate(entity.getExchangeRate())
					.build());
		} else {
			response = Optional.empty();
		}
		return response;
	}

	@Transactional
	public void updateRateByCurrencies(String mainCurrency, String secondCurrency, Double rate) {
		NewestCurrencyRateEntity entity;
		Optional<NewestCurrencyRateEntity> rateEntity = rateRepository.findFirstByMainCurrencyAndSecondCurrency(mainCurrency, secondCurrency);
		entity = rateEntity.orElseGet(() -> new NewestCurrencyRateEntity(mainCurrency, secondCurrency));
		entity.setExchangeRate(rate);
		rateRepository.save(entity);
	}
}
