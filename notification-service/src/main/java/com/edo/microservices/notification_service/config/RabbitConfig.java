package com.edo.microservices.notification_service.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration
public class RabbitConfig {


    @Value("${spring.rabbitmq.queue}")
    String queue_name;

    @Value("${spring.rabbitmq.exchange}")
    String exchange_name;

    @Value("${spring.rabbitmq.routing}")
    String routing_queue;


    @Bean
    public Queue myQueue() {
        return new Queue(queue_name, true);
    }


    @Bean
    public DirectExchange myExchange() {
        return new DirectExchange(exchange_name);
    }


    @Bean
    public Binding binding(Queue myQueue, DirectExchange myExchange) {
        return BindingBuilder.bind(myQueue).to(myExchange).with(routing_queue);
    }


    @Bean
    public RabbitAdmin rabbitAdmin(RabbitTemplate rabbitTemplate) {
        return new RabbitAdmin(rabbitTemplate);
    }
}
