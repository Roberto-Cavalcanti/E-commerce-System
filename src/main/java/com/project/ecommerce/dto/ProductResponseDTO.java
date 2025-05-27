package com.project.ecommerce.dto;

import com.project.ecommerce.models.enums.Category;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        @NotNull String name,
        String description,
        @NotNull @Positive BigDecimal price,
        String image,
        String categoryName,
        String shopName
) {}
