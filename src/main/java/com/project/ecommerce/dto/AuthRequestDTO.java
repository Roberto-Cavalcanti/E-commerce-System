package com.project.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDTO(@NotNull @Email String email, @NotNull String password) {
}
