package io.github.grinden.exchange.core.rate;

import io.github.grinden.exchange.core.currency.CurrencyUnit;
import io.github.grinden.exchange.core.rate.model.NbpRate;
import io.github.grinden.exchange.core.rate.model.RateRepository;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;

    public RateServiceImpl(final RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public NbpRate getCurrentRate(final CurrencyUnit currency) {
        return this.rateRepository.getRateFromNbp(currency);
    }
}
