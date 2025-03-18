package com.example.ActionService.Controller;

import com.example.ActionService.Entity.Payment;
import com.example.ActionService.Service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/action/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/add/{orderId}/{userId}")
    public ResponseEntity<Map<String, Object>> createPayment(@PathVariable Long orderId, @PathVariable Long userId, @RequestBody Payment payment) {
        try {
            Map<String, Object> response = paymentService.createPayment(orderId, userId, payment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to mak payment", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Update Payment Status
    @PutMapping("/update/{paymentId}/paymentStatus")
    public ResponseEntity<Map<String, Object>> updatePaymentStatus(@PathVariable Long paymentId, @RequestParam Payment.PaymentStatus paymentStatus) {
        try {
            Map<String, Object> response = paymentService.updatePaymentStatus(paymentId, paymentStatus);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to update payment", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<Map<String, Object>> deletePayment(@PathVariable Long paymentId) {
        try{
        Map<String, Object> response = paymentService.deletePayment(paymentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to delete payment", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
