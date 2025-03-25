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
                        .uri("lb://bootcamps"))
                .build();
    }
}
