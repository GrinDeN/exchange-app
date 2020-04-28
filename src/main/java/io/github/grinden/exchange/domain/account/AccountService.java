package io.github.grinden.exchange.domain.account;

import io.github.grinden.exchange.domain.account.model.Account;

public interface AccountService {

    void registerAccount(final Account account);

    Account getAccount(String pesel);
}
