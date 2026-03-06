package com.eventmaster.ticketsales.infrastructure.persistence;

import com.eventmaster.ticketsales.domain.model.Event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes unitarios para o mapeamento dominio <-> JPA da entidade Event.
 */
class EventJpaEntityTest {

    @Test
    @DisplayName("Deve converter de dominio para JPA e vice-versa sem perda de dados")
    void shouldMapDomainToJpaAndBack() {
        // Arrange
        UUID id = UUID.randomUUID();
        Event original = new Event(
                id,
                "Festival de Jazz",
                "Jazz ao vivo",
                LocalDateTime.of(2026, 7, 20, 18, 0),
                "Parque da Cidade",
                200,
                new BigDecimal("120.00")
        );
        original.reserveTickets(10); // disponivel = 190

        // Act
        EventJpaEntity jpaEntity = EventJpaEntity.fromDomain(original);
        Event restored = jpaEntity.toDomain();

        // Assert
        assertEquals(id, restored.getId());
        assertEquals("Festival de Jazz", restored.getName());
        assertEquals("Jazz ao vivo", restored.getDescription());
        assertEquals(LocalDateTime.of(2026, 7, 20, 18, 0), restored.getDateTime());
        assertEquals("Parque da Cidade", restored.getVenue());
        assertEquals(200, restored.getTotalTickets());
        assertEquals(190, restored.getAvailableTickets());
        assertEquals(new BigDecimal("120.00"), restored.getTicketPrice());
    }

    @Test
    @DisplayName("Deve preservar getters da entidade JPA")
    void shouldExposeJpaGetters() {
        // Arrange
        UUID id = UUID.randomUUID();
        Event event = new Event(
                id, "Show", "Descricao",
                LocalDateTime.of(2026, 1, 1, 20, 0),
                "Arena", 100, new BigDecimal("50.00")
        );

        // Act
        EventJpaEntity entity = EventJpaEntity.fromDomain(event);

        // Assert
        assertEquals(id, entity.getId());
        assertEquals("Show", entity.getName());
        assertEquals("Descricao", entity.getDescription());
        assertEquals(LocalDateTime.of(2026, 1, 1, 20, 0), entity.getDateTime());
        assertEquals("Arena", entity.getVenue());
        assertEquals(100, entity.getTotalTickets());
        assertEquals(100, entity.getAvailableTickets());
        assertEquals(new BigDecimal("50.00"), entity.getTicketPrice());
    }
}
