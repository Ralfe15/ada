package com.eventmaster.ticketsales.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade de domínio que representa um evento disponível para venda de ingressos.
 */
public class Event {

    private UUID id;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private String venue;
    private int totalTickets;
    private int availableTickets;
    private BigDecimal ticketPrice;

    public Event() {}

    public Event(UUID id, String name, String description, LocalDateTime dateTime,
                 String venue, int totalTickets, BigDecimal ticketPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.venue = venue;
        this.totalTickets = totalTickets;
        this.availableTickets = totalTickets;
        this.ticketPrice = ticketPrice;
    }

    /**
     * Reserva uma quantidade de ingressos, diminuindo o estoque disponível.
     * @throws IllegalStateException se não houver ingressos suficientes.
     */
    public void reserveTickets(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        if (quantity > availableTickets) {
            throw new IllegalStateException("Ingressos insuficientes. Disponíveis: " + availableTickets);
        }
        this.availableTickets -= quantity;
    }

    public boolean hasAvailableTickets(int quantity) {
        return availableTickets >= quantity;
    }

    // Getters e Setters

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

    public int getAvailableTickets() { return availableTickets; }
    public void setAvailableTickets(int availableTickets) { this.availableTickets = availableTickets; }

    public BigDecimal getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(BigDecimal ticketPrice) { this.ticketPrice = ticketPrice; }
}
