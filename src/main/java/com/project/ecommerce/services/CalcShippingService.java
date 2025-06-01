package com.project.ecommerce.services;

import com.project.ecommerce.dto.ShippingRequestDTO;
import com.project.ecommerce.dto.ShippingResponseDTO;
import org.springframework.stereotype.Service;


@Service
public  class CalcShippingService {

    public ShippingResponseDTO calcShipping(ShippingRequestDTO request) {
        return new ShippingResponseDTO(request.state().getValue(), request.date().plusDays(15));
    }
}
