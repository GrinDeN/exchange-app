package io.github.grinden.exchange.presentation;

import io.github.grinden.exchange.core.account.AccountService;
import io.github.grinden.exchange.core.account.model.Account;
import io.github.grinden.exchange.core.account.model.AccountDto;
import io.github.grinden.exchange.core.exchange.ExchangeService;
import io.github.grinden.exchange.core.exchange.model.ExchangeOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/exchange")
public class ExchangeEndpoint {

    private final AccountService accountService;
    private final ExchangeService exchangeService;

    public ExchangeEndpoint(final ExchangeService exchangeService, final AccountService accountService) {
        this.accountService = accountService;
        this.exchangeService = exchangeService;
    }

    @PostMapping(path = "/accounts", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> registerAccount(@RequestBody Account account) {
        this.accountService.registerAccount(account);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/accounts/{pesel}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> viewAccount(@PathVariable String pesel) {
        AccountDto account = AccountDto.of(this.accountService.getAccount(pesel));
        return ResponseEntity.ok(account);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> exchangeCurrency(@RequestBody ExchangeOperation exchangeOperation) {
        this.exchangeService.exchange(exchangeOperation);
        return ResponseEntity.noContent().build();
    }
}
