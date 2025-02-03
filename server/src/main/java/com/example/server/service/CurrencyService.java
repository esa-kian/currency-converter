package com.example.server.service;

import com.example.currencyconverter.model.Currency;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {
    @Value("${swop.api.key}")
    private String apiKey;
    private static final String SWOP_API_URL = "https://swop.cx/rest/currencies?api-key=" + API_KEY;

    public List<Currency> getCurrencies() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity(SWOP_API_URL, Map.class);
        
        Map<String, Object> currencies = (Map<String, Object>) response.getBody().get("currencies");
        List<Currency> currencyList = new ArrayList<>();
        
        currencies.forEach((code, name) -> currencyList.add(new Currency(code, name.toString())));

        return currencyList;
    }
}
