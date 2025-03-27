package com.reto_reactivo.tecnologia.adapters.in.webflux;


import com.reto_reactivo.tecnologia.application.service.TecnologiaService;
import com.reto_reactivo.tecnologia.config.TestSecurityConfig;
import com.reto_reactivo.tecnologia.config.XHeaderAuthenticationFilter;
import com.reto_reactivo.tecnologia.domain.exception.DuplicateTechnologyNameException;
import com.reto_reactivo.tecnologia.domain.model.Tecnologia;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WebFluxTest(controllers = TecnologiaController.class)
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
class TecnologiaControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TecnologiaService tecnologiaService;

    @MockBean
    private XHeaderAuthenticationFilter xHeaderAuthenticationFilter;

    @BeforeEach
    void setUp() {
        Mockito.when(xHeaderAuthenticationFilter.filter(Mockito.any(), Mockito.any()))
                .thenAnswer(invocation -> {
                    ServerWebExchange exchange = invocation.getArgument(0);
                    WebFilterChain chain = invocation.getArgument(1);
                    return chain.filter(exchange); // Simula que el filtro no hace nada
                });
    }

    @Test
    void registrarTecnologia_givenValidTecnologia_whenPost_thenReturnsCreatedTecnologia() {
        // GIVEN
        Tecnologia tecnologia = new Tecnologia(1L, "Java", "Lenguaje de programación");
        Mockito.when(tecnologiaService.registrarTecnologia(Mockito.any(Tecnologia.class)))
                .thenReturn(Mono.just(tecnologia));

        // WHEN & THEN
        webTestClient.post()
                .uri("/api/tecnologias")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"nombre\":\"Java\", \"descripcion\":\"Lenguaje de programación\"}")
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("Java")
                .jsonPath("$.descripcion").isEqualTo("Lenguaje de programación");
    }

    @Test
    void registrarTecnologia_givenDuplicateName_whenPost_thenReturnsConflict() {
        // GIVEN
        Mockito.when(tecnologiaService.registrarTecnologia(Mockito.any(Tecnologia.class)))
                .thenReturn(Mono.error(new DuplicateTechnologyNameException("Nombre duplicado")));

        // WHEN & THEN
        webTestClient.post()
                .uri("/api/tecnologias")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"nombre\":\"Java\", \"descripcion\":\"Lenguaje de programación\"}")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void listarTecnologias_givenValidParams_whenGet_thenReturnsFluxTecnologia() {
        // GIVEN
        Tecnologia tech1 = new Tecnologia(1L, "Java", "Lenguaje de programación");
        Tecnologia tech2 = new Tecnologia(2L, "Spring", "Framework Java");

        Mockito.when(tecnologiaService.listarTecnologias(
                Mockito.eq(0),
                Mockito.eq(10),
                Mockito.eq("asc")
        )).thenReturn(Flux.just(tech1, tech2));

        // WHEN & THEN
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/tecnologias")
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .queryParam("sortDirection", "asc")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Tecnologia.class)
                .hasSize(2)
                .value(response -> {
                    assertThat(response.get(0).getId()).isEqualTo(1L);
                    assertThat(response.get(0).getNombre()).isEqualTo("Java");
                    assertThat(response.get(1).getId()).isEqualTo(2L);
                    assertThat(response.get(1).getNombre()).isEqualTo("Spring");
                });
    }

    @Test
    void listarTecnologias_givenDescSort_whenGet_thenReturnsSortedDesc() {
        // GIVEN
        Tecnologia tech1 = new Tecnologia(2L, "Spring", "Framework Java");
        Tecnologia tech2 = new Tecnologia(1L, "Java", "Lenguaje de programación");

        Mockito.when(tecnologiaService.listarTecnologias(
                Mockito.eq(0),
                Mockito.eq(10),
                Mockito.eq("desc")
        )).thenReturn(Flux.just(tech1, tech2));

        // WHEN & THEN
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/tecnologias")
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .queryParam("sortDirection", "desc")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Tecnologia.class)
                .hasSize(2)
                .value(response -> {
                    assertThat(response.get(0).getNombre()).isEqualTo("Spring");
                    assertThat(response.get(1).getNombre()).isEqualTo("Java");
                });
    }
}
