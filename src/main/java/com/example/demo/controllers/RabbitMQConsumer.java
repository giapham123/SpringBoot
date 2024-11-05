package com.example.demo.controllers;


import com.example.demo.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;


@Service
public class RabbitMQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String messageContent, Message message, Channel channel) {
        try {
            // Xử lý message
            logger.info("Processing message: {}", messageContent);

            // Giả định xử lý thành công
            processMessage(messageContent);

            // Gửi xác nhận nếu xử lý thành công
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            logger.info("Message acknowledged: {}", messageContent);

        } catch (Exception e) {
            logger.error("Failed to process message: {}. Error: {}", messageContent, e.getMessage());

            // Tùy chọn: Không xác nhận message và có thể chuyển sang hàng đợi Dead Letter
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } catch (Exception nackException) {
                logger.error("Failed to nack message: {}", nackException.getMessage());
            }
        }
    }

    private void processMessage(String messageContent) throws Exception {
        // Logic xử lý message
        // Ném ra exception nếu xử lý thất bại
        if (messageContent.contains("fail")) {
            throw new Exception("Simulated processing failure");
        }
        // Xử lý thành công
        logger.info("Message processed successfully: {}", messageContent);
    }

}