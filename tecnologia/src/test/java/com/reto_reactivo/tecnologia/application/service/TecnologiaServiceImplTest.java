package com.reto_reactivo.tecnologia.application.service;

import com.reto_reactivo.tecnologia.domain.exception.DuplicateTechnologyNameException;
import com.reto_reactivo.tecnologia.domain.model.Tecnologia;
import com.reto_reactivo.tecnologia.domain.port.out.TecnologiaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import org.springframework.data.domain.Sort;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
class TecnologiaServiceImplTest {

    @Mock
    private TecnologiaRepository tecnologiaRepository;

    @InjectMocks
    private TecnologiaServiceImpl service;

    private Tecnologia tecnologiaValida;
    private Tecnologia tecnologiaNombreRepetido;
    private Tecnologia tecnologiaDescripcionInvalida;

    @BeforeEach
    void setUp() {
        tecnologiaValida = new Tecnologia(null, "Java", "Lenguaje backend");
        tecnologiaNombreRepetido = new Tecnologia(null, "Java", "Otra descripción");
        tecnologiaDescripcionInvalida = new Tecnologia(null, "Python", "");
    }

    @Test
    void registrarTecnologia_NombreRepetido_ThrowsException() {
        // Arrange (Preparación)
        when(tecnologiaRepository.existsByNombre("Java")).thenReturn(Mono.just(true));

        // Act & Assert (Ejecución y Verificación)
        StepVerifier.create(service.registrarTecnologia(tecnologiaValida))
                .expectError(DuplicateTechnologyNameException.class)
                .verify();
    }


    @Test
    void registrarTecnologia_DescripcionInvalida_ThrowsException() {
        StepVerifier.create(service.registrarTecnologia(tecnologiaDescripcionInvalida))
                .expectErrorMatches(throwable ->
                        throwable instanceof IllegalArgumentException &&
                                throwable.getMessage().contains("La descripción es obligatoria"))
                .verify();
    }
    @Test
    void listarTecnologias_PaginacionYOrdenamiento() {
        // Arrange (Preparación)
        Tecnologia t1 = new Tecnologia(1L, "Angular", "Framework frontend");
        Tecnologia t2 = new Tecnologia(2L, "React", "Biblioteca frontend");
        Sort sort = Sort.by("nombre").ascending();
        when(tecnologiaRepository.findAllTecnologias(sort))
                .thenReturn(Flux.just(t1, t2)); //

        // Act (Ejecución)
        var resultado = service.listarTecnologias(0, 1, "asc");

        // Assert (Verificación)
        StepVerifier.create(resultado)
                .assertNext(tecnologia -> {
                    assertEquals("Angular", tecnologia.getNombre());
                    assertEquals("Framework frontend", tecnologia.getDescripcion());
                })
                .verifyComplete();
    }
}