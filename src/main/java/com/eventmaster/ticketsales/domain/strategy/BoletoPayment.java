package com.eventmaster.ticketsales.domain.strategy;

import java.math.BigDecimal;

/**
 * Implementação da estratégia de pagamento via Boleto Bancário.
 */
public class BoletoPayment implements PaymentStrategy {

    @Override
    public boolean pay(BigDecimal amount) {
        // Simulação: pagamento via boleto sempre aprovado para fins acadêmicos
        System.out.println("Processando pagamento de R$ " + amount + " via Boleto Bancário...");
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "BOLETO";
    }
}
