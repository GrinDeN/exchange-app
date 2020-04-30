package io.github.grinden.exchange.domain.account;

public interface AccountService {

    void registerAccount(final Account account);

    Account getAccount(String pesel);
}
