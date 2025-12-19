package com.delivery.deliveryy.service;

import com.delivery.deliveryy.model.*;
import com.delivery.deliveryy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    public Delivery assignDelivery(Long orderId, Long deliveryPersonId) {
        // Vérifier que le livreur existe et est disponible
        DeliveryPerson person = deliveryPersonRepository
                .findById(deliveryPersonId)
                .orElseThrow(() -> new RuntimeException(
                        "Delivery person not found"));

        if (!person.getIsAvailable()) {
            throw new RuntimeException(
                    "Delivery person is not available");
        }

        // Créer la livraison
        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setDeliveryPersonId(deliveryPersonId);
        delivery.setStatus(DeliveryStatus.ASSIGNED);

        // Marquer le livreur comme non disponible
        person.setIsAvailable(false);
        deliveryPersonRepository.save(person);

        return deliveryRepository.save(delivery);
    }

    public Delivery updateDeliveryStatus(Long id, DeliveryStatus status) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Delivery not found"));

        delivery.setStatus(status);

        // Si livraison terminée ou échouée, libérer le livreur
        if (status == DeliveryStatus.COMPLETED ||
                status == DeliveryStatus.FAILED) {
            delivery.setCompletedAt(LocalDateTime.now());

            DeliveryPerson person = deliveryPersonRepository
                    .findById(delivery.getDeliveryPersonId())
                    .orElseThrow();
            person.setIsAvailable(true);
            deliveryPersonRepository.save(person);
        }

        return deliveryRepository.save(delivery);
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Delivery not found"));
    }

    public List<Delivery> getDeliveriesByPerson(Long personId) {
        return deliveryRepository.findByDeliveryPersonId(personId);
    }
}