package com.reto_reactivo.bootcamps.adapters.out.client;

import com.reto_reactivo.bootcamps.application.dto.CapacidadFullDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CapacidadClient {

    private final WebClient webClient;

    public CapacidadClient(WebClient.Builder webClientBuilder) {
        // "capacidad" es el nombre con el que se registra el microservicio de capacidades en Eureka.
        this.webClient = webClientBuilder.baseUrl("http://capacidad").build();
    }

    public Mono<CapacidadFullDTO> getCapacidadById(Long id) {
        return webClient.get()
                .uri("/api/capacidades/{id}", id)
                .retrieve()
                .bodyToMono(CapacidadFullDTO.class);
    }
}
