package com.eventmaster.ticketsales.domain;

import com.eventmaster.ticketsales.domain.model.Event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes unitários para a lógica de domínio do Event.
 */
class EventTest {

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event(
                UUID.randomUUID(),
                "Show de Rock",
                "Grande evento musical",
                LocalDateTime.of(2026, 6, 15, 20, 0),
                "Arena Central",
                100,
                new BigDecimal("50.00")
        );
    }

    @Test
    @DisplayName("Deve reservar ingressos quando há disponibilidade")
    void shouldReserveTicketsWhenAvailable() {
        event.reserveTickets(5);
        assertEquals(95, event.getAvailableTickets());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não há ingressos suficientes")
    void shouldThrowWhenInsufficientTickets() {
        assertThrows(IllegalStateException.class, () -> event.reserveTickets(101));
    }

    @Test
    @DisplayName("Deve lançar exceção quando quantidade é zero ou negativa")
    void shouldThrowWhenQuantityIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> event.reserveTickets(0));
        assertThrows(IllegalArgumentException.class, () -> event.reserveTickets(-1));
    }

    @Test
    @DisplayName("Deve verificar disponibilidade de ingressos corretamente")
    void shouldCheckAvailability() {
        assertTrue(event.hasAvailableTickets(100));
        assertFalse(event.hasAvailableTickets(101));
    }
}
