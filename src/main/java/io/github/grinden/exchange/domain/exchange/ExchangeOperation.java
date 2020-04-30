package io.github.grinden.exchange.domain.exchange;

import io.github.grinden.exchange.domain.currency.CurrencyUnit;
import io.github.grinden.exchange.domain.exchange.model.ExchangeType;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ExchangeOperation {

    @PESEL
    private String pesel;

    @NotNull
    private ExchangeType exchangeType;

    @NotNull
    private CurrencyUnit currency;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
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
