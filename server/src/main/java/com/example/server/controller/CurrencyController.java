package com.example.server.controller;

import com.example.server.model.Currency;
import com.example.server.dto.CurrencyConversionRequest;
import com.example.server.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/currencies")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Currency API", description = "Endpoints for currency operations")
public class CurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Operation(summary = "Get all currencies", description = "Returns a list of supported currencies")
    @GetMapping
    public List<Currency> getCurrencies() {
        logger.info("Fetching all currencies");

        return currencyService.getCurrencies();
    }

    @Operation(summary = "Convert currency", description = "Converts an amount from source currency to target currency")
    @PostMapping("/convert")
    public ResponseEntity convertCurrency(@Valid @RequestBody CurrencyConversionRequest request) {
        logger.info("Currency conversion requested: {} -> {} (Amount: {})",
                request.getSource(), request.getTarget(), request.getAmount());

        String result = currencyService.convertCurrency(request.getSource(), request.getTarget(), request.getAmount());

        logger.info("Conversion result: {} -> {} (Amount: {}) = {}",
                request.getSource(), request.getTarget(), request.getAmount(), result);

        Map<String, String> map = new HashMap<>();
        map.put("convertedAmount", result);

        return ResponseEntity.ok(map);
    }
}
