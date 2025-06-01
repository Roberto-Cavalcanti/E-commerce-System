package com.project.ecommerce.services;

import com.project.ecommerce.dto.OrderItemRequestDTO;
import com.project.ecommerce.dto.UserDTO;
import com.project.ecommerce.models.*;
import com.project.ecommerce.models.enums.OrderStatus;
import com.project.ecommerce.repositories.CartRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductVariantRepository productVariantRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    private User user;
    private UserDTO userDTO;
    private Product product;
    private ProductVariant variant;
    private OrderItemRequestDTO request;

    @BeforeEach
    void setUp() {
        // Configurar dados de teste
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        userDTO = new UserDTO(user.getName(), user.getEmail());

        product = new Product();
        product.setId(1L);
        product.setName("Camiseta Nike");
        product.setStartingPrice(new BigDecimal("50.00"));

        variant = new ProductVariant();
        variant.setId(1L);
        variant.setSize("M");
        variant.setProduct(product);
        variant.setQuantity(10);

        request = new OrderItemRequestDTO(1L, 2, BigDecimal.valueOf(20)); // variantId = 1, quantity = 2
    }

    @Test
    void addItemToCart_NewItem_Success() {

    }


    @Test
    void addItemToCart_UserNotFound_ThrowsException() {
        // Configurar mock para usuário não encontrado
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        // Verificar exceção
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> cartService.addItemToCart(request, userDTO));
        assertEquals("User not found. User need be logged in.", exception.getMessage());

        // Verificar que outros repositórios não foram chamados
        verifyNoInteractions(cartRepository, productVariantRepository);
    }

    @Test
    void addItemToCart_VariantNotFound_ThrowsException() {
        // Configurar mocks
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserIdAndStatus(1L, OrderStatus.AGUARDANDO_PAGAMENTO))
                .thenReturn(Optional.empty());
        when(productVariantRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificar exceção
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> cartService.addItemToCart(request, userDTO));
        assertEquals("Product variant not found", exception.getMessage());

        // Verificar interações
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(cartRepository, times(1)).findByUserIdAndStatus(1L, OrderStatus.AGUARDANDO_PAGAMENTO);
        verify(productVariantRepository, times(1)).findById(1L);
    }

    @Test
    void addItemToCart_ProductVariantNotFound_ThrowsException() {
        // Configurar mocks
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserIdAndStatus(1L, OrderStatus.AGUARDANDO_PAGAMENTO))
                .thenReturn(Optional.empty());
        when(productVariantRepository.findById(1L)).thenReturn(Optional.of(variant));

        // Verificar exceção
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> cartService.addItemToCart(request, userDTO));
        assertEquals("product variant item not found", exception.getMessage());
    }

    @Test
    void addItemToCart_InsufficientStock_ThrowsException() {
        // Configurar stock com quantidade insuficiente
        variant.setQuantity(1); // Menos que a quantidade solicitada (2)

        // Configurar mocks
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(cartRepository.findByUserIdAndStatus(1L, OrderStatus.AGUARDANDO_PAGAMENTO))
                .thenReturn(Optional.empty());
        when(productVariantRepository.findById(1L)).thenReturn(Optional.of(variant));

        // Verificar exceção
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> cartService.addItemToCart(request, userDTO));
        assertEquals("Insufficient stock for variant ID 1", exception.getMessage());
    }
}