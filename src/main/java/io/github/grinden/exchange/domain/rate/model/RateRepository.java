package io.github.grinden.exchange.domain.rate.model;

import io.github.grinden.exchange.domain.currency.CurrencyUnit;

public interface RateRepository {

    NbpRate getRateFromNbp(CurrencyUnit currency);
}
