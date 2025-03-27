package com.reto_reactivo.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("tecnologia_route", r -> r.path("/api/tecnologias/**")
                        .uri("lb://tecnologia"))
                .route("capacidad_route", r -> r.path("/api/capacidades/**")
                        .uri("lb://capacidad"))
                .route("bootcamps_route", r -> r.path("/api/bootcamps/**")
                        .uri("lb://bootcamp"))
                // Microservicio de Bootcamp-Eventos (Iteraciones, Entregables, Submissions)
                .route("iteraciones_route", r -> r.path("/api/iteraciones/**")
                        .uri("lb://bootcampeventos"))
                .route("entregables_route", r -> r.path("/api/entregables/**")
                        .uri("lb://bootcampeventos"))
                .route("submissions_route", r -> r.path("/api/submissions/**")
                        .uri("lb://bootcampeventos"))
                // Ruta opcional para endpoints generales de bootcamp-eventos
                .route("bootcamp_eventos_route", r -> r.path("/api/bootcamp-eventos/**")
                        .uri("lb://bootcampeventos"))
                .build();
    }
}
