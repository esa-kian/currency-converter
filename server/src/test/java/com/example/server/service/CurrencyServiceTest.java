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

    @Test
    void testConvertCurrency_InvalidPair() {
        Map<String, Double> mockRates = new HashMap<>();
        cacheManager.getCache("exchangeRates").put("rates", mockRates);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            currencyService.convertCurrency("EUR", "GBP", 100.0));
        assertEquals("Invalid currency pair: EUR to GBP", exception.getMessage());
    }

    @Test
    void testConvertCurrency_ZeroAmount() {
        Map<String, Double> mockRates = new HashMap<>();
        mockRates.put("EUR_USD", 1.1);
        cacheManager.getCache("exchangeRates").put("rates", mockRates);

        String convertedAmount = currencyService.convertCurrency("EUR", "USD", 0.0);
        assertEquals("$0.00", convertedAmount);
    }

    @Test
    void testConvertCurrency_NegativeAmount() {
        Map<String, Double> mockRates = new HashMap<>();
        mockRates.put("EUR_USD", 1.1);
        cacheManager.getCache("exchangeRates").put("rates", mockRates);
    }

    @Test
    void testConvertCurrency_CacheHit() {
        Map<String, Double> mockRates = new HashMap<>();
        mockRates.put("USD_JPY", 110.5);
        cacheManager.getCache("exchangeRates").put("rates", mockRates);

        String convertedAmount = currencyService.convertCurrency("USD", "JPY", 200.0);
        assertEquals("￥22,100", convertedAmount);
    }
}

