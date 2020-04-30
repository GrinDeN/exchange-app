package io.github.grinden.exchange.domain.rate.model;

import io.github.grinden.exchange.domain.currency.CurrencyUnit;

import java.time.LocalDate;

public interface RateRepository {

    NbpRate getRate(CurrencyUnit currency, LocalDate date);
}
