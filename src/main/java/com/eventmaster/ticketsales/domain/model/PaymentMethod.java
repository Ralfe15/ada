package com.eventmaster.ticketsales.domain.model;

/**
 * Enum representando os métodos de pagamento disponíveis.
 * Usado em conjunto com o Strategy Pattern.
 */
public enum PaymentMethod {
    CREDIT_CARD,
    PIX,
    BOLETO
}
