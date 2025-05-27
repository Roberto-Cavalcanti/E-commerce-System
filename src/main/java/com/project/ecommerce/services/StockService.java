package com.project.ecommerce.services;

import com.project.ecommerce.models.StockItem;
import com.project.ecommerce.repositories.StockItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockItemRepository stockItemRepository;

    public StockService(StockItemRepository stockItemRepository) {
        this.stockItemRepository = stockItemRepository;
    }

    public void checkStockAvailability(Long variantId, Integer requestedQuantity) {
        StockItem stockItem = stockItemRepository.findByVariantId(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Variant not found in stock"));
        if (stockItem.getQuantity() < requestedQuantity) {
            throw new IllegalStateException("Insufficient stock for variant ID " + variantId);
        }
    }

    public void updateStock(Long variantId, Integer quantityToRemove) {
        StockItem stockItem = stockItemRepository.findByVariantId(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Variant not found in stock"));
        int newQuantity = stockItem.getQuantity() - quantityToRemove;
        if (newQuantity < 0) {
            throw new IllegalStateException("Cannot reduce stock below zero");
        }
        stockItem.setQuantity(newQuantity);
        stockItemRepository.save(stockItem);
    }
}
