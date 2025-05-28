package com.project.ecommerce.services;

import com.project.ecommerce.dto.AddCartItemRequestDTO;
import com.project.ecommerce.dto.CartItemDTO;
import com.project.ecommerce.dto.CartResponseDTO;
import com.project.ecommerce.models.*;
import com.project.ecommerce.models.enums.OrderStatus;
import com.project.ecommerce.repositories.OrderRepository;
import com.project.ecommerce.repositories.ProductVariantRepository;
import com.project.ecommerce.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductVariantRepository productVariantRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    private User user;
    private Product product;
    private ProductVariant variant;
    private AddCartItemRequestDTO request;

    @BeforeEach
    void setUp() {
        // Configurar dados de teste
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        product = new Product();
        product.setId(1L);
        product.setName("Camiseta Nike");
        product.setPrice(new BigDecimal("50.00"));

        variant = new ProductVariant();
        variant.setId(1L);
        variant.setSize("M");
        variant.setProduct(product);
        variant.setQuantity(10);

        request = new AddCartItemRequestDTO(1L, 2); // variantId = 1, quantity = 2
    }

    @Test
    void addItemToCart_NewItem_Success() {
        // Configurar mocks
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(orderRepository.findByUserIdAndStatus(1L, OrderStatus.AGUARDANDO_PAGAMENTO))
                .thenReturn(Optional.empty());
        when(productVariantRepository.findById(1L)).thenReturn(Optional.of(variant));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L); // Simular ID gerado
            return order;
        });

        // Criar um novo carrinho
        Order newCart = new Order();
        newCart.setUser(user);
        newCart.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        newCart.setItems(new ArrayList<>());
        newCart.setShippingPrice(BigDecimal.ZERO);

        // Executar o método
        CartResponseDTO response = cartService.addItemToCart(request);

        // Verificar resultados
        assertNotNull(response);
        assertEquals(1L, response.orderId());
        assertEquals("Test User", response.userName());
        assertEquals(1, response.items().size());

        CartItemDTO item = response.items().get(0);
        assertEquals(1L, item.variantId());
        assertEquals("Camiseta Nike", item.productName());
        assertEquals("M", item.size());
        assertEquals(2, item.quantity());
        assertEquals(new BigDecimal("50.00"), item.unitPrice());
        assertEquals(new BigDecimal("100.00"), item.totalItemPrice());
        assertEquals(new BigDecimal("100.00"), response.totalPrice());

        // Verificar interações
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(productVariantRepository, times(1)).findById(1L);
    }

    @Test
    void addItemToCart_UpdateExistingItem_Success() {
        // Configurar carrinho existente com um item
        OrderItem existingItem = new OrderItem();
        existingItem.setId(1L);
        existingItem.setVariant(variant);
        existingItem.setQuantity(1);
        existingItem.setOrder(new Order());

        Order cart = new Order();
        cart.setId(1L);
        cart.setUser(user);
        cart.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        cart.setItems(new ArrayList<>(List.of(existingItem)));
        cart.setShippingPrice(BigDecimal.ZERO);

        // Configurar mocks
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(orderRepository.findByUserIdAndStatus(1L, OrderStatus.AGUARDANDO_PAGAMENTO))
                .thenReturn(Optional.of(cart));
        when(productVariantRepository.findById(1L)).thenReturn(Optional.of(variant));
        when(orderRepository.save(any(Order.class))).thenReturn(cart);

        // Executar o método
        CartResponseDTO response = cartService.addItemToCart(request);

        // Verificar resultados
        assertNotNull(response);
        assertEquals(1L, response.orderId());
        assertEquals(1, response.items().size());
        assertEquals(2, response.items().get(0).quantity()); // Quantidade atualizada
        assertEquals(new BigDecimal("100.00"), response.totalPrice());

        // Verificar interações
        verify(orderRepository, times(1)).save(cart);
    }

    @Test
    void addItemToCart_UserNotFound_ThrowsException() {
        // Configurar mock para usuário não encontrado
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Verificar exceção
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> cartService.addItemToCart(request));
        assertEquals("User not found. User need be logged in.", exception.getMessage());

        // Verificar que outros repositórios não foram chamados
        verifyNoInteractions(orderRepository, productVariantRepository);
    }

    @Test
    void addItemToCart_VariantNotFound_ThrowsException() {
        // Configurar mocks
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(orderRepository.findByUserIdAndStatus(1L, OrderStatus.AGUARDANDO_PAGAMENTO))
                .thenReturn(Optional.empty());
        when(productVariantRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificar exceção
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> cartService.addItemToCart(request));
        assertEquals("Product variant not found", exception.getMessage());

        // Verificar interações
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(orderRepository, times(1)).findByUserIdAndStatus(1L, OrderStatus.AGUARDANDO_PAGAMENTO);
        verify(productVariantRepository, times(1)).findById(1L);
//        verifyNoInteractions();
    }

    @Test
    void addItemToCart_ProductVariantNotFound_ThrowsException() {
        // Configurar mocks
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(orderRepository.findByUserIdAndStatus(1L, OrderStatus.AGUARDANDO_PAGAMENTO))
                .thenReturn(Optional.empty());
        when(productVariantRepository.findById(1L)).thenReturn(Optional.of(variant));

        // Verificar exceção
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> cartService.addItemToCart(request));
        assertEquals("product variant item not found", exception.getMessage());
    }

    @Test
    void addItemToCart_InsufficientStock_ThrowsException() {
        // Configurar stock com quantidade insuficiente
        variant.setQuantity(1); // Menos que a quantidade solicitada (2)

        // Configurar mocks
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(orderRepository.findByUserIdAndStatus(1L, OrderStatus.AGUARDANDO_PAGAMENTO))
                .thenReturn(Optional.empty());
        when(productVariantRepository.findById(1L)).thenReturn(Optional.of(variant));

        // Verificar exceção
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> cartService.addItemToCart(request));
        assertEquals("Insufficient stock for variant ID 1", exception.getMessage());
    }
}