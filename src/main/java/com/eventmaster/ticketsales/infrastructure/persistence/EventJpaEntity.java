package com.eventmaster.ticketsales.infrastructure.persistence;

import com.eventmaster.ticketsales.domain.model.Event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade JPA para mapeamento da tabela de eventos.
 * Separada do modelo de domínio para manter a Clean Architecture.
 */
@Entity
@Table(name = "events")
public class EventJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    private String venue;

    @Column(name = "total_tickets", nullable = false)
    private int totalTickets;

    @Column(name = "available_tickets", nullable = false)
    private int availableTickets;

    @Column(name = "ticket_price", nullable = false)
    private BigDecimal ticketPrice;

    public EventJpaEntity() {}

    public static EventJpaEntity fromDomain(Event event) {
        EventJpaEntity entity = new EventJpaEntity();
        entity.id = event.getId();
        entity.name = event.getName();
        entity.description = event.getDescription();
        entity.dateTime = event.getDateTime();
        entity.venue = event.getVenue();
        entity.totalTickets = event.getTotalTickets();
        entity.availableTickets = event.getAvailableTickets();
        entity.ticketPrice = event.getTicketPrice();
        return entity;
    }

    public Event toDomain() {
        Event event = new Event(id, name, description, dateTime, venue, totalTickets, ticketPrice);
        event.setAvailableTickets(availableTickets);
        return event;
    }

    // Getters para JPA
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getVenue() { return venue; }
    public int getTotalTickets() { return totalTickets; }
    public int getAvailableTickets() { return availableTickets; }
    public BigDecimal getTicketPrice() { return ticketPrice; }
}
