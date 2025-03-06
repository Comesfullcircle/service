package org.delivery.api.config.rabbitmq

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMqConfig(@Qualifier("objectMapper") private val objectMapper: ObjectMapper) {

    @Bean
    fun directExchange(): DirectExchange{
        return DirectExchange("delivery.exchange")
    }

    @Bean
    fun queue(): Queue {
        return Queue("delivery.queue")
    }

    @Bean
    fun binding(
        directExchange: DirectExchange,
        queue: Queue
    ): Binding {
        return BindingBuilder
            .bind(queue)
            .to(directExchange)
            .with("delivery.key")

    }

    // end queue 설정

    @Bean
    fun rabbitTemplate(
        connectionFactory: ConnectionFactory,
        messageconverter: MessageConverter
    ) : RabbitTemplate{
        val rabbitTemplate : RabbitTemplate = RabbitTemplate().apply {
            setConnectionFactory(connectionFactory)
            setMessageConverter(messageconverter)
        }

        return rabbitTemplate
    }

    @Bean
    fun messageConverter(
        objectMapper: ObjectMapper
    ): MessageConverter{
        return Jackson2JsonMessageConverter(objectMapper)
    }
}