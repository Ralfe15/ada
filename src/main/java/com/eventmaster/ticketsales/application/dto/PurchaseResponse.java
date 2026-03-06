package com.eventmaster.ticketsales.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO de saída com os dados do pedido confirmado.
 */
public class PurchaseResponse {

    private UUID orderId;
    private UUID eventId;
    private int quantity;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String status;

    public PurchaseResponse() {}

    public PurchaseResponse(UUID orderId, UUID eventId, int quantity,
                            BigDecimal totalAmount, String paymentMethod, String status) {
        this.orderId = orderId;
        this.eventId = eventId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
