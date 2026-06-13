package com.labarchitecture.orderservice.dto.response;

import com.labarchitecture.orderservice.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        OrderStatus status,
        String customerName,
        String customerEmail,
        String shippingAddress,
        String billingAddress,
        String currency,
        BigDecimal totalAmount,
        String notes,
        List<OrderItemResponse> items,
        Instant createdAt,
        Instant updatedAt) {
}
