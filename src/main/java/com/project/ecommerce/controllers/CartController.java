package com.project.ecommerce.controllers;

import com.project.ecommerce.dto.OrderItemRequestDTO;
import com.project.ecommerce.dto.OrderItemDTO;
import com.project.ecommerce.dto.CartResponseDTO;
import com.project.ecommerce.dto.UserDTO;
import com.project.ecommerce.models.User;
import com.project.ecommerce.services.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<String> addItemToCart(
            @Valid @RequestBody OrderItemRequestDTO request,
            UserDTO user
            ) {
        cartService.addItemToCart(request, user);
        return ResponseEntity.status(HttpStatus.OK).body("Item added to the cart");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteItemFromCart(
            @Valid @RequestBody OrderItemDTO request
            ) {
        cartService.deleteItemFromCart(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item successfully deleted.");
    }

   @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(User id){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.buildCartResponse(id));
    }


}
