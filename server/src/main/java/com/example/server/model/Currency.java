package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
public class Currency {
    private String code;

    @JsonProperty("numeric_code")
    private String numericCode;

    @JsonProperty("decimal_digits")
    private int decimalDigits;

    private String name;
    private boolean active;
}