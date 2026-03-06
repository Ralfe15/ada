package com.eventmaster.ticketsales.infrastructure.persistence;

import com.eventmaster.ticketsales.domain.model.Event;
import com.eventmaster.ticketsales.domain.repository.EventRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapter que implementa o port EventRepository usando Spring Data JPA.
 * Faz a conversão entre o modelo de domínio (Event) e a entidade JPA (EventJpaEntity).
 */
@Repository
public class JpaEventRepository implements EventRepository {

    private final SpringDataEventRepository springDataRepository;

    public JpaEventRepository(SpringDataEventRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Optional<Event> findById(UUID id) {
        return springDataRepository.findById(id).map(EventJpaEntity::toDomain);
    }

    @Override
    public Event save(Event event) {
        EventJpaEntity entity = EventJpaEntity.fromDomain(event);
        EventJpaEntity saved = springDataRepository.save(entity);
        return saved.toDomain();
    }
}
