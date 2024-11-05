package com.example.demo.controllers;

import com.example.demo.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DeadLetterQueueConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DeadLetterQueueConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.DLX_QUEUE_NAME)
    public void receiveDLQMessage(String messageContent, Message message, Channel channel) {
        // Xử lý message từ hàng đợi Dead Letter
        logger.warn("Received message from DLQ: {}", message);

        // Ví dụ: Bạn có thể thực hiện các hành động xử lý bổ sung, ví dụ ghi log lại, gửi thông báo cảnh báo, hoặc lưu trữ vào cơ sở dữ liệu.

        // Nếu muốn thử xử lý lại hoặc có logic bổ sung cho message, bạn có thể thực hiện ở đây
        // Ví dụ:
        try {
            // Thực hiện một số xử lý với message
            if (retryProcessing(messageContent)) {
                logger.info("Message processed successfully after retry: {}", messageContent);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                logger.error("Message could not be processed from DLQ: {}", messageContent);
            }
        } catch (Exception e) {
            logger.error("Exception while processing message from DLQ: {}", e.getMessage());
        }
    }

    // Ví dụ một phương thức để thử lại xử lý message
    private boolean retryProcessing(String messageContent) {
        // Logic để thử xử lý lại message
        // Trả về true nếu xử lý thành công, false nếu thất bại
        return !messageContent.contains("fail"); // Ví dụ, xử lý lại sẽ thành công nếu message không chứa "fail"
    }
}
