package com.eventmaster.ticketsales.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Spring Data JPA repository para OrderJpaEntity.
 */
public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, UUID> {
}
