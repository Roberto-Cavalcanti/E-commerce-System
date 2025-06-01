package com.project.ecommerce.models;

import com.project.ecommerce.models.enums.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size( max = 50)
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    private String description;

    @NotNull
    @Positive
    private BigDecimal startingPrice;

    @Size(max = 255)
    private String image;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "product")
    private List<ProductVariant> variants;
}