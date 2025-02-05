package com.example.server.service;

import com.example.server.dto.ExchangeRate;
import com.example.server.model.Currency;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    @Value("${swop.api.key}")
    private String apiKey;
    private static final String SWOP_CURRENCIES_URL = "https://swop.cx/rest/currencies?api-key=";
    private static final String SWOP_RATES_URL = "https://swop.cx/rest/rates?api-key=";

    private final RestTemplate restTemplate = new RestTemplate();
    private final CacheManager cacheManager;

    public CurrencyService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = "currencies")
    public List<Currency> getCurrencies() {
        String url = SWOP_CURRENCIES_URL + apiKey;

        ResponseEntity<Currency[]> response = restTemplate.getForEntity(url, Currency[].class);

        Currency[] currenciesArray = response.getBody();
        
        return currenciesArray != null ? List.of(currenciesArray) : List.of();
    }

    @Cacheable(value = "exchangeRates")
    public Map<String, Double> getExchangeRates() {
        Map<String, Double> cachedRates = cacheManager.getCache("exchangeRates").get("rates", Map.class);
        if (cachedRates != null) {
            logger.info("Cache HIT for exchange rates");
            return cachedRates;
        }

        logger.info("Fetching exchange rates from API...");
        String url = SWOP_RATES_URL + apiKey;

        ResponseEntity<ExchangeRate[]> response = restTemplate.getForEntity(url, ExchangeRate[].class);

        Map<String, Double> ratesMap = new HashMap<>();

        if (response.getBody() != null) {
            for (ExchangeRate rate : response.getBody()) {
                String key = rate.getBaseCurrency() + "_" + rate.getQuoteCurrency();
                ratesMap.put(key, rate.getQuote());
            }
        }

        cacheManager.getCache("exchangeRates").put("rates", ratesMap);
        logger.info("Exchange rates cached: {}", ratesMap.size());
        
        return ratesMap;
    }

    public Double convertCurrency(String source, String target, Double amount) {
        logger.debug("Starting conversion: {} -> {} (Amount: {})", source, target, amount);

        Map<String, Double> rates = getExchangeRates();

        String key = source + "_" + target;

        if (!rates.containsKey(key)) {
            throw new IllegalArgumentException("Invalid currency pair: " + source + " to " + target);
        }

        double exchangeRate = rates.get(key);
        double convertedAmount = amount * exchangeRate;

        logger.info("Conversion completed: {} {} -> {} {} at rate {}", 
            amount, source, convertedAmount, target, exchangeRate);

        return convertedAmount;
    }
}
