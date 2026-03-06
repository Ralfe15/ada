package com.eventmaster.ticketsales.domain.strategy;

import java.math.BigDecimal;

/**
 * Implementação da estratégia de pagamento via Cartão de Crédito.
 */
public class CreditCardPayment implements PaymentStrategy {

    @Override
    public boolean pay(BigDecimal amount) {
        // Simulação: pagamento com cartão de crédito sempre aprovado para fins acadêmicos
        System.out.println("Processando pagamento de R$ " + amount + " via Cartão de Crédito...");
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "CREDIT_CARD";
    }
}
