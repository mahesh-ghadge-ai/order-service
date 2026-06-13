package com.labarchitecture.orderservice.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

@Component
public class OrderMetrics {

    private final Counter createdOrders;
    private final Counter updatedOrders;
    private final Counter deletedOrders;
    private final Counter orderLookups;

    public OrderMetrics(MeterRegistry meterRegistry) {
        this.createdOrders = Counter.builder("orders.created.total")
                .description("Total orders created")
                .register(meterRegistry);
        this.updatedOrders = Counter.builder("orders.updated.total")
                .description("Total orders updated")
                .register(meterRegistry);
        this.deletedOrders = Counter.builder("orders.deleted.total")
                .description("Total orders deleted")
                .register(meterRegistry);
        this.orderLookups = Counter.builder("orders.lookups.total")
                .description("Total order lookup operations")
                .register(meterRegistry);
    }

    public void incrementCreated() {
        createdOrders.increment();
    }

    public void incrementUpdated() {
        updatedOrders.increment();
    }

    public void incrementDeleted() {
        deletedOrders.increment();
    }

    public void incrementLookup() {
        orderLookups.increment();
    }

    public <T> T recordLookup(Timer timer, java.util.function.Supplier<T> supplier) {
        Timer.Sample sample = Timer.start();
        try {
            return supplier.get();
        } finally {
            sample.stop(timer);
        }
    }
}
