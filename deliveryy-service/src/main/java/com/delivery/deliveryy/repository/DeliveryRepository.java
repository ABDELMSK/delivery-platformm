package com.delivery.deliveryy.repository;

import com.delivery.deliveryy.model.Delivery;
import com.delivery.deliveryy.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository
        extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByOrderId(Long orderId);

    List<Delivery> findByDeliveryPersonId(Long deliveryPersonId);

    List<Delivery> findByStatus(DeliveryStatus status);
}