package com.example.ActionService.Controller;

import com.example.ActionService.Entity.Menu;
import com.example.ActionService.Entity.Order;
import com.example.ActionService.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/action/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/addOrder/{userId}/{restaurantId}")
    public ResponseEntity<Map<String, Object>> createOrder(@PathVariable Long userId, @PathVariable Long restaurantId, @RequestBody Order order) {
        try{
            Map<String, Object> response = orderService.createOrder(userId, restaurantId, order);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to addOrder", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

        @PutMapping("/update/{orderId}/status")
   public ResponseEntity<Map<String, Object>> updateOrderStatus(@PathVariable Long orderId, @RequestParam Order.Status status) {
        try {
            Map<String, Object> response = orderService.updateOrderStatus(orderId, status);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to update order", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Long orderId)  {
        try {
            Map<String, Object> response = orderService.deleteOrder(orderId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to delete", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}

