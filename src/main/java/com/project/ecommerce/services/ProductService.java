package com.project.ecommerce.services;

import com.project.ecommerce.dto.ProductResponseDTO;
import com.project.ecommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
}
