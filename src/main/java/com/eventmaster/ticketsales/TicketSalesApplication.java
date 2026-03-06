package com.eventmaster.ticketsales;

import com.eventmaster.ticketsales.application.usecase.PurchaseTicketUseCase;
import com.eventmaster.ticketsales.domain.repository.EventRepository;
import com.eventmaster.ticketsales.domain.repository.OrderRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketSalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketSalesApplication.class, args);
    }

    /**
     * Registra o use case como bean do Spring.
     * O use case não é um componente Spring por design (Clean Architecture),
     * então registramos manualmente via @Bean.
     */
    @Bean
    public PurchaseTicketUseCase purchaseTicketUseCase(EventRepository eventRepository,
                                                       OrderRepository orderRepository) {
        return new PurchaseTicketUseCase(eventRepository, orderRepository);
    }
}
