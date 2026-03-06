package com.eventmaster.ticketsales.infrastructure.persistence;

import com.eventmaster.ticketsales.domain.model.Order;
import com.eventmaster.ticketsales.domain.model.PaymentMethod;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade JPA para mapeamento da tabela de pedidos.
 */
@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    private UUID id;

    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Order.Status status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public OrderJpaEntity() {}

    public static OrderJpaEntity fromDomain(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.id = order.getId();
        entity.eventId = order.getEventId();
        entity.customerEmail = order.getCustomerEmail();
        entity.quantity = order.getQuantity();
        entity.totalAmount = order.getTotalAmount();
        entity.paymentMethod = order.getPaymentMethod();
        entity.status = order.getStatus();
        entity.createdAt = order.getCreatedAt();
        return entity;
    }

    public Order toDomain() {
        Order order = new Order();
        order.setId(id);
        order.setEventId(eventId);
        order.setCustomerEmail(customerEmail);
        order.setQuantity(quantity);
        order.setTotalAmount(totalAmount);
        order.setPaymentMethod(paymentMethod);
        order.setStatus(status);
        order.setCreatedAt(createdAt);
        return order;
    }

    // Getters para JPA
    public UUID getId() { return id; }
    public UUID getEventId() { return eventId; }
    public String getCustomerEmail() { return customerEmail; }
    public int getQuantity() { return quantity; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public Order.Status getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
