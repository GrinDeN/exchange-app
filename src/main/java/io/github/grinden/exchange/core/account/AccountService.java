package io.github.grinden.exchange.core.account;

import io.github.grinden.exchange.core.account.model.Account;
import io.github.grinden.exchange.core.account.model.AccountDto;

public interface AccountService {

    void registerAccount(final Account account);

    AccountDto getAccount(String pesel);
}
