package com.labarchitecture.orderservice.repository;

import com.labarchitecture.orderservice.entity.OrderEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}
