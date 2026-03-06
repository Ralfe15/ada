package com.eventmaster.ticketsales.domain.strategy;

import java.math.BigDecimal;

/**
 * Implementação da estratégia de pagamento via PIX.
 */
public class PixPayment implements PaymentStrategy {

    @Override
    public boolean pay(BigDecimal amount) {
        // Simulação: pagamento via PIX sempre aprovado para fins acadêmicos
        System.out.println("Processando pagamento de R$ " + amount + " via PIX...");
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "PIX";
    }
}
