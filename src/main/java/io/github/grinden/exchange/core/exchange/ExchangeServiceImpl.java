package io.github.grinden.exchange.core.exchange;

import io.github.grinden.exchange.core.account.AccountService;
import io.github.grinden.exchange.core.account.model.Account;
import io.github.grinden.exchange.core.currency.CurrencyUnit;
import io.github.grinden.exchange.core.exchange.model.ExchangeOperation;
import io.github.grinden.exchange.core.subaccount.SubAccount;
import io.github.grinden.exchange.core.subaccount.SubAccountRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final AccountService accountService;
    private final SubAccountRepository subAccountRepository;

    public ExchangeServiceImpl(final AccountService accountService, final SubAccountRepository subAccountRepository) {
        this.accountService = accountService;
        this.subAccountRepository = subAccountRepository;
    }

    @Override
    @Transactional
    public void exchange(ExchangeOperation exchangeOperation) {
        Account account = this.accountService.getAccount(exchangeOperation.getPesel());
        Map<CurrencyUnit, SubAccount> subAccountMap = account
                .getSubAccounts()
                .stream()
                .collect(Collectors.toMap(SubAccount::getCurrency, Function.identity()));
        switch (exchangeOperation.getExchangeType()) {
            case BUY:
                this.buy(subAccountMap, exchangeOperation.getFromCurrency(), exchangeOperation.getToCurrency(), exchangeOperation.getAmountToTrade());
                break;
            case SELL:
                break;
        }
    }

    private void buy(Map<CurrencyUnit, SubAccount> subAccountMap, CurrencyUnit fromCurrency, CurrencyUnit toCurrency,
                     BigDecimal amount) {
        SubAccount fromSubAccount = Optional
                .ofNullable(subAccountMap.get(fromCurrency))
                .orElseThrow(() -> new NoSuchElementException("User does not have subaccount with currency: " + fromCurrency));
        if (amount.compareTo(fromSubAccount.getAmount()) > 0) {
            throw new RuntimeException("Not enough money on subaccount with currency PLN");
        }
        SubAccount toSubAccount = Optional
                .ofNullable(subAccountMap.get(toCurrency))
                .orElseThrow(() -> new NoSuchElementException("User does not have subaccount with currency: " + toCurrency));

        BigDecimal exchangeRate = fromCurrency.equals(CurrencyUnit.PLN) ? this.getAskRate(CurrencyUnit.USD) : this.getBidRate(CurrencyUnit.USD);
        BigDecimal amountInNewCurrency = amount.multiply(exchangeRate);
        fromSubAccount.setAmount(fromSubAccount.getAmount().subtract(amount));
        toSubAccount.setAmount(toSubAccount.getAmount().add(amountInNewCurrency));
        List<SubAccount> subAccounts = List.of(fromSubAccount, toSubAccount);
        this.subAccountRepository.saveAll(subAccounts);
    }

    private BigDecimal getBidRate(CurrencyUnit currency) {
        return new BigDecimal("4.1665");
    }

    private BigDecimal getAskRate(CurrencyUnit currency) {
        return BigDecimal.ONE.setScale(4, RoundingMode.HALF_EVEN)
                .divide(new BigDecimal("4.2507"), RoundingMode.HALF_EVEN);
    }
}
