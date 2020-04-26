package io.github.grinden.exchange.core.exchange.model;

import io.github.grinden.exchange.core.currency.CurrencyUnit;
import io.github.grinden.exchange.core.exchange.ExchangeType;
import org.hibernate.validator.constraints.pl.PESEL;

import java.math.BigDecimal;

public class ExchangeOperation {

    @PESEL
    private String pesel;
    private CurrencyUnit fromCurrency;
    private CurrencyUnit toCurrency;
    private BigDecimal amountToTrade;
    private ExchangeType exchangeType;

    ExchangeOperation() {
    }

    public CurrencyUnit getFromCurrency() {
        return fromCurrency;
    }

    void setFromCurrency(final CurrencyUnit fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public CurrencyUnit getToCurrency() {
        return toCurrency;
    }

    void setToCurrency(final CurrencyUnit toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getAmountToTrade() {
        return amountToTrade;
    }

    void setAmountToTrade(final BigDecimal amountToTrade) {
        this.amountToTrade = amountToTrade;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    void setExchangeType(final ExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getPesel() {
        return pesel;
    }

    void setPesel(final String pesel) {
        this.pesel = pesel;
    }
}
