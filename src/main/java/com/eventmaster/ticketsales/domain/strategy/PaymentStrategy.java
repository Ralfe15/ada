package com.eventmaster.ticketsales.domain.strategy;

import java.math.BigDecimal;

/**
 * Strategy Pattern (GoF) - Interface para estratégias de pagamento.
 * Cada método de pagamento implementa esta interface com sua própria lógica.
 * Princípio: Aberto/Fechado (SOLID - O) — para adicionar novos métodos de pagamento,
 * basta criar uma nova implementação sem modificar o código existente.
 */
public interface PaymentStrategy {

    /**
     * Processa o pagamento e retorna true se aprovado.
     */
    boolean pay(BigDecimal amount);

    /**
     * Nome descritivo da estratégia de pagamento.
     */
    String getPaymentMethodName();
}
