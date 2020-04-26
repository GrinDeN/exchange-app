package io.github.grinden.exchange.core.subaccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.grinden.exchange.core.currency.CurrencyUnit;

import java.math.BigDecimal;

public class SubAccountDto {

    @JsonProperty("currency")
    private CurrencyUnit currency;

    @JsonProperty("amount")
    private BigDecimal amount;

    SubAccountDto() {
    }

    public static SubAccountDto of(SubAccount entity) {
        SubAccountDto subAccountDto = new SubAccountDto();
        subAccountDto.setCurrency(entity.getCurrency());
        subAccountDto.setAmount(entity.getAmount());
        return subAccountDto;
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
