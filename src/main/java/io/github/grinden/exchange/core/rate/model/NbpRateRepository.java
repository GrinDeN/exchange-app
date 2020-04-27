package io.github.grinden.exchange.core.rate.model;

import io.github.grinden.exchange.core.currency.CurrencyUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NbpRateRepository implements RateRepository {

    private static final String NBP_URL = "https://api.nbp.pl/api/exchangerates/rates/C/%s/today";

    private final Logger LOG = LoggerFactory.getLogger(NbpRateRepository.class);

    @Override
    @Cacheable("rates")
    public NbpRate getRateFromNbp(final CurrencyUnit currency) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(NBP_URL, currency);
        ResponseEntity<NbpRate> response = restTemplate.getForEntity(url, NbpRate.class);
        if (response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            throw new IllegalStateException(String.format("Cannot get rate for %s - service is unavailable", currency));
        }
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalStateException(String.format("Cannot get rate - currency %s is invalid", currency));
        }
        return response.getBody();
    }

    @Scheduled(cron = "0 20 8 * * MON-FRI")
    @CacheEvict(value = "rates", allEntries = true)
    void evictCache() {
        LOG.info("Cache evicted...");
    }
}
