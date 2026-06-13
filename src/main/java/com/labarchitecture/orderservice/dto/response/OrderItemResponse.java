package com.labarchitecture.orderservice.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse(
        UUID id,
        String productCode,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal) {
}
