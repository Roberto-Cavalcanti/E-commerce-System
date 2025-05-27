package com.project.ecommerce.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserRegistrationDTO(@NotNull String name, @NotNull @Email String email,@NotNull String password) {
}
