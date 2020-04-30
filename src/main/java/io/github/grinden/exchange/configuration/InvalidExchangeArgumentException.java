package io.github.grinden.exchange.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidExchangeArgumentException extends RuntimeException {

    public InvalidExchangeArgumentException(final String message) {
        super(message);
    }
}
