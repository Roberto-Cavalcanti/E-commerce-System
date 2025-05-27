package com.project.ecommerce.repositories;

import com.project.ecommerce.models.Order;
import com.project.ecommerce.models.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserIdAndStatus(Long userId, OrderStatus status);
}