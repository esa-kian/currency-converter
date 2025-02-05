package com.example.server.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CurrencyConversionRequest {

    @NotBlank(message = "Source currency cannot be blank")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid source currency format")
    private String source;

    @NotBlank(message = "Target currency cannot be blank")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid target currency format")
    private String target;

    @Min(value = 1, message = "Amount must be greater than zero")
    private Double amount;
}
