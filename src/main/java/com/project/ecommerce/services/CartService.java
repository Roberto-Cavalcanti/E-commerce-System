package com.project.ecommerce.services;

import com.project.ecommerce.dto.AddCartItemRequestDTO;
import com.project.ecommerce.dto.CartItemDTO;
import com.project.ecommerce.dto.CartResponseDTO;
import com.project.ecommerce.models.*;
import com.project.ecommerce.models.enums.OrderStatus;
import com.project.ecommerce.models.enums.States;
import com.project.ecommerce.repositories.OrderRepository;
import com.project.ecommerce.repositories.ProductVariantRepository;
import com.project.ecommerce.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final OrderRepository orderRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;

    public CartService(OrderRepository orderRepository,
                       ProductVariantRepository productVariantRepository,
                       UserRepository userRepository) {


        this.orderRepository = orderRepository;
        this.productVariantRepository = productVariantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CartResponseDTO addItemToCart(AddCartItemRequestDTO request) {//, Authentication authentication
        // Obter o usuário autenticado (assumindo que o email está no principal)
//        String userEmail = authentication.email();
        User user = userRepository.findByEmail("test@example.com")
                .orElseThrow(() -> new EntityNotFoundException("User not found. User need be logged in.")); // Substitua por uma busca no UserRepository
//        user.setEmail(userEmail); // Simulação; use userRepository.findByEmail

        // Verificar se existe um carrinho (pedido com status AGUARDANDO_PAGAMENTO)
        Order cart = orderRepository.findByUserIdAndStatus(user.getId(), OrderStatus.AGUARDANDO_PAGAMENTO)
                .orElseGet(() -> {
                    Order newCart = new Order();
                    newCart.setUser(user);
                    newCart.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
                    return orderRepository.save(newCart);
                });

        // Verificar a variante do produto
        ProductVariant variant = productVariantRepository.findById(request.variantId())
                .orElseThrow(() -> new EntityNotFoundException("Product variant not found"));

        // Verificar estoque
        if (variant.getQuantity() < request.quantity()) {//update
            throw new IllegalStateException("Insufficient stock for variant ID " + request.variantId());
        }

        // Adicionar ou atualizar item no carrinho
        OrderItem orderItem = cart.getItems().stream()
                .filter(item -> item.getVariant().getId().equals(request.variantId()))
                .findFirst()
                .orElseGet(() -> {
                    OrderItem newItem = new OrderItem();
                    newItem.setOrder(cart);
                    newItem.setVariant(variant);
                    cart.getItems().add(newItem);
                    return newItem;
                });

        orderItem.setQuantity(request.quantity());
        orderRepository.save(cart);

        // Retornar o carrinho atualizado
        return buildCartResponse(cart);
    }

    private CartResponseDTO buildCartResponse(Order cart) {
        List<CartItemDTO> cartItems = cart.getItems().stream()
                .map(item -> new CartItemDTO(
                        item.getVariant().getId(),
                        item.getVariant().getProduct().getName(),
                        item.getVariant().getSize(),
                        item.getQuantity(),
                        item.getVariant().getProduct().getPrice(),
                        item.getVariant().getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                ))
                .collect(Collectors.toList());

        BigDecimal totalPrice = cartItems.stream()
                .map(CartItemDTO::totalItemPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(cart.getShippingPrice());

        return new CartResponseDTO(
                cart.getId(),
                cart.getUser().getName(),
                cartItems,
                cart.getShippingPrice(),
                totalPrice
        );
    }
}
