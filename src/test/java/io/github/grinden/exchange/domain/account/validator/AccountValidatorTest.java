package io.github.grinden.exchange.domain.account.validator;

import io.github.grinden.exchange.configuration.InvalidExchangeArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AccountValidatorTest {

    @Test
    @DisplayName("Assert that there is no exception when age from the pesel is above 18")
    void validateAge_shouldDoesNotThrowExceptionWhenIsAbove18() {
        // given
        String peselOfPersonAbove18 = "79042090999";

        // when & then
        assertDoesNotThrow(() -> AccountValidator.validateAge(peselOfPersonAbove18));
    }

    @Test
    @DisplayName("Assert that there is an InvalidExchangeArgument when age from the pesel is under 18")
    void validateAge_shouldThrowAnExceptionWhenIsUnder18() {
        // given
        String peselOfPersonUnder18 = "08292645114";

        // when & then
        assertThatThrownBy(() -> AccountValidator.validateAge(peselOfPersonUnder18))
                .isInstanceOf(InvalidExchangeArgumentException.class)
                .hasMessageContaining("age must be equal or over 18");
    }
}