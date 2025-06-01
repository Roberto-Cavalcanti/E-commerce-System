package com.project.ecommerce.services;

import com.project.ecommerce.dto.OrderItemRequestDTO;
import com.project.ecommerce.dto.OrderItemDTO;
import com.project.ecommerce.dto.CartResponseDTO;
import com.project.ecommerce.dto.UserDTO;
import com.project.ecommerce.models.*;
import com.project.ecommerce.models.enums.OrderStatus;
import com.project.ecommerce.repositories.CartRepository;
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
    private final CartRepository cartRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;
    private final ProductService productService;


    public CartService(CartRepository cartRepository,
                       ProductVariantRepository productVariantRepository,
                       UserRepository userRepository,
                       ProductService productService) {


        this.cartRepository = cartRepository;
        this.productVariantRepository = productVariantRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Transactional
    public void addItemToCart(OrderItemRequestDTO request, UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found. User need be logged in."));

        // Verificar se existe um carrinho (pedido com status AGUARDANDO_PAGAMENTO)
        Cart cart = cartRepository.findByUserIdAndStatus(user.getId(), OrderStatus.AGUARDANDO_PAGAMENTO)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
                    return cartRepository.save(newCart);
                });

        // Verificar a variante do produto e estoque
        ProductVariant variant = productVariantRepository.findById(request.variantId())
                        .orElseThrow( () -> new EntityNotFoundException("Variant product not found."));

        productService.checkStockAvailability(variant.getId(), request.quantity());

        // Adicionar ou atualizar item no carrinho
        OrderItem orderItem = cart.getItems().stream()
                .filter(item -> item.getVariant().getId().equals(request.variantId()))
                .findFirst()
                .orElseGet(() -> {
                    OrderItem newItem = new OrderItem();
                    newItem.setCart(cart);
                    newItem.setVariant(variant);
                    cart.getItems().add(newItem);
                    return newItem;
                });
        orderItem.setQuantity(request.quantity());
        cartRepository.save(cart);
    }

    public CartResponseDTO buildCartResponse(User user) {
        Cart cart = cartRepository.findByUserIdAndStatus(user.getId(), OrderStatus.AGUARDANDO_PAGAMENTO)
                .orElseThrow( () -> new EntityNotFoundException("User not found. User need be logged in."));
        List<OrderItemDTO> cartItems = cart.getItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getVariant().getId(),
                        item.getVariant().getProduct().getName(),
                        item.getVariant().getSize(),
                        item.getQuantity(),
                        item.getVariant().getProduct().getStartingPrice(),
                        item.getVariant().getProduct().getStartingPrice().multiply(BigDecimal.valueOf(item.getQuantity())),
                        item.getShippingPrice()
                ))
                .collect(Collectors.toList());

        BigDecimal totalShipping = cartItems.stream()
                .map(OrderItemDTO::shipping)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPrice = cartItems.stream()
                .map(OrderItemDTO::totalItemPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(totalShipping);

        return new CartResponseDTO(
                cart.getId(),
                cart.getUser().getName(),
                cartItems,
                totalShipping,
                totalPrice
        );
    }

    public void deleteItemFromCart(OrderItemDTO request) {
        cartRepository.findById(request.variantId())
                .ifPresent( cartRepository::delete );
    }
}
