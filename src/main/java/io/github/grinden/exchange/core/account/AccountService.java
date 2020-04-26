package io.github.grinden.exchange.core.account;

import io.github.grinden.exchange.core.account.model.Account;

public interface AccountService {

    void registerAccount(final Account account);

    Account getAccount(String pesel);
}
