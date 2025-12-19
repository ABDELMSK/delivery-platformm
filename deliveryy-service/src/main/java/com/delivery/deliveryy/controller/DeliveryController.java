package com.delivery.deliveryy.controller;

import com.delivery.deliveryy.model.*;
import com.delivery.deliveryy.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@CrossOrigin(origins = "*")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDeliveries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(
            @PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }

    @PostMapping
    public ResponseEntity<Delivery> assignDelivery(
            @RequestParam Long orderId,
            @RequestParam Long deliveryPersonId) {
        Delivery delivery = deliveryService.assignDelivery(
                orderId, deliveryPersonId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(delivery);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Delivery> updateDeliveryStatus(
            @PathVariable Long id,
            @RequestParam DeliveryStatus status) {
        return ResponseEntity.ok(
                deliveryService.updateDeliveryStatus(id, status));
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<List<Delivery>> getDeliveriesByPerson(
            @PathVariable Long personId) {
        return ResponseEntity.ok(
                deliveryService.getDeliveriesByPerson(personId));
    }
}