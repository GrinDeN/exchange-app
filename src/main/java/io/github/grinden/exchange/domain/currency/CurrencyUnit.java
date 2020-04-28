package io.github.grinden.exchange.domain.currency;

public enum CurrencyUnit {

    PLN, USD;

    public boolean isPLN() {
        return this == PLN;
    }
}
