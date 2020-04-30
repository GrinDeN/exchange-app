package io.github.grinden.exchange.domain.account;

import io.github.grinden.exchange.domain.subaccount.SubAccount;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @PESEL(message = "Please check whether PESEL is correct")
    private String pesel;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Transient
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SubAccount> subAccounts;

    Account() {
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(final String pesel) {
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    void setSurname(final String surname) {
        this.surname = surname;
    }

    public List<SubAccount> getSubAccounts() {
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
