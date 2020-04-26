package io.github.grinden.exchange.core.exchange;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExchangeServiceImpl implements ExchangeService {


    public ExchangeServiceImpl() {
    }

    @Override
    public String exchange(final String currentCurrency, final String desiredCurrency, final BigDecimal amount) {
        return null;
    }
}
