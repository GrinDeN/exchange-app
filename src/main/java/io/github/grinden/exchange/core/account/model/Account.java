package io.github.grinden.exchange.core.account.model;

import io.github.grinden.exchange.core.subaccount.SubAccount;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @PESEL(message = "Please check whether PESEL is correct")
    private String pesel;

    private String name;

    private String surname;

    @Transient
    private BigDecimal amount;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SubAccount> subAccounts;

    Account() {
    }

    public String getPesel() {
        return pesel;
    }

    void setPesel(final String pesel) {
        this.pesel = pesel;
    }

    String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }

    String getSurname() {
        return surname;
    }

    void setSurname(final String surname) {
        this.surname = surname;
    }

    List<SubAccount> getSubAccounts() {
        return subAccounts;
    }

    public void setSubAccounts(final List<SubAccount> subAccounts) {
        this.subAccounts = subAccounts;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }
}