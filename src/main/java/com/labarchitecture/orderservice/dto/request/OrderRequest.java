package com.labarchitecture.orderservice.dto.request;

import com.labarchitecture.orderservice.entity.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public record OrderRequest(
        @NotNull(message = "Order status is required")
        OrderStatus status,

        @NotBlank(message = "Customer name is required")
        @Size(max = 200, message = "Customer name must not exceed 200 characters")
        String customerName,

        @NotBlank(message = "Customer email is required")
        @Email(message = "Customer email must be valid")
        @Size(max = 255, message = "Customer email must not exceed 255 characters")
        String customerEmail,

        @NotBlank(message = "Shipping address is required")
        @Size(max = 500, message = "Shipping address must not exceed 500 characters")
        String shippingAddress,

        @NotBlank(message = "Billing address is required")
        @Size(max = 500, message = "Billing address must not exceed 500 characters")
        String billingAddress,

        @NotBlank(message = "Currency is required")
        @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter uppercase ISO code")
        String currency,

        @Size(max = 1000, message = "Notes must not exceed 1000 characters")
        String notes,

        @NotEmpty(message = "At least one order item is required")
        List<@Valid OrderItemRequest> items) {
}
