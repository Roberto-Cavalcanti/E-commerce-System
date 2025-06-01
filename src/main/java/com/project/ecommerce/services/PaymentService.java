package com.project.ecommerce.services;

import com.project.ecommerce.dto.OrderItemDTO;
import com.project.ecommerce.dto.PaymentRequestDTO;
import com.project.ecommerce.dto.PaymentResponseDTO;
import com.project.ecommerce.exceptions.PaymentErrorException;
import com.project.ecommerce.models.enums.StatusPayment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO) {
        if (paymentRequestDTO.statusPayment().equals(StatusPayment.ERROR)) {
            throw new PaymentErrorException();
        }

        return buildPaymentResponseDTO(paymentRequestDTO);
    }

    private PaymentResponseDTO buildPaymentResponseDTO(PaymentRequestDTO paymentRequestDTO) {

        BigDecimal totalShipping = paymentRequestDTO.order().stream()
                .map(OrderItemDTO::shipping)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal subtotalProducts = paymentRequestDTO.order().stream()
                .map(OrderItemDTO::totalItemPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return  new PaymentResponseDTO(
                paymentRequestDTO.paymentType(),
                subtotalProducts,
                totalShipping,
                subtotalProducts.add(totalShipping),
                paymentRequestDTO.order()
        );


    }
}
