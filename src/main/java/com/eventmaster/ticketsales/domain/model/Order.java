package com.eventmaster.ticketsales.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade de domínio que representa um pedido de compra de ingressos.
 */
public class Order {

    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELLED
    }

    private UUID id;
    private UUID eventId;
    private String customerEmail;
    private int quantity;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private Status status;
    private LocalDateTime createdAt;

    public Order() {}

    public Order(UUID eventId, String customerEmail, int quantity,
                 BigDecimal totalAmount, PaymentMethod paymentMethod) {
        this.id = UUID.randomUUID();
        this.eventId = eventId;
        this.customerEmail = customerEmail;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = Status.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void confirm() {
        this.status = Status.CONFIRMED;
    }

    public void cancel() {
        this.status = Status.CANCELLED;
    }

    // Getters e Setters

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
