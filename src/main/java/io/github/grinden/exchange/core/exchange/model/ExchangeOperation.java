package io.github.grinden.exchange.core.exchange.model;

import io.github.grinden.exchange.core.currency.CurrencyUnit;
import io.github.grinden.exchange.core.exchange.ExchangeType;
import org.hibernate.validator.constraints.pl.PESEL;

import java.math.BigDecimal;

public class ExchangeOperation {

    @PESEL
    private String pesel;
    private ExchangeType exchangeType;
    private CurrencyUnit currency;
    private BigDecimal amountToTrade;

    ExchangeOperation() {
    }

    public CurrencyUnit getCurrency() {
        return currency;
    }

    void setCurrency(final CurrencyUnit currency) {
        this.currency = currency;
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
