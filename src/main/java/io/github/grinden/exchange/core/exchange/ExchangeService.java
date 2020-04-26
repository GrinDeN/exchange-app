package io.github.grinden.exchange.core.exchange;

import io.github.grinden.exchange.core.exchange.model.ExchangeOperation;

public interface ExchangeService {

    void exchange(ExchangeOperation exchangeOperation);
}
