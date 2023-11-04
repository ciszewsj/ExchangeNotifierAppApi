package com.example.exchangerateupdaterservice.data.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewestCurrencyRateRepository extends JpaRepository<NewestCurrencyRateEntity, Long> {

	Optional<NewestCurrencyRateEntity> findFirstByMainCurrencyAndSecondCurrency(String mainCurrency, String secondCurrency);
}
