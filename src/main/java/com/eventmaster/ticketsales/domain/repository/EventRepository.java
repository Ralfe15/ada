package com.eventmaster.ticketsales.domain.repository;

import com.eventmaster.ticketsales.domain.model.Event;

import java.util.Optional;
import java.util.UUID;

/**
 * Port (interface) do repositório de eventos.
 * Definida na camada de domínio, implementada na camada de infraestrutura.
 * Princípio: Inversão de Dependência (SOLID - D).
 */
public interface EventRepository {

    Optional<Event> findById(UUID id);

    Event save(Event event);
}
