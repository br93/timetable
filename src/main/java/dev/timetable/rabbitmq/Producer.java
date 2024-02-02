package dev.timetable.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import dev.timetable.config.RabbitMQConfig.RabbitMQProperties;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Producer {

    private final AmqpTemplate rabbitTemplate;
    private final RabbitMQProperties properties;

    public void send(Message message) {
        this.rabbitTemplate.convertAndSend(properties.getExchange(), properties.getRoutingKey(), message);
    }

}
