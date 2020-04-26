package io.github.grinden.exchange.core.exchange;

import java.math.BigDecimal;

public interface ExchangeService {

    String exchange(String currentCurrency, String desiredCurrency, BigDecimal amount);
}
