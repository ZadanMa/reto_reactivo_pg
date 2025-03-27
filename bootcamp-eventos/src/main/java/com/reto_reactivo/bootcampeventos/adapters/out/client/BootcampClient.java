package com.reto_reactivo.bootcampeventos.adapters.out.client;

import com.reto_reactivo.bootcampeventos.application.dto.BootcampDTO;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class BootcampClient {

    private WebClient webClient;

    public BootcampClient(@LoadBalanced WebClient.Builder webClientBuilder) {
        // Usa el nombre exacto registrado en Eureka
        this.webClient = webClientBuilder.baseUrl("lb://bootcamp").build();
    }

    public Mono<BootcampDTO> obtenerBootcampPorId(Long id) {
        return webClient.get()
                .uri("/api/bootcamps/{id}", id)
                .retrieve()
                .bodyToMono(BootcampDTO.class);
    }
}