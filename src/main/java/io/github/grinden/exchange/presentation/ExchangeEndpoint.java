package io.github.grinden.exchange.presentation;

import io.github.grinden.exchange.domain.account.Account;
import io.github.grinden.exchange.domain.account.AccountService;
import io.github.grinden.exchange.domain.currency.CurrencyUnit;
import io.github.grinden.exchange.domain.exchange.ExchangeOperation;
import io.github.grinden.exchange.domain.exchange.ExchangeService;
import io.github.grinden.exchange.domain.subaccount.SubAccount;
import io.github.grinden.exchange.presentation.dto.AccountDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        Account account = this.accountService.getAccount(exchangeOperation.getPesel());
        Map<CurrencyUnit, SubAccount> subAccounts = account
                .getSubAccounts()
                .stream()
                .collect(Collectors.toMap(SubAccount::getCurrency, Function.identity()));
        this.exchangeService.exchange(exchangeOperation, subAccounts);
        return ResponseEntity.noContent().build();
    }
}
