package com.eventmaster.ticketsales.infrastructure.persistence;

import com.eventmaster.ticketsales.domain.model.Order;
import com.eventmaster.ticketsales.domain.repository.OrderRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapter que implementa o port OrderRepository usando Spring Data JPA.
 */
@Repository
public class JpaOrderRepository implements OrderRepository {

    private final SpringDataOrderRepository springDataRepository;

    public JpaOrderRepository(SpringDataOrderRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = OrderJpaEntity.fromDomain(order);
        OrderJpaEntity saved = springDataRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return springDataRepository.findById(id).map(OrderJpaEntity::toDomain);
    }
}
