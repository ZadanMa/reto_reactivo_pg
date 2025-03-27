package com.reto_reactivo.bootcampeventos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         XHeaderAuthenticationFilter xHeaderAuthenticationFilter) {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        // Confiamos en gateway, no exigimos credenciales
                        .anyExchange().permitAll()
                )
                // Insertamos nuestro filtro en la cadena de seguridad
                .addFilterAt(xHeaderAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}