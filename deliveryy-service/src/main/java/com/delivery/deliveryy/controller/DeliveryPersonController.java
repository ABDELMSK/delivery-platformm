package com.delivery.deliveryy.controller;

import com.delivery.deliveryy.model.DeliveryPerson;
import com.delivery.deliveryy.service.DeliveryPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-persons")
@CrossOrigin(origins = "*")
public class DeliveryPersonController {

    @Autowired
    private DeliveryPersonService service;

    @GetMapping
    public ResponseEntity<List<DeliveryPerson>> getAllDeliveryPersons() {
        return ResponseEntity.ok(service.getAllDeliveryPersons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPerson> getDeliveryPersonById(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getDeliveryPersonById(id));
    }

    @PostMapping
    public ResponseEntity<DeliveryPerson> createDeliveryPerson(
            @RequestBody DeliveryPerson person) {
        DeliveryPerson created = service.createDeliveryPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryPerson> updateDeliveryPerson(
            @PathVariable Long id,
            @RequestBody DeliveryPerson person) {
        return ResponseEntity.ok(
                service.updateDeliveryPerson(id, person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryPerson(
            @PathVariable Long id) {
        service.deleteDeliveryPerson(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<DeliveryPerson>>
    getAvailableDeliveryPersons() {
        return ResponseEntity.ok(
                service.getAvailableDeliveryPersons());
    }
}