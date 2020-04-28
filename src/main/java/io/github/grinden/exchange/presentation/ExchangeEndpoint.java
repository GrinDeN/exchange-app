package io.github.grinden.exchange.presentation;

import io.github.grinden.exchange.domain.account.AccountService;
import io.github.grinden.exchange.domain.account.model.Account;
import io.github.grinden.exchange.domain.exchange.ExchangeService;
import io.github.grinden.exchange.domain.exchange.model.ExchangeOperation;
import io.github.grinden.exchange.presentation.dto.AccountDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    ResponseEntity<Void> registerAccount(@RequestBody @Valid Account account) {
        this.accountService.registerAccount(account);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/accounts/{pesel}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AccountDto> viewAccount(@PathVariable String pesel) {
        AccountDto account = AccountDto.of(this.accountService.getAccount(pesel));
        return ResponseEntity.ok(account);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> exchangeCurrency(@RequestBody @Valid ExchangeOperation exchangeOperation) {
        this.exchangeService.exchange(exchangeOperation);
        return ResponseEntity.noContent().build();
    }
}
