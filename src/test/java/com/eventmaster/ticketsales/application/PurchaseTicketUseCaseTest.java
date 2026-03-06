package com.eventmaster.ticketsales.application;

import com.eventmaster.ticketsales.application.dto.PurchaseRequest;
import com.eventmaster.ticketsales.application.dto.PurchaseResponse;
import com.eventmaster.ticketsales.application.usecase.PurchaseTicketUseCase;
import com.eventmaster.ticketsales.domain.model.Event;
import com.eventmaster.ticketsales.domain.model.Order;
import com.eventmaster.ticketsales.domain.model.PaymentMethod;
import com.eventmaster.ticketsales.domain.repository.EventRepository;
import com.eventmaster.ticketsales.domain.repository.OrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testes unitários para o caso de uso PurchaseTicketUseCase.
 * Utiliza Mockito para mockar os repositórios (ports), isolando a lógica de negócio.
 * Padrão Arrange-Act-Assert.
 */
@ExtendWith(MockitoExtension.class)
class PurchaseTicketUseCaseTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private OrderRepository orderRepository;

    private PurchaseTicketUseCase useCase;

    private UUID eventId;
    private Event event;

    @BeforeEach
    void setUp() {
        useCase = new PurchaseTicketUseCase(eventRepository, orderRepository);

        eventId = UUID.randomUUID();
        event = new Event(
                eventId,
                "Festival de Música",
                "Grande festival",
                LocalDateTime.of(2026, 7, 20, 18, 0),
                "Parque Central",
                500,
                new BigDecimal("100.00")
        );
    }

    @Test
    @DisplayName("Deve comprar ingressos com sucesso via Cartão de Crédito")
    void shouldPurchaseTicketsSuccessfully() {
        // Arrange
        PurchaseRequest request = new PurchaseRequest(eventId, "cliente@email.com", 2, PaymentMethod.CREDIT_CARD);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        PurchaseResponse response = useCase.execute(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getOrderId());
        assertEquals(eventId, response.getEventId());
        assertEquals(2, response.getQuantity());
        assertEquals(new BigDecimal("200.00"), response.getTotalAmount());
        assertEquals("CREDIT_CARD", response.getPaymentMethod());
        assertEquals("CONFIRMED", response.getStatus());

        verify(eventRepository).save(any(Event.class));
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    @DisplayName("Deve comprar ingressos com sucesso via PIX")
    void shouldPurchaseTicketsWithPix() {
        // Arrange
        PurchaseRequest request = new PurchaseRequest(eventId, "cliente@email.com", 1, PaymentMethod.PIX);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        PurchaseResponse response = useCase.execute(request);

        // Assert
        assertEquals("PIX", response.getPaymentMethod());
        assertEquals(new BigDecimal("100.00"), response.getTotalAmount());
    }

    @Test
    @DisplayName("Deve lançar exceção quando evento não é encontrado")
    void shouldThrowWhenEventNotFound() {
        // Arrange
        UUID unknownId = UUID.randomUUID();
        PurchaseRequest request = new PurchaseRequest(unknownId, "cliente@email.com", 1, PaymentMethod.CREDIT_CARD);
        when(eventRepository.findById(unknownId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(request));
        verify(orderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não há ingressos suficientes")
    void shouldThrowWhenInsufficientTickets() {
        // Arrange
        PurchaseRequest request = new PurchaseRequest(eventId, "cliente@email.com", 501, PaymentMethod.CREDIT_CARD);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> useCase.execute(request));
        verify(orderRepository, never()).save(any());
    }
}
