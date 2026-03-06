package com.eventmaster.ticketsales.domain;

import com.eventmaster.ticketsales.domain.model.PaymentMethod;
import com.eventmaster.ticketsales.domain.strategy.BoletoPayment;
import com.eventmaster.ticketsales.domain.strategy.CreditCardPayment;
import com.eventmaster.ticketsales.domain.strategy.PaymentStrategy;
import com.eventmaster.ticketsales.domain.strategy.PaymentStrategyFactory;
import com.eventmaster.ticketsales.domain.strategy.PixPayment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes unitários para o Strategy Pattern de pagamento.
 * Demonstra TDD: testes escritos para validar cada estratégia individualmente.
 */
class PaymentStrategyTest {

    @Test
    @DisplayName("Deve processar pagamento via Cartão de Crédito com sucesso")
    void shouldProcessCreditCardPayment() {
        // Arrange
        PaymentStrategy strategy = new CreditCardPayment();
        BigDecimal amount = new BigDecimal("150.00");

        // Act
        boolean result = strategy.pay(amount);

        // Assert
        assertTrue(result);
        assertEquals("CREDIT_CARD", strategy.getPaymentMethodName());
    }

    @Test
    @DisplayName("Deve processar pagamento via PIX com sucesso")
    void shouldProcessPixPayment() {
        // Arrange
        PaymentStrategy strategy = new PixPayment();
        BigDecimal amount = new BigDecimal("200.00");

        // Act
        boolean result = strategy.pay(amount);

        // Assert
        assertTrue(result);
        assertEquals("PIX", strategy.getPaymentMethodName());
    }

    @Test
    @DisplayName("Deve processar pagamento via Boleto com sucesso")
    void shouldProcessBoletoPayment() {
        // Arrange
        PaymentStrategy strategy = new BoletoPayment();
        BigDecimal amount = new BigDecimal("75.50");

        // Act
        boolean result = strategy.pay(amount);

        // Assert
        assertTrue(result);
        assertEquals("BOLETO", strategy.getPaymentMethodName());
    }

    @Test
    @DisplayName("Factory deve retornar a estratégia correta para CREDIT_CARD")
    void factoryShouldReturnCreditCardStrategy() {
        PaymentStrategy strategy = PaymentStrategyFactory.getStrategy(PaymentMethod.CREDIT_CARD);
        assertInstanceOf(CreditCardPayment.class, strategy);
    }

    @Test
    @DisplayName("Factory deve retornar a estratégia correta para PIX")
    void factoryShouldReturnPixStrategy() {
        PaymentStrategy strategy = PaymentStrategyFactory.getStrategy(PaymentMethod.PIX);
        assertInstanceOf(PixPayment.class, strategy);
    }

    @Test
    @DisplayName("Factory deve retornar a estratégia correta para BOLETO")
    void factoryShouldReturnBoletoStrategy() {
        PaymentStrategy strategy = PaymentStrategyFactory.getStrategy(PaymentMethod.BOLETO);
        assertInstanceOf(BoletoPayment.class, strategy);
    }
}
