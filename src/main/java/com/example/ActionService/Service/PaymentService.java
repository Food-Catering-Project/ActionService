package com.example.ActionService.Service;

import com.example.ActionService.Entity.Order;
import com.example.ActionService.Entity.Payment;
import com.example.ActionService.Entity.User;
import com.example.ActionService.Repository.OrderRepository;
import com.example.ActionService.Repository.PaymentRepository;
import com.example.ActionService.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public Map<String, Object> createPayment(Long orderId, Long userId, Payment payment) {


            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Assign order and user
            payment.setOrder(order);
            payment.setUser(user);

            // Calculate and assign the amount based on order total
            payment.setAmount(order.getTotalAmount());
            payment.setDate(LocalDateTime.now());

            Payment savedPayment = paymentRepository.save(payment);

        return Map.of(
                "status", HttpStatus.CREATED.value(),
                "success","successfully made payment",
                "data",savedPayment
        );
    }

//    // Update Payment Status
    public Map<String, Object> updatePaymentStatus(Long paymentId, Payment.PaymentStatus paymentStatus) {


            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new RuntimeException("Payment not found"));

            payment.setPaymentStatus(paymentStatus);
            Payment updatedPayment = paymentRepository.save(payment);


        return Map.of(
                "status", HttpStatus.CREATED.value(),
                "success","successfully update paymentStatus",
                "data",updatedPayment
        );
    }

//    @Transactional
//    public Map<String, Object> updatePaymentStatus(Long paymentId, Payment.PaymentStatus status) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            Payment payment = paymentRepository.findById(paymentId)
//                    .orElseThrow(() -> new RuntimeException("Payment not found"));
//
//            payment.setStatus(status);
//            Payment updatedPayment = paymentRepository.save(payment);
//
//            // If payment is successful, update order payment status
////            if (status == Payment.PaymentStatus.SUCCESS) {
////                Order order = payment.getOrder();
////                User user = payment.getUser();
////
////                // Check if order belongs to the same user
////                if (order.getUser().getUserId().equals(user.getUserId())) {
////                    order.setPaymentStatus(Order.PaymentStatus.SUCCESS);
////                    orderRepository.save(order);
////                }
////            }
//
//
//            response.put("message", "Payment status updated successfully");
//            response.put("payment", updatedPayment);
//            return response;
//        } catch (Exception e) {
//            response.put("message", "Unable to update payment status");
//            response.put("error", e.getMessage());
//            return response;
//        }
//    }

    public Map<String, Object> deletePayment(Long paymentId) {

            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new RuntimeException("Payment not found"));

            paymentRepository.delete(payment);

        return Map.of(
                "status", HttpStatus.CREATED.value(),
                "success","successfully update paymentStatus"
        );
    }
}
