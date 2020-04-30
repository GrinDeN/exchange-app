package io.github.grinden.exchange.domain.rate;

import io.github.grinden.exchange.domain.currency.CurrencyUnit;
import io.github.grinden.exchange.domain.rate.model.NbpRate;

import java.time.LocalDate;

public interface RateService {

    NbpRate getCurrentRate(CurrencyUnit currency, final LocalDate date);
}
