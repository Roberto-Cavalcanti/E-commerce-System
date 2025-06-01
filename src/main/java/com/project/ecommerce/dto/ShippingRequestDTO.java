package com.project.ecommerce.dto;

import com.project.ecommerce.models.enums.States;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ShippingRequestDTO(States state, BigDecimal shipping, LocalDate date){}
