package com.example.ActionService.Service;

import com.example.ActionService.Dto.OrderEvent;
import com.example.ActionService.Entity.Order;
import com.example.ActionService.Entity.Restaurant;
import com.example.ActionService.Entity.User;
import com.example.ActionService.Repository.OrderRepository;
import com.example.ActionService.Repository.RestaurantRepository;
import com.example.ActionService.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


    public OrderService(OrderRepository orderRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

//    public Map<String, Object> createOrder(Long userId, Long restaurantId, Order order) {
//        Map<String, Object> response = new HashMap<>();
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Restaurant restaurant = restaurantRepository.findById(restaurantId)
//                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
//
//        order.setUser(user);
//        order.setRestaurant(restaurant);
//        order.setStatus(Order.Status.PLACED);
//        order.setPaymentStatus(Order.PaymentStatus.PENDING);
//        order.setDate(LocalDateTime.now());
//
//        Order savedOrder = orderRepository.save(order);
//
//        response.put("status", 201);
//        response.put("message", "Order created successfully!");
//        response.put("data", savedOrder);
//
//        return response;
//    }

    public Map<String, Object> createOrder(Long userId, Long restaurantId, Order order) {
        Map<String, Object> response = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();  // Create an ObjectMapper


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // Send event to RabbitMQ

        OrderEvent event = new OrderEvent(
                savedOrder.getOrderId(),
                user.getEmail(),
                restaurant.getEmail(),
                "Your order has been placed successfully!",
                savedOrder.getTotalAmount(),
                user.getAddress()
        );

        try {
            // Convert event to JSON string
            String eventJson = objectMapper.writeValueAsString(event);

            // Send JSON message to RabbitMQ
            rabbitTemplate.convertAndSend("orderExchange", "order.created", eventJson);
        } catch (Exception e) {
            response.put("status", 500);
            response.put("message", "Error while sending event to RabbitMQ: " + e.getMessage());
            return response;
        }
        response.put("status", 201);
        response.put("message", "Order created successfully!");
        response.put("data", savedOrder);

        return response;
    }

    public Map<String, Object> updateOrderStatus(Long orderId, Order.Status status) {
        Map<String, Object> response = new HashMap<>();

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        existingOrder.setStatus(status);
        Order updatedOrder = orderRepository.save(existingOrder);

        response.put("status", 200);
        response.put("message", "Order status updated successfully!");
        response.put("data", updatedOrder);

        return response;
    }

    public Map<String, Object> deleteOrder(Long orderId) {
        Map<String, Object> response = new HashMap<>();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        orderRepository.delete(order);

        response.put("status", 200);
        response.put("message", "Order deleted successfully!");

        return response;
    }
}

