package com.project.ecommerce.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
public class StockItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "variant_id")
    @NotNull
    private ProductVariant variant;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    @NotNull
    private Stock stock;

    @NotNull
    @PositiveOrZero
    private Integer quantity;
}