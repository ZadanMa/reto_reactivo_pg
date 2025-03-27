package com.reto_reactivo.bootcampeventos.adapters.out.client;

import com.reto_reactivo.bootcampeventos.application.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UsuarioClient {


    private WebClient webClient;

    public UsuarioClient(WebClient.Builder webClientBuilder) {
        // Suponiendo que el microservicio de usuarios está registrado en Eureka con el nombre "users"
        this.webClient = webClientBuilder.baseUrl("http://apigateway").build();
    }

    public Mono<UserDTO> getUsuarioPorId(Long id) {
        return webClient.get()
                .uri("/admin/users/{id}", id)
                .retrieve()
                .bodyToMono(UserDTO.class);
    }
}
