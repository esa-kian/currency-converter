package com.example.server.controller;

import com.example.server.model.Currency;
import com.example.server.dto.CurrencyConversionRequest;
import com.example.server.service.CurrencyService;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/currencies")
@CrossOrigin(origins = "http://localhost:5173")
public class CurrencyController {
    
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public List<Currency> getCurrencies() {
        logger.info("Fetching all currencies");

        return currencyService.getCurrencies();
    }

    @PostMapping("/convert")
    public String convertCurrency(@Valid @RequestBody CurrencyConversionRequest request) {
        logger.info("Currency conversion requested: {} -> {} (Amount: {})", 
            request.getSource(), request.getTarget(), request.getAmount());

        String result = currencyService.convertCurrency(request.getSource(), request.getTarget(), request.getAmount());

        logger.info("Conversion result: {} -> {} (Amount: {}) = {}", 
            request.getSource(), request.getTarget(), request.getAmount(), result);

        return result;
    }
}
