package com.project.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO(
        Long orderId,
        String userName,
        List<CartItemDTO> items,
        BigDecimal shippingPrice,
        BigDecimal totalPrice
) {}
