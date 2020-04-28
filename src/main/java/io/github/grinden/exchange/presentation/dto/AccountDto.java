package io.github.grinden.exchange.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.grinden.exchange.domain.account.model.Account;

import java.util.List;
import java.util.stream.Collectors;

public class AccountDto {

    @JsonProperty("pesel")
    private String pesel;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("subAccounts")
    private List<SubAccountDto> subAccounts;

    AccountDto() {
    }

    public static AccountDto of(Account entity) {
        AccountDto accountDto = new AccountDto();
        accountDto.setPesel(entity.getPesel());
        accountDto.setName(entity.getName());
        accountDto.setSurname(entity.getSurname());
        accountDto.setSubAccounts(entity.getSubAccounts().stream().map(SubAccountDto::of).collect(Collectors.toList()));
        return accountDto;
    }

    String getPesel() {
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

    List<SubAccountDto> getSubAccounts() {
        return subAccounts;
    }

    void setSubAccounts(final List<SubAccountDto> subAccounts) {
        this.subAccounts = subAccounts;
    }
}
