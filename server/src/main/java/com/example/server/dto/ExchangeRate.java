package com.example.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRate {
    @JsonProperty("base_currency")
    private String baseCurrency;

    @JsonProperty("quote_currency")
    private String quoteCurrency;

    @JsonProperty("quote")
    private double quote;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public double getQuote() {
        return quote;
    }
}
