package com.project.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddCartItemRequestDTO(
        @NotNull Long variantId,
        @NotNull @Positive Integer quantity
) {}
