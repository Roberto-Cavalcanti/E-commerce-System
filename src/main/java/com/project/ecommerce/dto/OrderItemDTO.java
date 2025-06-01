package com.project.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record OrderItemDTO(
        Long variantId,
        String productName,
        String size,
        @NotNull @Positive Integer quantity,
        BigDecimal unitPrice,
        @NotNull @PositiveOrZero BigDecimal shipping,
        BigDecimal totalItemPrice
) {}