package com.eventmaster.ticketsales.application.usecase;

import com.eventmaster.ticketsales.application.dto.PurchaseRequest;
import com.eventmaster.ticketsales.application.dto.PurchaseResponse;
import com.eventmaster.ticketsales.domain.model.Event;
import com.eventmaster.ticketsales.domain.model.Order;
import com.eventmaster.ticketsales.domain.repository.EventRepository;
import com.eventmaster.ticketsales.domain.repository.OrderRepository;
import com.eventmaster.ticketsales.domain.strategy.PaymentStrategy;
import com.eventmaster.ticketsales.domain.strategy.PaymentStrategyFactory;

import java.math.BigDecimal;

/**
 * Caso de uso: Compra de Ingressos.
 *
 * Orquestra a lógica de negócio:
 * 1. Busca o evento
 * 2. Verifica disponibilidade de ingressos
 * 3. Processa o pagamento usando Strategy Pattern
 * 4. Cria e confirma o pedido
 *
 * Princípio: Responsabilidade Única (SOLID - S) — esta classe tem uma única razão para mudar.
 */
public class PurchaseTicketUseCase {

    private final EventRepository eventRepository;
    private final OrderRepository orderRepository;

    public PurchaseTicketUseCase(EventRepository eventRepository, OrderRepository orderRepository) {
        this.eventRepository = eventRepository;
        this.orderRepository = orderRepository;
    }

    public PurchaseResponse execute(PurchaseRequest request) {
        // 1. Buscar evento
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado: " + request.getEventId()));

        // 2. Reservar ingressos (valida disponibilidade internamente)
        event.reserveTickets(request.getQuantity());

        // 3. Calcular valor total
        BigDecimal totalAmount = event.getTicketPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        // 4. Processar pagamento via Strategy Pattern
        PaymentStrategy paymentStrategy = PaymentStrategyFactory.getStrategy(request.getPaymentMethod());
        boolean paymentApproved = paymentStrategy.pay(totalAmount);

        if (!paymentApproved) {
            throw new IllegalStateException("Pagamento recusado.");
        }

        // 5. Criar e confirmar pedido
        Order order = new Order(
                event.getId(),
                request.getCustomerEmail(),
                request.getQuantity(),
                totalAmount,
                request.getPaymentMethod()
        );
        order.confirm();

        // 6. Persistir
        eventRepository.save(event);
        orderRepository.save(order);

        // 7. Retornar resposta
        return new PurchaseResponse(
                order.getId(),
                event.getId(),
                order.getQuantity(),
                order.getTotalAmount(),
                paymentStrategy.getPaymentMethodName(),
                order.getStatus().name()
        );
    }
}
