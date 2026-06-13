package com.labarchitecture.orderservice.service;

import com.labarchitecture.orderservice.dto.request.OrderRequest;
import com.labarchitecture.orderservice.dto.response.OrderResponse;
import com.labarchitecture.orderservice.config.OrderMetrics;
import com.labarchitecture.orderservice.entity.OrderEntity;
import com.labarchitecture.orderservice.exception.OrderNotFoundException;
import com.labarchitecture.orderservice.mapper.OrderMapper;
import com.labarchitecture.orderservice.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    public static final String ORDER_CACHE = "orders-by-id";
    public static final String ORDER_LIST_CACHE = "orders-list";
    public static final String ORDER_CACHE_V2 = "orders-by-id-v2";
    public static final String ORDER_LIST_CACHE_V2 = "orders-list-v2";
    private static final String ORDER_LOOKUP_TIMER = "orders.lookup.duration";

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderMetrics orderMetrics;
    private final Timer orderLookupTimer;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, OrderMetrics orderMetrics, MeterRegistry meterRegistry) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderMetrics = orderMetrics;
        this.orderLookupTimer = Timer.builder(ORDER_LOOKUP_TIMER)
                .description("Time spent fetching an order by id")
                .register(meterRegistry);
    }

    @Transactional
    @CacheEvict(cacheNames = {ORDER_CACHE_V2, ORDER_LIST_CACHE_V2}, allEntries = true)
    public OrderResponse createOrder(OrderRequest request) {
        OrderEntity order = orderMapper.toEntity(request);
        order.recalculateTotals();
        OrderEntity savedOrder = orderRepository.save(order);
        orderMetrics.incrementCreated();
        return orderMapper.toResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ORDER_LIST_CACHE_V2)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ORDER_CACHE_V2, key = "#id")
    public OrderResponse getOrderById(UUID id) {
        orderMetrics.incrementLookup();
        return orderMetrics.recordLookup(orderLookupTimer, () -> {
            OrderEntity order = findOrderById(id);
            return orderMapper.toResponse(order);
        });
    }

    @Transactional
    @CacheEvict(cacheNames = {ORDER_CACHE_V2, ORDER_LIST_CACHE_V2}, allEntries = true)
    public OrderResponse updateOrder(UUID id, OrderRequest request) {
        OrderEntity order = findOrderById(id);
        orderMapper.updateEntity(order, request);
        order.recalculateTotals();
        OrderEntity savedOrder = orderRepository.save(order);
        orderMetrics.incrementUpdated();
        return orderMapper.toResponse(savedOrder);
    }

    @Transactional
    @CacheEvict(cacheNames = {ORDER_CACHE_V2, ORDER_LIST_CACHE_V2}, allEntries = true)
    public void deleteOrder(UUID id) {
        OrderEntity order = findOrderById(id);
        orderRepository.delete(order);
        orderMetrics.incrementDeleted();
    }

    private OrderEntity findOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
