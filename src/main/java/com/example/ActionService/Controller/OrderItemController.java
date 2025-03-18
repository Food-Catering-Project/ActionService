package com.example.ActionService.Controller;

import com.example.ActionService.Entity.OrderItem;
import com.example.ActionService.Service.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/action/api/orderItems")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/add/{orderId}/{menuId}")
    public ResponseEntity<Map<String, Object>> addOrderItem(@PathVariable Long orderId, @PathVariable Long menuId, @RequestBody OrderItem orderItem) {
        try {
            Map<String, Object> response = orderItemService.addOrderItem(orderId, menuId, orderItem);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to add orderItem", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Update Order Item Quantity
    @PutMapping("/update/{orderItemId}/quantity")
    public ResponseEntity<Map<String, Object>> updateOrderItemQuantity(@PathVariable Long orderItemId, @RequestParam int quantity) {
        try {
            Map<String, Object> response = orderItemService.updateOrderItemQuantity(orderItemId, quantity);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to update orderItem", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{orderItemId}")
    public ResponseEntity<Map<String, Object>> deleteOrderItem(@PathVariable Long orderItemId) {
        try{
        Map<String, Object> response = orderItemService.deleteOrderItem(orderItemId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to delete orderItem", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
