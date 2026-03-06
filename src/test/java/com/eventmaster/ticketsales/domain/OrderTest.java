package com.eventmaster.ticketsales.domain;

import com.eventmaster.ticketsales.domain.model.Order;
import com.eventmaster.ticketsales.domain.model.PaymentMethod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Testes unitarios para a entidade de dominio Order.
 */
class OrderTest {

    @Test
    @DisplayName("Deve criar pedido com status PENDING e campos preenchidos")
    void shouldCreateOrderWithPendingStatus() {
        // Arrange
        UUID eventId = UUID.randomUUID();
        String email = "cliente@email.com";
        int quantity = 3;
        BigDecimal total = new BigDecimal("300.00");
        PaymentMethod method = PaymentMethod.PIX;

        // Act
        Order order = new Order(eventId, email, quantity, total, method);

        // Assert
        assertNotNull(order.getId());
        assertEquals(eventId, order.getEventId());
        assertEquals(email, order.getCustomerEmail());
        assertEquals(quantity, order.getQuantity());
        assertEquals(total, order.getTotalAmount());
        assertEquals(method, order.getPaymentMethod());
        assertEquals(Order.Status.PENDING, order.getStatus());
        assertNotNull(order.getCreatedAt());
    }

    @Test
    @DisplayName("Deve confirmar pedido alterando status para CONFIRMED")
    void shouldConfirmOrder() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), "a@b.com", 1,
                new BigDecimal("50.00"), PaymentMethod.CREDIT_CARD);

        // Act
        order.confirm();

        // Assert
        assertEquals(Order.Status.CONFIRMED, order.getStatus());
    }

    @Test
    @DisplayName("Deve cancelar pedido alterando status para CANCELLED")
    void shouldCancelOrder() {
        // Arrange
        Order order = new Order(UUID.randomUUID(), "a@b.com", 1,
                new BigDecimal("50.00"), PaymentMethod.BOLETO);

        // Act
        order.cancel();

        // Assert
        assertEquals(Order.Status.CANCELLED, order.getStatus());
    }

    @Test
    @DisplayName("Deve permitir alterar campos via setters")
    void shouldSetFieldsCorrectly() {
        // Arrange
        Order order = new Order();
        UUID id = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();

        // Act
        order.setId(id);
        order.setEventId(eventId);
        order.setCustomerEmail("novo@email.com");
        order.setQuantity(5);
        order.setTotalAmount(new BigDecimal("250.00"));
        order.setPaymentMethod(PaymentMethod.PIX);
        order.setStatus(Order.Status.CONFIRMED);

        // Assert
        assertEquals(id, order.getId());
        assertEquals(eventId, order.getEventId());
        assertEquals("novo@email.com", order.getCustomerEmail());
        assertEquals(5, order.getQuantity());
        assertEquals(new BigDecimal("250.00"), order.getTotalAmount());
        assertEquals(PaymentMethod.PIX, order.getPaymentMethod());
        assertEquals(Order.Status.CONFIRMED, order.getStatus());
    }
}
