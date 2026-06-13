package com.labarchitecture.orderservice.mapper;

import com.labarchitecture.orderservice.dto.request.OrderItemRequest;
import com.labarchitecture.orderservice.dto.request.OrderRequest;
import com.labarchitecture.orderservice.dto.response.OrderItemResponse;
import com.labarchitecture.orderservice.dto.response.OrderResponse;
import com.labarchitecture.orderservice.entity.OrderEntity;
import com.labarchitecture.orderservice.entity.OrderItemEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderEntity toEntity(OrderRequest request) {
        OrderEntity entity = new OrderEntity();
        apply(request, entity);
        return entity;
    }

    public void updateEntity(OrderEntity entity, OrderRequest request) {
        apply(request, entity);
    }

    public OrderResponse toResponse(OrderEntity entity) {
        List<OrderItemResponse> itemResponses = entity.getItems().stream()
                .map(this::toResponse)
                .toList();
        return new OrderResponse(
                entity.getId(),
                entity.getStatus(),
                entity.getCustomerName(),
                entity.getCustomerEmail(),
                entity.getShippingAddress(),
                entity.getBillingAddress(),
                entity.getCurrency(),
                entity.getTotalAmount(),
                entity.getNotes(),
                itemResponses,
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    private void apply(OrderRequest request, OrderEntity entity) {
        entity.setStatus(request.status());
        entity.setCustomerName(request.customerName());
        entity.setCustomerEmail(request.customerEmail());
        entity.setShippingAddress(request.shippingAddress());
        entity.setBillingAddress(request.billingAddress());
        entity.setCurrency(request.currency());
        entity.setNotes(request.notes());
        entity.setItems(toEntities(request.items()));
    }

    private List<OrderItemEntity> toEntities(List<OrderItemRequest> requests) {
        List<OrderItemEntity> items = new ArrayList<>(requests.size());
        for (OrderItemRequest request : requests) {
            OrderItemEntity item = new OrderItemEntity();
            item.setProductCode(request.productCode());
            item.setProductName(request.productName());
            item.setQuantity(request.quantity());
            item.setUnitPrice(request.unitPrice());
            item.recalculateLineTotal();
            items.add(item);
        }
        return items;
    }

    private OrderItemResponse toResponse(OrderItemEntity entity) {
        return new OrderItemResponse(
                entity.getId(),
                entity.getProductCode(),
                entity.getProductName(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getLineTotal());
    }
}
