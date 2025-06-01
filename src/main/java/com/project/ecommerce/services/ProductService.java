package com.project.ecommerce.services;

import com.project.ecommerce.dto.ProductResponseDTO;
import com.project.ecommerce.dto.ProductVariantResponseDTO;
import com.project.ecommerce.models.ProductVariant;
import com.project.ecommerce.models.Shop;
import com.project.ecommerce.models.enums.Category;
import com.project.ecommerce.repositories.ProductRepository;
import com.project.ecommerce.repositories.ProductVariantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductVariantRepository productVariantRepository) {
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
    }

    public List<ProductResponseDTO> listAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getStartingPrice(),
                        product.getImage(),
                        product.getCategory() != null ? product.getCategory().toString() : null,
                        product.getShop().getName()
                ))
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> searchProductByName(String productName) {
        Pattern pattern = Pattern.compile("\\b" + productName, Pattern.CASE_INSENSITIVE);
        return productRepository.findAll().stream()
                .filter(product -> pattern.matcher(product.getName()).find())
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getStartingPrice(),
                        product.getImage(),
                        product.getCategory() != null ? product.getCategory().toString() : null,
                        product.getShop().getName()
                )).collect(Collectors.toList());
    }

    public List<ProductResponseDTO> filterProductsByCategory(Category category) {
        return productRepository.findAll().stream()
                .filter(product -> product.getCategory() == category)
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getStartingPrice(),
                        product.getImage(),
                        product.getCategory() != null ? product.getCategory().toString() : null,
                        product.getShop().getName()
                )).collect(Collectors.toList());
    }

    public List<ProductResponseDTO> filterProductsByShop(Shop shopId) {
        return productRepository.findProductsByShop(shopId).stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getStartingPrice(),
                        product.getImage(),
                        product.getCategory() != null ? product.getCategory().toString() : null,
                        product.getShop().getName()
                )).collect(Collectors.toList());
    }

    public List<ProductVariantResponseDTO> viewAllProductsVariants(Long ProductId){
        return productVariantRepository.findAll().stream()
                .map(productVariant -> new ProductVariantResponseDTO(
                        productVariant.getId(),
                        productVariant.getSize(),
                        productVariant.getPrice(),
                        productVariant.getQuantity()
                )).collect(Collectors.toList());
    }

    public void checkStockAvailability(Long variantId, Integer requestedQuantity) {
        ProductVariant productVariant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Variant not found in stock"));
        if (productVariant.getQuantity() < requestedQuantity) {
            throw new IllegalStateException("Insufficient stock for variant ID " + variantId);
        }
    }

    public void updateStock(Long variantId, Integer quantityToRemove) {
        ProductVariant productVariant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Variant not found in stock"));
        int newQuantity = productVariant.getQuantity() - quantityToRemove;
        if (newQuantity < 0) {
            throw new IllegalStateException("Cannot reduce stock below zero");
        }
        productVariant.setQuantity(newQuantity);
        productVariantRepository.save(productVariant);
    }
}
