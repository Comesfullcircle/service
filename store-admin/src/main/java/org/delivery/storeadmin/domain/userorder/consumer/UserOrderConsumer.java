package org.delivery.storeadmin.domain.userorder.consumer;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.userorder.business.UserOrderBusiness;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserOrderConsumer {

    private final UserOrderBusiness userOrderBusiness;

    @RabbitListener(queues = "delivery.queue")
    public void userOrderConsumer(
            UserOrderMessage userOrderMessage,
            Channel channel,
            Message message
    ) {
        try {
            log.info("message queue >> {}", userOrderMessage);
            userOrderBusiness.pushUserOrder(userOrderMessage);

            // 처리 성공 시 ack
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("메시지 처리 실패", e);

            //실패 시 nack (재처리)
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // requeue=true
            } catch (IOException ioException) {
                log.error("nack 실패", ioException);
            }
        }
    }
}