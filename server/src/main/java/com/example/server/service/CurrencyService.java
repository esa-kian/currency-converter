package com.example.server.service;

import com.example.server.model.Currency;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {
    @Value("${swop.api.key}")
    private String apiKey;
    private static final String SWOP_API_URL = "https://swop.cx/rest/currencies?api-key=";

    public List<Currency> getCurrencies() {
        RestTemplate restTemplate = new RestTemplate();
        String url = SWOP_API_URL + apiKey;

        ResponseEntity<Currency[]> response = restTemplate.getForEntity(url, Currency[].class);

        Currency[] currenciesArray = response.getBody();
        
        if (currenciesArray != null) {
            return List.of(currenciesArray);
        }

        return List.of();
    }
}
