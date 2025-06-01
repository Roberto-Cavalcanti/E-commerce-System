package com.project.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProductResponseDTO(Long id, @NotNull String name, String description, @NotNull @Positive BigDecimal price, String image, String categoryName, String shopName) {
}
