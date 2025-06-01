package com.project.ecommerce.dto;

import com.project.ecommerce.models.enums.PaymentType;

import java.math.BigDecimal;
import java.util.List;

public record PaymentResponseDTO(PaymentType paymentType, BigDecimal subTotalProducts, BigDecimal shipping, BigDecimal totalAmount, List<OrderItemDTO> order) {
}
