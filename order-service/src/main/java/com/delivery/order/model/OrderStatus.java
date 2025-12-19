package com.delivery.order.model;

public enum OrderStatus {
    PENDING,      // En attente de confirmation
    CONFIRMED,    // Confirmée
    IN_DELIVERY,  // En cours de livraison
    DELIVERED,    // Livrée
    CANCELLED     // Annulée
}