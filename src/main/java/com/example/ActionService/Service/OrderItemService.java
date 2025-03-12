package com.example.ActionService.Service;

import com.example.ActionService.Entity.Menu;
import com.example.ActionService.Entity.Order;
import com.example.ActionService.Entity.OrderItem;
import com.example.ActionService.Repository.MenuRepository;
import com.example.ActionService.Repository.OrderItemRepository;
import com.example.ActionService.Repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderRepository orderRepository, MenuRepository menuRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
    }

    public Map<String, Object> addOrderItem(Long orderId, Long menuId, OrderItem orderItem) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            Menu menu = menuRepository.findById(menuId)
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));

            orderItem.setOrder(order);
            orderItem.setMenu(menu);
            orderItem.setPrice(menu.getPrice() * orderItem.getQuantity());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);

            return Map.of(
                    "status", HttpStatus.CREATED.value(),
                    "success","successfully added orderItem",
                    "data",savedOrderItem
            );

    }

    // Update Order Item Quantity
    public Map<String, Object> updateOrderItemQuantity(Long orderItemId, int quantity) {

            OrderItem orderItem = orderItemRepository.findById(orderItemId)
                    .orElseThrow(() -> new RuntimeException("Order Item not found"));

            orderItem.setQuantity(quantity);
            orderItem.setPrice(orderItem.getMenu().getPrice() * quantity);

            OrderItem updatedOrderItem = orderItemRepository.save(orderItem);

        return Map.of(
                "status", HttpStatus.CREATED.value(),
                "success","successfully update orderItemQuantity",
                "data",updatedOrderItem
        );
    }


    public Map<String, Object> deleteOrderItem(Long orderItemId) {

            OrderItem orderItem = orderItemRepository.findById(orderItemId)
                    .orElseThrow(() -> new RuntimeException("Order Item not found"));

            orderItemRepository.delete(orderItem);
            return Map.of(
                    "status", HttpStatus.CREATED.value(),
                    "success","successfully update orderItemQuantity"
            );

    }
}
