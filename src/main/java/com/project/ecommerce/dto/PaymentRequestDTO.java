package com.project.ecommerce.dto;

import com.project.ecommerce.models.enums.PaymentType;
import com.project.ecommerce.models.enums.StatusPayment;

import java.util.List;

public record PaymentRequestDTO(PaymentType paymentType, List<OrderItemDTO> order, StatusPayment statusPayment) {
}
