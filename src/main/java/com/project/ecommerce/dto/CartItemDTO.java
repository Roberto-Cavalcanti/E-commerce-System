package com.project.ecommerce.dto;

import java.math.BigDecimal;

public record CartItemDTO(
        Long variantId,
        String productName,
        String size,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalItemPrice
) {}