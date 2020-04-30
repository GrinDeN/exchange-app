package io.github.grinden.exchange.domain.account;

import io.github.grinden.exchange.configuration.InvalidExchangeArgumentException;
import io.github.grinden.exchange.domain.account.model.AccountRepository;
import io.github.grinden.exchange.domain.currency.CurrencyUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    @Test
    @DisplayName("Register account with same pesel twice throws an InvalidExchangeArgument exception")
    void registerAccount_shouldThrowExceptionIfAccountAlreadyExists() {
        // given
        String pesel = "74040988413";
        AccountRepository mockedRepo = mock(AccountRepository.class);
        when(mockedRepo.existsById(pesel)).thenReturn(true);

        AccountService accountService = new AccountServiceImpl(mockedRepo);
        Account mockedAcc = new Account();
        mockedAcc.setPesel(pesel);

        // when & then
        assertThatThrownBy(() -> accountService.registerAccount(mockedAcc))
                .isInstanceOf(InvalidExchangeArgumentException.class)
                .hasMessageContaining("already exist");

    }

    @Test
    @DisplayName("Register account should have 2 accounts in PLN and USD")
    void registerAccount_shouldHaveSubAccountsWithPLN_and_USD() {
        // given
        String pesel = "74040988413";
        String startingPLNAmount = "19";
        String startingUSDAmount = "0";
        AccountRepository mockedRepo = mock(AccountRepository.class);
        when(mockedRepo.existsById(pesel)).thenReturn(false);

        AccountService accountService = new AccountServiceImpl(mockedRepo);
        Account mockedAcc = new Account();
        mockedAcc.setPesel(pesel);
        mockedAcc.setAmount(new BigDecimal(startingPLNAmount));

        // when
        accountService.registerAccount(mockedAcc);

        // then
        assertThat(mockedAcc.getSubAccounts()).hasSize(2);
        assertThat(mockedAcc.getSubAccounts()).element(0)
                .hasFieldOrPropertyWithValue("currency", CurrencyUnit.PLN)
                .hasFieldOrPropertyWithValue("amount", new BigDecimal(startingPLNAmount).setScale(2));
        assertThat(mockedAcc.getSubAccounts()).element(1)
                .hasFieldOrPropertyWithValue("currency", CurrencyUnit.USD)
                .hasFieldOrPropertyWithValue("amount", new BigDecimal(startingUSDAmount).setScale(2));
    }
}