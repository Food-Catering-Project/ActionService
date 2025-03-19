package com.example.ActionService.Service;

import com.example.ActionService.Entity.CateringOrder;
import com.example.ActionService.Entity.Menu;
import com.example.ActionService.Repository.CateringOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CateringOrderService {

    private final CateringOrderRepository cateringOrderRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


    public CateringOrderService(CateringOrderRepository cateringOrderRepository, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.cateringOrderRepository = cateringOrderRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public Map<String,Object> createOrder(CateringOrder order) throws Exception {
        CateringOrder cateringOrder = cateringOrderRepository.save(order);

        try {
            // Convert event to JSON string
            String cateringOrderjson = objectMapper.writeValueAsString(order);

            // Send JSON message to RabbitMQ
            rabbitTemplate.convertAndSend("CateringorderExchange", "Cateringorder.created", cateringOrderjson);
        } catch (Exception e) {
            throw  new Exception(e.getMessage());
        }

        return Map.of(
                "status", HttpStatus.CREATED,
                "message", "CateringOrder added successfully ",
                "data", cateringOrder
        );
    }


    public CateringOrder updateOrder(Long cateringOrderid, CateringOrder updatedOrder) {
        return cateringOrderRepository.findById(cateringOrderid)
                .map(order -> {
                    order.setNoOfPerson(updatedOrder.getNoOfPerson());
                    order.setEmail(updatedOrder.getEmail());
                    order.setDate(updatedOrder.getDate());
                    order.setPhoneNo(updatedOrder.getPhoneNo());
                    return cateringOrderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + cateringOrderid));
    }

    public Map<String, Object> deleteOrder(Long cateringOrderid) {
        CateringOrder cateringOrder = cateringOrderRepository.findById(cateringOrderid)
                .orElseThrow(() -> new RuntimeException("CateringOrder item not found"));

        cateringOrderRepository.delete(cateringOrder);

        return Map.of(
                "status", 200,
                "message", "CateringOrder item deleted successfully"
        );
    }




}