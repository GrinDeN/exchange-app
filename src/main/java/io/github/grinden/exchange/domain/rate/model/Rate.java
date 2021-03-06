package io.github.grinden.exchange.domain.rate.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Rate {

    private String no;
    private LocalDate effectiveDate;
    private BigDecimal bid;
    private BigDecimal ask;

    Rate() {
    }

    public Rate(BigDecimal bid, BigDecimal ask) {
        this.bid = bid;
        this.ask = ask;
    }

    String getNo() {
        return no;
    }

    void setNo(final String no) {
        this.no = no;
    }

    LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    void setEffectiveDate(final LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public BigDecimal getBid() {
        return bid;
    }

    void setBid(final BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    void setAsk(final BigDecimal ask) {
        this.ask = ask;
    }
}
