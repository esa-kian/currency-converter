package com.example.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyServiceTest {

    @Mock

    private CacheManager cacheManager;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cacheManager = new ConcurrentMapCacheManager("exchangeRates");
        currencyService = new CurrencyService(cacheManager);
    }

    @Test
    void testConvertCurrency_ValidPair() {
        Map<String, Double> mockRates = new HashMap<>();
        mockRates.put("EUR_USD", 1.1);
        cacheManager.getCache("exchangeRates").put("rates", mockRates);

        String convertedAmount = currencyService.convertCurrency("EUR", "USD", 100.0);
        assertEquals("$110.00", convertedAmount);
    }
}
