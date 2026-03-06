package com.eventmaster.ticketsales.domain.strategy;

import com.eventmaster.ticketsales.domain.model.PaymentMethod;

import java.util.EnumMap;
import java.util.Map;

/**
 * Factory que resolve a estratégia de pagamento a partir do PaymentMethod.
 * Centraliza a criação das estratégias e facilita a extensão (SOLID - O).
 */
public class PaymentStrategyFactory {

    private static final Map<PaymentMethod, PaymentStrategy> STRATEGIES = new EnumMap<>(PaymentMethod.class);

    static {
        STRATEGIES.put(PaymentMethod.CREDIT_CARD, new CreditCardPayment());
        STRATEGIES.put(PaymentMethod.PIX, new PixPayment());
        STRATEGIES.put(PaymentMethod.BOLETO, new BoletoPayment());
    }

    public static PaymentStrategy getStrategy(PaymentMethod method) {
        PaymentStrategy strategy = STRATEGIES.get(method);
        if (strategy == null) {
            throw new IllegalArgumentException("Método de pagamento não suportado: " + method);
        }
        return strategy;
    }
}
