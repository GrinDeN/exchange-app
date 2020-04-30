package io.github.grinden.exchange.domain.exchange;

import io.github.grinden.exchange.configuration.EntityNotFoundException;
import io.github.grinden.exchange.configuration.InvalidExchangeArgumentException;
import io.github.grinden.exchange.domain.currency.CurrencyUnit;
import io.github.grinden.exchange.domain.rate.RateService;
import io.github.grinden.exchange.domain.rate.model.NbpRate;
import io.github.grinden.exchange.domain.subaccount.SubAccount;
import io.github.grinden.exchange.domain.subaccount.SubAccountRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final SubAccountRepository subAccountRepository;
    private final RateService rateService;

    public ExchangeServiceImpl(final SubAccountRepository subAccountRepository,
                               final RateService rateService) {
        this.subAccountRepository = subAccountRepository;
        this.rateService = rateService;
    }

    @Override
    @Transactional
    public void exchange(ExchangeOperation exchangeOperation, final Map<CurrencyUnit, SubAccount> subAccountMap) {
        NbpRate nbpRate = this.rateService.getCurrentRate(exchangeOperation.getCurrency(), LocalDate.now());
        switch (exchangeOperation.getExchangeType()) {
            case BUY:
                this.buy(this.getSubaccountInCurrency(subAccountMap, CurrencyUnit.PLN),
                        this.getSubaccountInCurrency(subAccountMap, exchangeOperation.getCurrency()),
                        exchangeOperation.getAmountToTrade(), nbpRate.getRate().getAsk());
                break;
            case SELL:
                this.sell(this.getSubaccountInCurrency(subAccountMap, exchangeOperation.getCurrency()),
                        this.getSubaccountInCurrency(subAccountMap, CurrencyUnit.PLN),
                        exchangeOperation.getAmountToTrade(), nbpRate.getRate().getBid());
                break;
            default:
                throw new InvalidExchangeArgumentException("Operation is not supported: " + exchangeOperation.getExchangeType());
        }
    }

    private SubAccount getSubaccountInCurrency(Map<CurrencyUnit, SubAccount> subAccountMap, CurrencyUnit currency) {
        return Optional
                .ofNullable(subAccountMap.get(currency))
                .orElseThrow(() -> new EntityNotFoundException("User does not have subaccount in currency: " + currency));
    }

    private void buy(SubAccount from, SubAccount to, BigDecimal amountToBuy, BigDecimal rate) {
        BigDecimal plnAmount = amountToBuy.multiply(rate);
        this.transfer(from, to, plnAmount, amountToBuy);
        List<SubAccount> subAccounts = List.of(from, to);
        this.subAccountRepository.saveAll(subAccounts);
    }

    private void sell(SubAccount from, SubAccount to, BigDecimal amountToSell, BigDecimal rate) {
        BigDecimal plnAmount = amountToSell.multiply(rate);
        this.transfer(from, to, amountToSell, plnAmount);
        List<SubAccount> subAccounts = List.of(from, to);
        this.subAccountRepository.saveAll(subAccounts);
    }

    private void transfer(SubAccount from, SubAccount to, BigDecimal amount, BigDecimal amountInNewCurrency) {
        from.subtractFunds(amount);
        to.addFunds(amountInNewCurrency);
    }
}
