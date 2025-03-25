package com.reto_reactivo.capacidad.adapters.out.client;

import com.reto_reactivo.capacidad.application.dto.TecnologiaDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TecnologiaClient {

    private final WebClient webClient;

    public TecnologiaClient(WebClient.Builder webClientBuilder) {
        // "tecnologia" es el nombre con el que se registra el microservicio de tecnología en Eureka
        this.webClient = webClientBuilder.baseUrl("http://tecnologia").build();
    }

    public Mono<TecnologiaDTO> getTecnologiaById(Long id) {
        return webClient.get()
                .uri("/api/tecnologias/{id}", id)
                .retrieve()
                .bodyToMono(TecnologiaDTO.class);
    }
}
