package org.delivery.storeadmin.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMqConfig {

    // Queue 설정 (durable = true)
    @Bean
    public Queue deliveryQueue() {
        return new Queue("delivery.queue", true); // durable=true → 서버 재시작 시에도 유지
    }

    // JSON 직렬화 메시지 컨버터
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper){
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    // RabbitTemplate도 필요하면 여기에 추가 가능 (발송용)
    //수동 ACK을 위한 리스너 컨테이너 팩토리
        @Bean
        public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
        ) {
            SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
            factory.setConnectionFactory(connectionFactory);
            factory.setMessageConverter(messageConverter);
            factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 수동 ack 설정
            return factory;
        }
}
