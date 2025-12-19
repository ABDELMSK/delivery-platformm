package com.delivery.order.repository;

import com.delivery.order.model.Order;
import com.delivery.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClientId(Long clientId);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByClientIdAndStatus(Long clientId, OrderStatus status);
}