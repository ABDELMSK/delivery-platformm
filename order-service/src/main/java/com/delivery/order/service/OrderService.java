package com.delivery.order.service;

import com.delivery.order.dto.ClientDTO;
import com.delivery.order.feign.ClientFeignClient;
import com.delivery.order.model.Order;
import com.delivery.order.model.OrderStatus;
import com.delivery.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientFeignClient clientFeignClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    // NOUVELLE MÉTHODE : Créer commande avec vérification du client
    public Order createOrderWithClientValidation(Order order) {
        // Vérifier que le client existe via FeignClient
        try {
            ClientDTO client = clientFeignClient.getClientById(order.getClientId());
            System.out.println("✅ Client trouvé : " + client.getFirstName() + " " + client.getLastName());
        } catch (Exception e) {
            throw new RuntimeException("Client not found with id: " + order.getClientId());
        }

        // Si le client existe, créer la commande
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = getOrderById(id);
        order.setDescription(orderDetails.getDescription());
        order.setTotalAmount(orderDetails.getTotalAmount());
        order.setDeliveryAddress(orderDetails.getDeliveryAddress());
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);

        if (status == OrderStatus.DELIVERED) {
            order.setDeliveryDate(LocalDateTime.now());
        }

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}