package com.project.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO(
        Long orderId,
        String userName,
        List<OrderItemDTO> items,
        BigDecimal shipping,
        BigDecimal totalPrice
) {}
