package com.example.server.controller;

import com.example.server.model.Currency;
import com.example.server.dto.CurrencyConversionRequest;
import com.example.server.service.CurrencyService;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/currencies")
@CrossOrigin(origins = "http://localhost:5173")
public class CurrencyController {
    
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public List<Currency> getCurrencies() {
        return currencyService.getCurrencies();
    }

    @PostMapping("/convert")
    public double convertCurrency(@Valid @RequestBody CurrencyConversionRequest request) {
        return currencyService.convertCurrency(request.getSource(), request.getTarget(), request.getAmount());
    }
}
