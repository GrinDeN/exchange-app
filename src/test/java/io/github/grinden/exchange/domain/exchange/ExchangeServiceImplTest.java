package io.github.grinden.exchange.domain.exchange;

import io.github.grinden.exchange.domain.currency.CurrencyUnit;
import io.github.grinden.exchange.domain.exchange.model.ExchangeType;
import io.github.grinden.exchange.domain.rate.RateServiceImpl;
import io.github.grinden.exchange.domain.rate.model.NbpRate;
import io.github.grinden.exchange.domain.rate.model.Rate;
import io.github.grinden.exchange.domain.subaccount.SubAccount;
import io.github.grinden.exchange.domain.subaccount.SubAccountRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExchangeServiceImplTest {

    @Test
    void exchange_shouldNotThrowExceptionForProperParametersForBuyOperation() {
        // given
        Map<CurrencyUnit, SubAccount> mockedMap = new HashMap<>();
        mockedMap.put(CurrencyUnit.PLN, new SubAccount(CurrencyUnit.PLN, new BigDecimal("10.00"), null));
        mockedMap.put(CurrencyUnit.USD, new SubAccount(CurrencyUnit.USD, new BigDecimal("0.00"), null));

        RateServiceImpl mockedRateService = mock(RateServiceImpl.class);
        NbpRate nbpRate = new NbpRate(List.of(new Rate(new BigDecimal("4.1234"), new BigDecimal("4.2345"))));
        when(mockedRateService.getCurrentRate(any(), any())).thenReturn(nbpRate);

        SubAccountRepository mockedSubAccountRepo = mock(SubAccountRepository.class);

        ExchangeOperation op = new ExchangeOperation();
        op.setAmountToTrade(new BigDecimal("1.00"));
        op.setCurrency(CurrencyUnit.USD);
        op.setExchangeType(ExchangeType.BUY);

        ExchangeService exchangeService = new ExchangeServiceImpl(mockedSubAccountRepo, mockedRateService);

        // when & then
        assertDoesNotThrow(() -> exchangeService.exchange(op, mockedMap));
        assertThat(mockedMap.get(CurrencyUnit.PLN).getAmount().setScale(2, RoundingMode.HALF_UP)).isEqualTo(new BigDecimal("5.77"));
        assertThat(mockedMap.get(CurrencyUnit.USD).getAmount()).isEqualTo(new BigDecimal("1.00").setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void exchange_shouldNotThrowExceptionForProperParametersForSellOperation() {
        // given
        Map<CurrencyUnit, SubAccount> mockedMap = new HashMap<>();
        mockedMap.put(CurrencyUnit.PLN, new SubAccount(CurrencyUnit.PLN, new BigDecimal("10.00"), null));
        mockedMap.put(CurrencyUnit.USD, new SubAccount(CurrencyUnit.USD, new BigDecimal("1.00"), null));

        RateServiceImpl mockedRateService = mock(RateServiceImpl.class);
        NbpRate nbpRate = new NbpRate(List.of(new Rate(new BigDecimal("4.1234"), new BigDecimal("4.2345"))));
        when(mockedRateService.getCurrentRate(any(), any())).thenReturn(nbpRate);

        SubAccountRepository mockedSubAccountRepo = mock(SubAccountRepository.class);

        ExchangeOperation op = new ExchangeOperation();
        op.setAmountToTrade(new BigDecimal("1.00"));
        op.setCurrency(CurrencyUnit.USD);
        op.setExchangeType(ExchangeType.SELL);

        ExchangeService exchangeService = new ExchangeServiceImpl(mockedSubAccountRepo, mockedRateService);

        // when & then
        assertDoesNotThrow(() -> exchangeService.exchange(op, mockedMap));
        assertThat(mockedMap.get(CurrencyUnit.PLN).getAmount().setScale(2, RoundingMode.HALF_UP)).isEqualTo(new BigDecimal("14.12"));
        assertThat(mockedMap.get(CurrencyUnit.USD).getAmount()).isEqualTo(new BigDecimal("0.00"));
    }
}