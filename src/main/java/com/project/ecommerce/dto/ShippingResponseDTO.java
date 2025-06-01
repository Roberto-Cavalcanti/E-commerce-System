package com.project.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ShippingResponseDTO(BigDecimal shipping, LocalDate date) {
}
