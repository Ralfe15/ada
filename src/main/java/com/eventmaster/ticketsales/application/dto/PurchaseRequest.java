package com.eventmaster.ticketsales.application.dto;

import com.eventmaster.ticketsales.domain.model.PaymentMethod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * DTO de entrada para a compra de ingressos.
 * Utiliza Bean Validation para proteção contra inputs inválidos (OWASP Top 10).
 */
public class PurchaseRequest {

    @NotNull(message = "O ID do evento é obrigatório.")
    private UUID eventId;

    @NotNull(message = "O e-mail do cliente é obrigatório.")
    @Email(message = "E-mail inválido.")
    private String customerEmail;

    @Min(value = 1, message = "A quantidade deve ser pelo menos 1.")
    private int quantity;

    @NotNull(message = "O método de pagamento é obrigatório.")
    private PaymentMethod paymentMethod;

    public PurchaseRequest() {}

    public PurchaseRequest(UUID eventId, String customerEmail, int quantity, PaymentMethod paymentMethod) {
        this.eventId = eventId;
        this.customerEmail = customerEmail;
        this.quantity = quantity;
        this.paymentMethod = paymentMethod;
    }

    public UUID getEventId() { return eventId; }
    public void setEventId(UUID eventId) { this.eventId = eventId; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
}
