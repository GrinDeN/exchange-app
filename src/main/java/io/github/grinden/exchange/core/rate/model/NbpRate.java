package io.github.grinden.exchange.core.rate.model;

import java.util.List;

public class NbpRate {

    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;

    NbpRate() {
    }

    String getTable() {
        return table;
    }

    void setTable(final String table) {
        this.table = table;
    }

    String getCurrency() {
        return currency;
    }

    void setCurrency(final String currency) {
        this.currency = currency;
    }

    String getCode() {
        return code;
    }

    void setCode(final String code) {
        this.code = code;
    }

    List<Rate> getRates() {
        return rates;
    }

    void setRates(final List<Rate> rates) {
        this.rates = rates;
    }

    public Rate getRate() {
        return rates.get(0);
    }
}
