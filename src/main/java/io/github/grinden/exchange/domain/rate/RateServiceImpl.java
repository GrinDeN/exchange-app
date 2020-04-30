package io.github.grinden.exchange.domain.rate;

import io.github.grinden.exchange.domain.currency.CurrencyUnit;
import io.github.grinden.exchange.domain.rate.model.NbpRate;
import io.github.grinden.exchange.domain.rate.model.RateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;

    public RateServiceImpl(final RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public NbpRate getCurrentRate(final CurrencyUnit currency, final LocalDate date) {
        return this.rateRepository.getRate(currency, date);
    }
}
