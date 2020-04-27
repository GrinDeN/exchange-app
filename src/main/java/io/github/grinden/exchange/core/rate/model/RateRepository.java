package io.github.grinden.exchange.core.rate.model;

import io.github.grinden.exchange.core.currency.CurrencyUnit;

public interface RateRepository {

    NbpRate getRateFromNbp(CurrencyUnit currency);
}
