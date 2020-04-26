package io.github.grinden.exchange.core.subaccount;

import io.github.grinden.exchange.core.account.model.Account;
import io.github.grinden.exchange.core.currency.CurrencyUnit;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "subaccounts")
public class SubAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private CurrencyUnit currency;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "FK_AccountId")
    private Account account;

    SubAccount() {
    }

    public SubAccount(final CurrencyUnit currency, final BigDecimal amount, final Account account) {
        this.currency = currency;
        this.amount = amount;
        this.account = account;
    }

    Long getId() {
        return id;
    }

    void setId(final Long id) {
        this.id = id;
    }

    CurrencyUnit getCurrency() {
        return currency;
    }

    void setCurrency(final CurrencyUnit currency) {
        this.currency = currency;
    }

    BigDecimal getAmount() {
        return amount;
    }

    void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }
}
