package com.reto_reactivo.bootcamps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        // Sin baseUrl fija, ya que la resolveremos dinámicamente usando Eureka
        return WebClient.builder();
    }
}