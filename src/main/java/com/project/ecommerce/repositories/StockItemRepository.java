package com.project.ecommerce.repositories;

import com.project.ecommerce.models.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockItemRepository extends JpaRepository<StockItem, Long> {
    Optional<StockItem> findByVariantId(Long variantId);
}