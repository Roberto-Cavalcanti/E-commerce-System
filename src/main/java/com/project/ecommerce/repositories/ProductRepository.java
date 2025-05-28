package com.project.ecommerce.repositories;

import com.project.ecommerce.models.Product;
import com.project.ecommerce.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductsByShop(Shop shop);
}
