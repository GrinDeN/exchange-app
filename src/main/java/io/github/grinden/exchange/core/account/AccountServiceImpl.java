package io.github.grinden.exchange.core.account;

import io.github.grinden.exchange.core.account.model.Account;
import io.github.grinden.exchange.core.account.model.AccountDto;
import io.github.grinden.exchange.core.account.model.AccountRepository;
import io.github.grinden.exchange.core.currency.CurrencyUnit;
import io.github.grinden.exchange.core.subaccount.SubAccount;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public void registerAccount(final Account account) {
        List<SubAccount> subAccounts = Arrays
                .stream(CurrencyUnit.values())
                .map(currency -> new SubAccount(currency, currency.isPLN() ? account.getAmount() : new BigDecimal("0.00"), account))
                .collect(Collectors.toList());
        account.setSubAccounts(subAccounts);
        this.accountRepository.save(account);
    }

    @Override
    public AccountDto getAccount(final String pesel) {
        Optional<Account> account = this.accountRepository.findById(pesel);
        Account acc = account.orElseThrow(() -> new NoSuchElementException("Account not found with pesel: " + pesel));
        return AccountDto.of(acc);
    }
}
