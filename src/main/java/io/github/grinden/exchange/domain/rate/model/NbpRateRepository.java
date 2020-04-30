package io.github.grinden.exchange.domain.rate.model;

import io.github.grinden.exchange.configuration.InvalidExchangeArgumentException;
import io.github.grinden.exchange.domain.currency.CurrencyUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class NbpRateRepository implements RateRepository {

    private static final String NBP_URL = "https://api.nbp.pl/api/exchangerates/rates/C/%s/%d-%02d-%02d";

    private final Logger LOG = LoggerFactory.getLogger(NbpRateRepository.class);

    private final RestTemplate restTemplate = new RestTemplateBuilder()
            .setConnectTimeout(Duration.of(15, ChronoUnit.SECONDS))
            .setReadTimeout(Duration.of(5, ChronoUnit.SECONDS))
            .build();

    @Override
    @Cacheable("rates")
    public NbpRate getRate(final CurrencyUnit currency, final LocalDate date) {
        try {
            ResponseEntity<NbpRate> response = this.getRateForDay(currency, date);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new InvalidExchangeArgumentException(
                    String.format("Cannot get rate - currency %s is invalid or the service is unavailable", currency));
        }
    }

    @Scheduled(cron = "0 20 8 * * MON-FRI")
    @CacheEvict(value = "rates", allEntries = true)
    void evictCache() {
        LOG.info("Cache evicted...");
    }

    ResponseEntity<NbpRate> getRateForDay(final CurrencyUnit currency, final LocalDate date) {
        String url = String.format(NBP_URL, currency, date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
        return this.restTemplate.getForEntity(url, NbpRate.class);
    }
}
