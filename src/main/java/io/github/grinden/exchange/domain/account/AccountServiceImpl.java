package io.github.grinden.exchange.domain.account;

import io.github.grinden.exchange.configuration.EntityNotFoundException;
import io.github.grinden.exchange.configuration.InvalidExchangeArgumentException;
import io.github.grinden.exchange.domain.account.model.AccountRepository;
import io.github.grinden.exchange.domain.account.validator.AccountValidator;
import io.github.grinden.exchange.domain.currency.CurrencyUnit;
import io.github.grinden.exchange.domain.subaccount.SubAccount;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
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
        if (accountRepository.existsById(account.getPesel())) {
            throw new InvalidExchangeArgumentException(String.format("Account with PESEL: %s already exist!", account.getPesel()));
        }
        AccountValidator.validateAge(account.getPesel());
        List<SubAccount> subAccounts = Arrays
                .stream(CurrencyUnit.values())
                .map(currency -> new SubAccount(currency,
                        currency.isPLN() ?
                                account.getAmount().setScale(2, RoundingMode.HALF_UP) :
                                BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP),
                        account))
                .collect(Collectors.toList());
        account.setSubAccounts(subAccounts);
        this.accountRepository.save(account);
    }

    @Override
    public Account getAccount(final String pesel) {
        Optional<Account> account = this.accountRepository.findById(pesel);
        return account.orElseThrow(() -> new EntityNotFoundException("Account not found with pesel: " + pesel));
    }
}
