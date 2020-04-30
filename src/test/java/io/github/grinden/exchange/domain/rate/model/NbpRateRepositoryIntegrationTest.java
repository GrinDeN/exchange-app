package io.github.grinden.exchange.domain.rate.model;

import io.github.grinden.exchange.configuration.InvalidExchangeArgumentException;
import io.github.grinden.exchange.domain.currency.CurrencyUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NbpRateRepositoryIntegrationTest {

    private NbpRateRepository nbpRateRepository;
    private LocalDate dateOfRate;

    @BeforeEach
    void setupRepository() {
        nbpRateRepository = new NbpRateRepository();
        dateOfRate = LocalDate.of(2020, 4, 9);
    }

    @Test
    @DisplayName("Assert that nbp service provides rates for USD currency")
    void getRate_forUSDCurrencyShouldGiveRateObject() {
        // given
        String expectedCurrencyName = "dolar amerykański";

        // when
        NbpRate rate = nbpRateRepository.getRate(CurrencyUnit.USD, dateOfRate);

        // then
        assertThat(rate.getCurrency()).isEqualTo(expectedCurrencyName);
        assertThat(rate.getRate().getAsk()).isNotNull();
        assertThat(rate.getRate().getBid()).isNotNull();
    }

    @Test
    @DisplayName("Assert that nbp service throws an exception for PLN currency as it does not exist")
    void getRate_shouldThrowAnExceptionForNotPLNCurrency() {
        // given

        // when & then
        assertThatThrownBy(() -> nbpRateRepository.getRate(CurrencyUnit.PLN, dateOfRate))
                .isInstanceOf(InvalidExchangeArgumentException.class)
                .hasMessageContaining("currency PLN is invalid");
    }

    @Test
    @DisplayName("Assert that nbp service throws an exception when service is unavailable")
    void getRateFromNbp_shouldThrowAnExceptionWhenServiceIsUnavailable() {
        // given
        NbpRateRepository mockedRepo = mock(NbpRateRepository.class);
        when(mockedRepo.getRateForDay(any(), any()))
                .thenReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());

        // when & then
        assertThatThrownBy(() -> nbpRateRepository.getRate(CurrencyUnit.PLN, dateOfRate))
                .isInstanceOf(InvalidExchangeArgumentException.class)
                .hasMessageContaining("service is unavailable");
    }
}