package io.github.grinden.exchange.domain.subaccount;

import io.github.grinden.exchange.configuration.InvalidExchangeArgumentException;
import io.github.grinden.exchange.domain.currency.CurrencyUnit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubAccountTest {

    @Test
    void addFunds_shouldGiveSumOfAvailbleAmountAndNewAmount() {
        // given
        SubAccount subAccount = new SubAccount(CurrencyUnit.PLN, new BigDecimal("19").setScale(2), null);

        // when
        subAccount.addFunds(new BigDecimal("10.55"));

        // then
        assertThat(subAccount.getAmount()).isEqualTo(new BigDecimal("29.55"));
    }

    @Test
    void subtractFunds_shouldGiveSubtractOfAvailableAmountAndNewAmount() {
        // given
        SubAccount subAccount = new SubAccount(CurrencyUnit.PLN, new BigDecimal("19").setScale(2), null);

        // when
        subAccount.subtractFunds(new BigDecimal("10.55"));

        // then
        assertThat(subAccount.getAmount()).isEqualTo(new BigDecimal("8.45"));
    }

    @Test
    void subtractFunds_shouldThrowAnExceptionIfThereIsNotEnoughAvailableAmount() {
        // given
        SubAccount subAccount = new SubAccount(CurrencyUnit.PLN, new BigDecimal("9").setScale(2), null);

        // when & then
        assertThatThrownBy(() -> subAccount.subtractFunds(new BigDecimal("10.55")))
                .isInstanceOf(InvalidExchangeArgumentException.class)
                .hasMessageContaining("Not enough funds");


    }
}