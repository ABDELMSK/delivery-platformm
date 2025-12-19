package com.delivery.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Client ID is required")
    @Positive(message = "Client ID must be positive")
    @Column(nullable = false)
    private Long clientId;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be positive")
    @DecimalMin(value = "1.0", message = "Total amount must be at least 1.0")
    @Column(nullable = false)
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @NotBlank(message = "Delivery address is required")
    @Size(min = 10, max = 200, message = "Address must be between 10 and 200 characters")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String deliveryAddress;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;
}