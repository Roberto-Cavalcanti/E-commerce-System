package com.project.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SearchProductDTO(@Valid @NotNull String name) {
}
