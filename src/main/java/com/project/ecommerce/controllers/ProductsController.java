package com.project.ecommerce.controllers;

import com.project.ecommerce.dto.ProductResponseDTO;
import com.project.ecommerce.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listAllProducts() {
        List<ProductResponseDTO> products = productService.listAllProducts();
        return ResponseEntity.ok(products);
    }
}
