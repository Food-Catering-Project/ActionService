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
public class CateringConfigRabbitMQ {


    public static final String CateringQueue_Name = "CateringorderQueue";
    public static final String CateringExchange_Name = "CateringorderExchange";
    public static final String CateringRouting_Key = "Cateringorder.created";

    @Bean
    public Queue cateringqueue() {
        return new Queue(CateringQueue_Name);
    }

    @Bean
    public TopicExchange cateringexchange() {
        return new TopicExchange(CateringExchange_Name);
    }

    @Bean
    public Binding cateringbinding(Queue cateringqueue, TopicExchange cateringexchange) {
        return BindingBuilder.bind(cateringqueue).to(cateringexchange).with(CateringRouting_Key);
    }


}
