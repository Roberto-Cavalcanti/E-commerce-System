package com.project.ecommerce.repositories;

import com.project.ecommerce.models.Cart;
import com.project.ecommerce.models.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndStatus(Long userId, OrderStatus status);
}