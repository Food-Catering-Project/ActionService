package com.example.ActionService.Controller;

import com.example.ActionService.Entity.CateringOrder;
import com.example.ActionService.Service.CateringOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/action/api/cateringorders")
public class CateringOrderController {

    private final CateringOrderService cateringOrderService;

    public CateringOrderController(CateringOrderService cateringOrderService) {
        this.cateringOrderService = cateringOrderService;
    }

    @PostMapping("/addCateringOrder")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody CateringOrder order) {
        try {
            Map<String, Object> response = cateringOrderService.createOrder(order);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to add", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateOrder(@PathVariable Long cateringOrderid, @RequestBody CateringOrder order) {
        try {
            CateringOrder updatedOrder = cateringOrderService.updateOrder(cateringOrderid, order);
            return ResponseEntity.ok(Map.of("message", "Order updated successfully", "data", updatedOrder));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Long cateringOrderid) {
        cateringOrderService.deleteOrder(cateringOrderid);
        return ResponseEntity.ok(Map.of("message", "Order deleted successfully"));
    }
}
