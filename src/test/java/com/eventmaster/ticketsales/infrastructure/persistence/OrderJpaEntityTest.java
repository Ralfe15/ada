package com.eventmaster.ticketsales.infrastructure.persistence;

import com.eventmaster.ticketsales.domain.model.Order;
import com.eventmaster.ticketsales.domain.model.PaymentMethod;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes unitarios para o mapeamento dominio <-> JPA da entidade Order.
 */
class OrderJpaEntityTest {

    @Test
    @DisplayName("Deve converter de dominio para JPA e vice-versa sem perda de dados")
    void shouldMapDomainToJpaAndBack() {
        // Arrange
        Order original = new Order(
                UUID.randomUUID(),
                "cliente@email.com",
                3,
                new BigDecimal("225.00"),
                PaymentMethod.CREDIT_CARD
        );
        original.confirm();

        // Act
        OrderJpaEntity jpaEntity = OrderJpaEntity.fromDomain(original);
        Order restored = jpaEntity.toDomain();

        // Assert
        assertEquals(original.getId(), restored.getId());
        assertEquals(original.getEventId(), restored.getEventId());
        assertEquals("cliente@email.com", restored.getCustomerEmail());
        assertEquals(3, restored.getQuantity());
        assertEquals(new BigDecimal("225.00"), restored.getTotalAmount());
        assertEquals(PaymentMethod.CREDIT_CARD, restored.getPaymentMethod());
        assertEquals(Order.Status.CONFIRMED, restored.getStatus());
        assertEquals(original.getCreatedAt(), restored.getCreatedAt());
    }

    @Test
    @DisplayName("Deve preservar getters da entidade JPA")
    void shouldExposeJpaGetters() {
        // Arrange
        Order order = new Order(
                UUID.randomUUID(),
                "test@test.com",
                2,
                new BigDecimal("100.00"),
                PaymentMethod.PIX
        );

        // Act
        OrderJpaEntity entity = OrderJpaEntity.fromDomain(order);

        // Assert
        assertEquals(order.getId(), entity.getId());
        assertEquals(order.getEventId(), entity.getEventId());
        assertEquals("test@test.com", entity.getCustomerEmail());
        assertEquals(2, entity.getQuantity());
        assertEquals(new BigDecimal("100.00"), entity.getTotalAmount());
        assertEquals(PaymentMethod.PIX, entity.getPaymentMethod());
        assertEquals(Order.Status.PENDING, entity.getStatus());
        assertEquals(order.getCreatedAt(), entity.getCreatedAt());
    }

    @Test
    @DisplayName("Deve mapear pedido com status CANCELLED corretamente")
    void shouldMapCancelledOrder() {
        // Arrange
        Order order = new Order(
                UUID.randomUUID(),
                "cancel@email.com",
                1,
                new BigDecimal("50.00"),
                PaymentMethod.BOLETO
        );
        order.cancel();

        // Act
        OrderJpaEntity entity = OrderJpaEntity.fromDomain(order);
        Order restored = entity.toDomain();

        // Assert
        assertEquals(Order.Status.CANCELLED, restored.getStatus());
        assertEquals(PaymentMethod.BOLETO, restored.getPaymentMethod());
    }
}
