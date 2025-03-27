package com.reto_reactivo.bootcamps.application.service;

import com.reto_reactivo.bootcamps.adapters.out.client.CapacidadClient;
import com.reto_reactivo.bootcamps.application.dto.CapacidadFullDTO;
import com.reto_reactivo.bootcamps.application.dto.TecnologiaMinDTO;
import com.reto_reactivo.bootcamps.domain.model.Bootcamp;
import com.reto_reactivo.bootcamps.domain.port.out.BootcampRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;

public class BootcampServiceImplTest {

    @Mock
    private CapacidadClient capacidadClient;

    @Mock
    private BootcampRepository bootcampRepository;

    @InjectMocks
    private BootcampServiceImpl bootcampService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarBootcamp_conCantidadInvalida_debeLanzarError() {
        // Arrange
        Bootcamp bootcamp = new Bootcamp();
        bootcamp.setCapacidadIds(List.of()); // 0 capacidades (inválido)

        // Act & Assert
        StepVerifier.create(bootcampService.registrarBootcamp(bootcamp))
                .expectErrorMatches(throwable ->
                        throwable instanceof IllegalArgumentException &&
                                throwable.getMessage().equals("Un bootcamp debe tener entre 1 y 4 capacidades asociadas")
                )
                .verify();

        Mockito.verifyNoInteractions(bootcampRepository); // No debe guardar
    }
    @Test
    void registrarBootcamp_conCantidadValida_debeGuardar() {
        // Arrange
        Bootcamp bootcamp = new Bootcamp();
        bootcamp.setId(1L);
        bootcamp.setNombre("Java Bootcamp");
        bootcamp.setDescripcion("Bootcamp de Java");
        bootcamp.setCapacidadIds(List.of(100L, 200L)); // 2 capacidades (válido)

        Mockito.when(bootcampRepository.save(bootcamp))
                .thenReturn(Mono.just(bootcamp));

        // Act & Assert
        StepVerifier.create(bootcampService.registrarBootcamp(bootcamp))
                .expectNext(bootcamp)
                .verifyComplete();

        Mockito.verify(bootcampRepository, Mockito.times(1)).save(bootcamp);
    }
    @Test
    void listarBootcamps_ordenAscendentePorNombre() {
        // Arrange
        Bootcamp bootcamp1 = new Bootcamp(1L, "Angular Bootcamp", "...", List.of(100L));
        Bootcamp bootcamp2 = new Bootcamp(2L, "React Bootcamp", "...", List.of(200L));

        Mockito.when(bootcampRepository.findAll())
                .thenReturn(Flux.just(bootcamp2, bootcamp1)); // Orden inicial: React, Angular

        // Act & Assert
        StepVerifier.create(bootcampService.listarBootcamps(0, 10, "nombre", "asc"))
                .expectNext(bootcamp1, bootcamp2) // Verifica ordenamiento A-Z
                .verifyComplete();
    }
    @Test
    void listarBootcamps_ordenDescendentePorCantidad() {
        // Arrange
        Bootcamp bootcamp1 = new Bootcamp(1L, "Bootcamp 1", "...", List.of(100L, 200L)); // 2 capacidades
        Bootcamp bootcamp2 = new Bootcamp(2L, "Bootcamp 2", "...", List.of(300L)); // 1 capacidad

        Mockito.when(bootcampRepository.findAll())
                .thenReturn(Flux.just(bootcamp1, bootcamp2));

        // Act & Assert
        StepVerifier.create(bootcampService.listarBootcamps(0, 10, "cantidad", "desc"))
                .expectNext(bootcamp1, bootcamp2) // Verifica ordenamiento de mayor a menor
                .verifyComplete();
    }
    @Test
    void listarBootcamps_paginacion() {
        // Arrange
        Bootcamp bootcamp1 = new Bootcamp(1L, "Angular", "...", List.of(100L));
        Bootcamp bootcamp2 = new Bootcamp(2L, "React", "...", List.of(200L));
        Bootcamp bootcamp3 = new Bootcamp(3L, "Vue", "...", List.of(300L));

        Mockito.when(bootcampRepository.findAll())
                .thenReturn(Flux.just(bootcamp1, bootcamp2,bootcamp3));

        // Act & Assert
        StepVerifier.create(bootcampService.listarBootcamps(0, 2, "nombre", "asc")) // Página 0, tamaño 2
                .assertNext(bootcamp -> {
                    assertThat(bootcamp.getId()).isEqualTo(1L); // Angular (primero por orden alfabético)
                    assertThat(bootcamp.getNombre()).isEqualTo("Angular");
                })
                .assertNext(bootcamp -> {
                    assertThat(bootcamp.getId()).isEqualTo(2L); // React (segundo por orden alfabético)
                    assertThat(bootcamp.getNombre()).isEqualTo("React");
                })
                .verifyComplete();
    }

    @Test
    void obtenerBootcampConDetalles_debeIncluirTecnologias() {
        // Arrange
        Bootcamp bootcamp = new Bootcamp(1L, "Java Bootcamp", "...", List.of(100L, 200L));

        // Crear TecnologiaMinDTO para las tecnologías
        TecnologiaMinDTO tech1 = new TecnologiaMinDTO(1L, "Java");
        TecnologiaMinDTO tech2 = new TecnologiaMinDTO(2L, "Spring");
        TecnologiaMinDTO tech3 = new TecnologiaMinDTO(3L, "Hibernate");

        // Crear capacidades con listas de TecnologiaMinDTO
        CapacidadFullDTO capacidad1 = new CapacidadFullDTO(
                100L,
                "Spring",
                List.of(tech1, tech2) // Lista de TecnologiaMinDTO
        );
        CapacidadFullDTO capacidad2 = new CapacidadFullDTO(
                200L,
                "Hibernate",
                List.of(tech3)
        );

        Mockito.when(bootcampRepository.findById(1L))
                .thenReturn(Mono.just(bootcamp));
        Mockito.when(capacidadClient.getCapacidadById(100L))
                .thenReturn(Mono.just(capacidad1));
        Mockito.when(capacidadClient.getCapacidadById(200L))
                .thenReturn(Mono.just(capacidad2));

        // Act & Assert
        StepVerifier.create(bootcampService.obtenerBootcampConDetalles(1L))
                .assertNext(dto -> {
                    assertThat(dto.getCapacidades().size()).isEqualTo(2);

                    // Validar tecnologías de la primera capacidad
                    assertThat(dto.getCapacidades().get(0).getTecnologias())
                            .extracting(TecnologiaMinDTO::getId)
                            .containsExactlyInAnyOrder(1L, 2L);

                    // Validar tecnologías de la segunda capacidad
                    assertThat(dto.getCapacidades().get(1).getTecnologias())
                            .extracting(TecnologiaMinDTO::getId)
                            .containsExactly(3L);
                })
                .verifyComplete();
    }
}
