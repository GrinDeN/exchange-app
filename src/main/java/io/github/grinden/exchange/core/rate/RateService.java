package io.github.grinden.exchange.core.rate;

import io.github.grinden.exchange.core.currency.CurrencyUnit;
import io.github.grinden.exchange.core.rate.model.NbpRate;

public interface RateService {

    NbpRate getCurrentRate(CurrencyUnit currency);
}
