package com.project.ecommerce.models;

import com.project.ecommerce.models.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    @NotNull
    @PositiveOrZero
    private BigDecimal shippingPrice = BigDecimal.ZERO;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.AGUARDANDO_PAGAMENTO;
}