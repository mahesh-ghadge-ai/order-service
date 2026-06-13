package com.labarchitecture.orderservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record OrderItemRequest(
        @NotBlank(message = "Product code is required")
        @Size(max = 64, message = "Product code must not exceed 64 characters")
        String productCode,

        @NotBlank(message = "Product name is required")
        @Size(max = 200, message = "Product name must not exceed 200 characters")
        String productName,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity,

        @NotNull(message = "Unit price is required")
        @Positive(message = "Unit price must be greater than 0")
        BigDecimal unitPrice) {
}
