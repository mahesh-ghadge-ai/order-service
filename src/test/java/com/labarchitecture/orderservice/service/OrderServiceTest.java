package com.labarchitecture.orderservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.labarchitecture.orderservice.dto.request.OrderItemRequest;
import com.labarchitecture.orderservice.dto.request.OrderRequest;
import com.labarchitecture.orderservice.dto.response.OrderResponse;
import com.labarchitecture.orderservice.entity.OrderEntity;
import com.labarchitecture.orderservice.entity.OrderStatus;
import com.labarchitecture.orderservice.exception.OrderNotFoundException;
import com.labarchitecture.orderservice.config.OrderMetrics;
import com.labarchitecture.orderservice.mapper.OrderMapper;
import com.labarchitecture.orderservice.repository.OrderRepository;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private final OrderMapper orderMapper = new OrderMapper();
    private final SimpleMeterRegistry meterRegistry = new SimpleMeterRegistry();
    private final OrderMetrics orderMetrics = new OrderMetrics(meterRegistry);

    @Test
    void createOrderCalculatesTotalsAndPersistsOrder() {
        OrderService orderService = new OrderService(orderRepository, orderMapper, orderMetrics, meterRegistry);
        OrderRequest request = sampleRequest();
        when(orderRepository.save(org.mockito.ArgumentMatchers.any(OrderEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse response = orderService.createOrder(request);

        assertThat(response.totalAmount()).isEqualByComparingTo("49.98");
        assertThat(response.items()).hasSize(1);
        assertThat(response.items().get(0).lineTotal()).isEqualByComparingTo("49.98");
        verify(orderRepository).save(org.mockito.ArgumentMatchers.any(OrderEntity.class));
    }

    @Test
    void getOrderByIdThrowsWhenOrderDoesNotExist() {
        OrderService orderService = new OrderService(orderRepository, orderMapper, orderMetrics, meterRegistry);
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(orderId))
                .isInstanceOf(OrderNotFoundException.class)
                .hasMessageContaining(orderId.toString());
    }

    @Test
    void updateOrderReplacesFieldsAndItems() {
        OrderService orderService = new OrderService(orderRepository, orderMapper, orderMetrics, meterRegistry);
        UUID orderId = UUID.randomUUID();
        OrderEntity existingOrder = orderMapper.toEntity(sampleRequest());
        existingOrder.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(org.mockito.ArgumentMatchers.any(OrderEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OrderRequest updateRequest = new OrderRequest(
                OrderStatus.CONFIRMED,
                "Updated Customer",
                "updated@example.com",
                "Updated Shipping Address",
                "Updated Billing Address",
                "INR",
                "Updated notes",
                List.of(new OrderItemRequest("SKU-2", "Keyboard", 2, new BigDecimal("99.00"))));

        OrderResponse response = orderService.updateOrder(orderId, updateRequest);

        assertThat(response.status()).isEqualTo(OrderStatus.CONFIRMED);
        assertThat(response.customerName()).isEqualTo("Updated Customer");
        assertThat(response.totalAmount()).isEqualByComparingTo("198.00");
    }

    private OrderRequest sampleRequest() {
        return new OrderRequest(
                OrderStatus.PENDING,
                "John Doe",
                "john.doe@example.com",
                "123 Shipping Lane",
                "456 Billing Road",
                "USD",
                "Leave at the front desk",
                List.of(new OrderItemRequest("SKU-1", "Laptop", 1, new BigDecimal("49.98"))));
    }
}
