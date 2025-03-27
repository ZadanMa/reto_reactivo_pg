package com.reto_reactivo.capacidad.adapters.in.webflux;

import com.reto_reactivo.capacidad.application.dto.CapacidadDTO;
import com.reto_reactivo.capacidad.application.dto.TecnologiaDTO;
import com.reto_reactivo.capacidad.application.service.CapacidadService;
import com.reto_reactivo.capacidad.config.TestSecurityConfig;
import com.reto_reactivo.capacidad.config.XHeaderAuthenticationFilter;
import com.reto_reactivo.capacidad.domain.model.Capacidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@WebFluxTest(controllers = CapacidadController.class)
@ImportAutoConfiguration(exclude = {
        ReactiveSecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration.class,
        org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration.class
})
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Import(TestSecurityConfig.class)
class CapacidadControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CapacidadService capacidadService;

    @MockBean
    private XHeaderAuthenticationFilter xHeaderAuthenticationFilter;
    // Agrega esto para simular el comportamiento del filtro
    @BeforeEach
    void setUp() {
        Mockito.when(xHeaderAuthenticationFilter.filter(Mockito.any(), Mockito.any()))
                .thenAnswer(invocation -> {
                    ServerWebExchange exchange = invocation.getArgument(0);
                    WebFilterChain chain = invocation.getArgument(1);
                    return chain.filter(exchange);
                });
    }

    @Test
    void registrarCapacidad_givenValidCapacidad_whenPost_thenReturnsCreatedCapacidad() {
        // GIVEN
        Capacidad capacidad = new Capacidad();
        capacidad.setId(1L);
        capacidad.setNombre("Capacidad A");
        capacidad.setDescripcion("Descripción de Capacidad A");
        capacidad.setTecnologiaIds(List.of(1L, 2L, 3L));

        Mockito.when(capacidadService.registrarCapacidad(Mockito.any(Capacidad.class)))
                .thenReturn(Mono.just(capacidad));

        // WHEN & THEN
        webTestClient.post()
                .uri("/api/capacidades")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"nombre\":\"Capacidad A\", \"descripcion\":\"Descripción de Capacidad A\", \"tecnologiaIds\":[1,2,3]}") // Corregido
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("Capacidad A")
                .jsonPath("$.descripcion").isEqualTo("Descripción de Capacidad A");
    }
    @Test
    void listarCapacidades_givenValidParams_whenGet_thenReturnsFluxCapacidadDTO() {
        // GIVEN
        CapacidadDTO dto1 = new CapacidadDTO(
                1L,
                "Capacidad 1",
                "Descripción 1",
                List.of(new TecnologiaDTO(100L, "Java"), new TecnologiaDTO(101L, "Spring"), new TecnologiaDTO(102L, "Hibernate"))
        );

        CapacidadDTO dto2 = new CapacidadDTO(
                2L,
                "Capacidad 2",
                "Descripción 2",
                List.of(new TecnologiaDTO(200L, "Spring"), new TecnologiaDTO(201L, "Spring Boot"), new TecnologiaDTO(202L, "JPA"))
        );

        // Mock del servicio para devolver los DTOs
        Mockito.when(capacidadService.listarCapacidades(
                Mockito.eq(0), // page
                Mockito.eq(10), // size
                Mockito.eq("nombre"), // sortField
                Mockito.eq("asc") // sortDirection
        )).thenReturn(Flux.just(dto1, dto2));

        // WHEN & THEN
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/capacidades")
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .queryParam("sortField", "nombre")
                        .queryParam("sortDirection", "asc")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CapacidadDTO.class)
                .hasSize(2)
                .contains(dto1, dto2);
    }
}
