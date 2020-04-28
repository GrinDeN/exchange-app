package io.github.grinden.exchange.domain.subaccount;

import io.github.grinden.exchange.configuration.InvalidExchangeArgument;
import io.github.grinden.exchange.domain.account.model.Account;
import io.github.grinden.exchange.domain.currency.CurrencyUnit;

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

    public CurrencyUnit getCurrency() {
        return currency;
    }

    void setCurrency(final CurrencyUnit currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public void addFunds(BigDecimal amountToAdd) {
        BigDecimal updatedAmount = this.amount.add(amountToAdd);
        this.setAmount(updatedAmount);
    }

    public void subtractFunds(BigDecimal amountToSubtract) {
        if (this.amount.compareTo(amountToSubtract) < 0) {
            throw new InvalidExchangeArgument("Not enough funds on subaccount in currency: " + this.currency);
        }
        BigDecimal updatedAmount = this.amount.subtract(amountToSubtract);
        this.setAmount(updatedAmount);
    }
}
