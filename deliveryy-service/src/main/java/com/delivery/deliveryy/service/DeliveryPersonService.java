package com.delivery.deliveryy.service;

import com.delivery.deliveryy.model.DeliveryPerson;
import com.delivery.deliveryy.repository.DeliveryPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryPersonService {

    @Autowired
    private DeliveryPersonRepository repository;

    public List<DeliveryPerson> getAllDeliveryPersons() {
        return repository.findAll();
    }

    public DeliveryPerson getDeliveryPersonById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Delivery person not found"));
    }

    public DeliveryPerson createDeliveryPerson(DeliveryPerson person) {
        person.setIsAvailable(true); // Disponible par défaut
        return repository.save(person);
    }

    public DeliveryPerson updateDeliveryPerson(
            Long id, DeliveryPerson personDetails) {
        DeliveryPerson person = getDeliveryPersonById(id);

        person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        person.setPhone(personDetails.getPhone());
        person.setVehicleType(personDetails.getVehicleType());

        return repository.save(person);
    }

    public void deleteDeliveryPerson(Long id) {
        DeliveryPerson person = getDeliveryPersonById(id);
        repository.delete(person);
    }

    public List<DeliveryPerson> getAvailableDeliveryPersons() {
        return repository.findByIsAvailable(true);
    }
}