package com.project.ecommerce.services;

import com.project.ecommerce.dto.ProductResponseDTO;
import com.project.ecommerce.dto.ProductVariantResponseDTO;
import com.project.ecommerce.models.Shop;
import com.project.ecommerce.models.enums.Category;
import com.project.ecommerce.repositories.ProductRepository;
import com.project.ecommerce.repositories.ProductVariantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

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
                        product.getPrice(),
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
                        product.getPrice(),
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
                        product.getPrice(),
                        product.getImage(),
                        product.getCategory() != null ? product.getCategory().toString() : null,
                        product.getShop().getName()
                )).collect(Collectors.toList());
    }

    public List<ProductResponseDTO> filterProductsByShop(Long shopId) {
        return productRepository.findProductsByShop(shopId).stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
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
                        productVariant.getStockItem().getQuantity()
                )).collect(Collectors.toList());
    }
}
