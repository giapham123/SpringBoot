package com.example.demo.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "exampleQueueWithDLX"; // Tên hàng đợi mới
    public static final String DLX_QUEUE_NAME = "exampleDLQ"; // Tên hàng đợi DLQ
    public static final String EXCHANGE_NAME = "exampleExchange";
    public static final String DLX_EXCHANGE_NAME = "exampleDLX"; // Tên DLX
    public static final String ROUTING_KEY = "exampleRoutingKey";
    public static final int MAX_QUEUE_LENGTH = 4; // Số lượng tối đa message trong hàng đợi

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }

    @Bean
    public Queue queue() {
        // Cấu hình hàng đợi mới với DLX và x-max-length
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE_NAME) // Chỉ định DLX
                .withArgument("x-dead-letter-routing-key", "exampleDLRoutingKey") // Routing key cho DLX
                .withArgument("x-max-length", MAX_QUEUE_LENGTH) // Số lượng message tối đa
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLX_QUEUE_NAME, true); // Hàng đợi Dead Letter
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_EXCHANGE_NAME); // DLX
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("exampleDLRoutingKey"); // Binding cho DLQ
    }
}
