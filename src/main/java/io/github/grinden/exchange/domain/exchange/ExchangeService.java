package io.github.grinden.exchange.domain.exchange;

import io.github.grinden.exchange.domain.currency.CurrencyUnit;
import io.github.grinden.exchange.domain.subaccount.SubAccount;

import java.util.Map;

public interface ExchangeService {

    void exchange(ExchangeOperation exchangeOperation, final Map<CurrencyUnit, SubAccount> subAccountMap);
}
