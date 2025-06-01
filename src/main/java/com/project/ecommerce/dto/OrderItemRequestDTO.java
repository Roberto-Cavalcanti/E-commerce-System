package com.project.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemRequestDTO(
        @NotNull Long variantId,
        @NotNull @Positive Integer quantity,
        BigDecimal shipping
) {}
