package com.project.ecommerce.controllers;

import com.project.ecommerce.dto.ShippingRequestDTO;
import com.project.ecommerce.dto.ShippingResponseDTO;
import com.project.ecommerce.services.CalcShippingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product/shipping")
public class ShippingController {
    private final CalcShippingService calcShippingService;

    public ShippingController() {
        calcShippingService = new CalcShippingService();
    }

    @GetMapping
    public ResponseEntity<ShippingResponseDTO> calculateShippingTo(@RequestBody ShippingRequestDTO request){
        ShippingResponseDTO response = calcShippingService.calcShipping(request);
        return ResponseEntity.ok(response);
    }
}
