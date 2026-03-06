package com.eventmaster.ticketsales.bdd;

import com.eventmaster.ticketsales.application.dto.PurchaseRequest;
import com.eventmaster.ticketsales.application.dto.PurchaseResponse;
import com.eventmaster.ticketsales.application.usecase.PurchaseTicketUseCase;
import com.eventmaster.ticketsales.domain.model.Event;
import com.eventmaster.ticketsales.domain.model.PaymentMethod;
import com.eventmaster.ticketsales.domain.repository.EventRepository;
import com.eventmaster.ticketsales.domain.repository.OrderRepository;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Step definitions para o cenário BDD de compra de ingressos.
 * Usa repositórios in-memory simples para isolar o teste da infraestrutura.
 */
public class PurchaseTicketSteps {

    private final Map<UUID, Event> eventStore = new HashMap<>();

    private final EventRepository eventRepository = new EventRepository() {
        @Override
        public Optional<Event> findById(UUID id) {
            return Optional.ofNullable(eventStore.get(id));
        }

        @Override
        public Event save(Event event) {
            eventStore.put(event.getId(), event);
            return event;
        }
    };

    private final OrderRepository orderRepository = new OrderRepository() {
        @Override
        public com.eventmaster.ticketsales.domain.model.Order save(com.eventmaster.ticketsales.domain.model.Order order) {
            return order;
        }

        @Override
        public Optional<com.eventmaster.ticketsales.domain.model.Order> findById(UUID id) {
            return Optional.empty();
        }
    };

    private UUID eventId;
    private PurchaseResponse response;

    @Dado("que existe um evento {string} com {int} ingressos disponíveis ao preço de R$ {double}")
    public void eventExists(String name, int tickets, double price) {
        eventId = UUID.randomUUID();
        Event event = new Event(
                eventId, name, "Evento de teste",
                LocalDateTime.of(2026, 12, 1, 20, 0),
                "Local Teste", tickets, BigDecimal.valueOf(price)
        );
        eventStore.put(eventId, event);
    }

    @Quando("o cliente {string} compra {int} ingressos via {string}")
    public void clientPurchases(String email, int quantity, String method) {
        PurchaseTicketUseCase useCase = new PurchaseTicketUseCase(eventRepository, orderRepository);
        PurchaseRequest request = new PurchaseRequest(eventId, email, quantity, PaymentMethod.valueOf(method));
        response = useCase.execute(request);
    }

    @Então("o pedido deve ser confirmado com valor total de R$ {double}")
    public void orderConfirmed(double expectedTotal) {
        assertNotNull(response);
        assertEquals("CONFIRMED", response.getStatus());
        assertEquals(0, BigDecimal.valueOf(expectedTotal).compareTo(response.getTotalAmount()));
    }

    @E("o evento deve ter {int} ingressos disponíveis")
    public void eventHasTickets(int expectedAvailable) {
        Event event = eventStore.get(eventId);
        assertEquals(expectedAvailable, event.getAvailableTickets());
    }
}
