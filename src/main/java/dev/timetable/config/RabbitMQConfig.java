package dev.timetable.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    @ConfigurationProperties(prefix = "rabbitmq")
    RabbitMQProperties rabbitMQProperties() {
        return new RabbitMQProperties();
    }

    @Setter
    @Getter
    public static class RabbitMQProperties {
        private String queueName;
        private String exchange;
        private String routingKey;
    }

    @Bean
    Queue queue(RabbitMQProperties properties) {
        return new Queue(properties.getQueueName(), false);
    }

    @Bean
    TopicExchange exchange(RabbitMQProperties properties) {
        return new TopicExchange(properties.getExchange(), false, true);
    }

    @Bean
    Binding binding(RabbitMQProperties properties, Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(properties.getRoutingKey());
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        return new RabbitTemplate(connectionFactory);
    }

    
}
