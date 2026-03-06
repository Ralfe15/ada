package com.eventmaster.ticketsales.domain.repository;

import com.eventmaster.ticketsales.domain.model.Order;

import java.util.Optional;
import java.util.UUID;

/**
 * Port (interface) do repositório de pedidos.
 * Definida na camada de domínio, implementada na camada de infraestrutura.
 * Princípio: Inversão de Dependência (SOLID - D).
 */
public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(UUID id);
}
