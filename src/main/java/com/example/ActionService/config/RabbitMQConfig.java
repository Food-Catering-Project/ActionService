package com.example.ActionService.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // Queue, Exchange, Routing Key names
    public static final String Queue_Name = "orderQueue";
    public static final String Exchange_Name = "orderExchange";
    public static final String Routing_Key = "order.created";

    @Bean
    public Queue queue() {
        return new Queue(Queue_Name);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Exchange_Name);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Routing_Key);
    }





}
