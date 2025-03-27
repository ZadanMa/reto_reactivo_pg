package com.reto_reactivo.apigateway.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Order(-2) // Prioridad alta para que se ejecute antes del manejador por defecto
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalErrorHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof ServerWebInputException && ex.getMessage().contains("No request body")) {
            logger.warn("Se ha capturado y disimilado el error 'No request body': {}", ex.getMessage());
            // Ajusta el status o la respuesta que desees enviar; por ejemplo, 200 OK sin body
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            return exchange.getResponse().setComplete();
        }
        // Para otros errores, permite que se manejen de forma normal (o puedes customizarlos también)
        return Mono.error(ex);
    }
}
