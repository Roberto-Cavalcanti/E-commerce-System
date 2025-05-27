package com.project.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "shop_id")
    @NotNull
    private Shop shop;

    @OneToMany(mappedBy = "stock")
    private List<StockItem> stockItems;
}
