package com.delivery.apigw.exceptionhandler

import com.delivery.apigw.common.Log
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class GlobalExceptionHandler(
    private val objectMapper: ObjectMapper
) : ErrorWebExceptionHandler {

    data class ErrorResponse(val error: String)

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val response = exchange.response
        if (response.isCommitted) {
            return Mono.error(ex)
        }

        response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
        response.headers.contentType = MediaType.APPLICATION_JSON

        val errorResponse = ErrorResponse(error = ex.localizedMessage)
        val errorResponseBytes = objectMapper.writeValueAsBytes(errorResponse)

        val buffer = response.bufferFactory().wrap(errorResponseBytes)

        return response.writeWith(Mono.just(buffer))
    }
}