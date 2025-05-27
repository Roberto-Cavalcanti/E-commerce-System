package com.project.ecommerce.controllers;

import com.project.ecommerce.dto.AddCartItemRequestDTO;
import com.project.ecommerce.dto.CartResponseDTO;
import com.project.ecommerce.services.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addItemToCart(
            @Valid @RequestBody AddCartItemRequestDTO request
            ) {//Authentication authentication
        /*if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(null);
        }*/
        CartResponseDTO cart = cartService.addItemToCart(request);//, authentication
        return ResponseEntity.ok(cart);
    }
}
