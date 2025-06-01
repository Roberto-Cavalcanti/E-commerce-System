package com.project.ecommerce.dto;

import java.math.BigDecimal;

public record ProductVariantResponseDTO(Long variantId, String size, BigDecimal price, Integer quantity) {
}
