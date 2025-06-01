package com.project.ecommerce.controllers;

import com.project.ecommerce.dto.ProductResponseDTO;
import com.project.ecommerce.dto.ProductVariantResponseDTO;
import com.project.ecommerce.dto.SearchProductDTO;
import com.project.ecommerce.exceptions.ProductServiceException;
import com.project.ecommerce.models.enums.Category;
import com.project.ecommerce.repositories.ProductVariantRepository;
import com.project.ecommerce.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService, ProductVariantRepository productVariantRepository) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listAllProducts() {
        List<ProductResponseDTO> products = productService.listAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProductByName(@RequestBody SearchProductDTO searchProductDTO) {
        List<ProductResponseDTO> products = productService.searchProductByName(searchProductDTO.name());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponseDTO>> filterProductsByCategory(@RequestParam(name = "category" ) Category category) {
        if (category == null) {
            log.warn("The category parameter is null");
            throw new ProductServiceException("This field is required.");
        }
        try {
            List<ProductResponseDTO> products = productService.filterProductsByCategory(category);
            if (products.isEmpty()) {
                log.info("No products were found for this category: {}", category);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            log.error("An error occurred while trying to filter by category: {}", category, e);
            throw new ProductServiceException("Internal error processing the request.");
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductVariantResponseDTO>> listProductVariants(@PathVariable Long productId) {
        List<ProductVariantResponseDTO> variants = productService.viewAllProductsVariants(productId);
        return ResponseEntity.status(HttpStatus.OK).body(variants);
    }
}
