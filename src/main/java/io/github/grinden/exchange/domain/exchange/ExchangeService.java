package io.github.grinden.exchange.domain.exchange;

import io.github.grinden.exchange.domain.exchange.model.ExchangeOperation;

public interface ExchangeService {

    void exchange(ExchangeOperation exchangeOperation);
}
