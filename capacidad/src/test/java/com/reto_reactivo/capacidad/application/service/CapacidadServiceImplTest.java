package com.reto_reactivo.capacidad.application.service;

import com.reto_reactivo.capacidad.adapters.out.client.TecnologiaClient;
import com.reto_reactivo.capacidad.application.dto.TecnologiaDTO;
import com.reto_reactivo.capacidad.domain.exception.InvalidTecnologiasException;
import com.reto_reactivo.capacidad.domain.model.Capacidad;
import com.reto_reactivo.capacidad.domain.port.out.CapacidadRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Set;
import java.util.List;

class CapacidadServiceImplTest {

    @Mock
    private TecnologiaClient tecnologiaClient;

    @Mock
    private CapacidadRepository capacidadRepository;

    @InjectMocks
    private CapacidadServiceImpl capacidadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarCapacidad_TecnologiasNulas_ThrowsException() {
        // Arrange
        Capacidad capacidad = new Capacidad();
        capacidad.setTecnologiaIds(null); // Tecnologías nulas

        // Act & Assert
        StepVerifier.create(capacidadService.registrarCapacidad(capacidad))
                .expectErrorMatches(throwable -> throwable instanceof InvalidTecnologiasException &&
                        throwable.getMessage().equals("La capacidad debe tener entre 3 y 20 tecnologias asociadas"))
                .verify();

        Mockito.verifyNoInteractions(tecnologiaClient, capacidadRepository);
    }

    @Test
    void registrarCapacidad_MenosDe3Tecnologias_ThrowsException() {
        // Arrange
        Capacidad capacidad = new Capacidad();
        capacidad.setTecnologiaIds(List.of(1L, 2L)); // Solo 2 IDs

        // Act & Assert
        StepVerifier.create(capacidadService.registrarCapacidad(capacidad))
                .expectErrorMatches(throwable -> throwable instanceof InvalidTecnologiasException &&
                        throwable.getMessage().equals("La capacidad debe tener entre 3 y 20 tecnologias asociadas"))
                .verify();

        Mockito.verifyNoInteractions(tecnologiaClient, capacidadRepository);
    }

    @Test
    void registrarCapacidad_TecnologiasDuplicadas_ThrowsException() {
        // Arrange
        Capacidad capacidad = new Capacidad();
        capacidad.setTecnologiaIds(List.of(1L, 1L, 2L)); // Lista con duplicados (asumiendo que el campo es List)

        // Act & Assert
        StepVerifier.create(capacidadService.registrarCapacidad(capacidad))
                .expectErrorMatches(throwable -> throwable instanceof InvalidTecnologiasException &&
                        throwable.getMessage().equals("No se permiten tecnologías repetidas"))
                .verify();

        Mockito.verifyNoInteractions(tecnologiaClient, capacidadRepository);
    }

    @Test
    void registrarCapacidad_TecnologiaNoExiste_ThrowsException() {
        // Arrange
        Capacidad capacidad = new Capacidad();
        capacidad.setTecnologiaIds(List.of(1L, 2L, 3L));

        // Simula que la tecnología 3 no existe
        Mockito.when(tecnologiaClient.getTecnologiaById(1L)).thenReturn(Mono.just(new TecnologiaDTO()));
        Mockito.when(tecnologiaClient.getTecnologiaById(2L)).thenReturn(Mono.just(new TecnologiaDTO()));
        Mockito.when(tecnologiaClient.getTecnologiaById(3L)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(capacidadService.registrarCapacidad(capacidad))
                .expectErrorMatches(throwable -> throwable instanceof InvalidTecnologiasException &&
                        throwable.getMessage().equals("La tecnología con ID 3 no existe"))
                .verify();

        Mockito.verify(tecnologiaClient, Mockito.times(3)).getTecnologiaById(Mockito.anyLong());
        Mockito.verifyNoInteractions(capacidadRepository);
    }

    @Test
    void registrarCapacidad_ValidTecnologias_ReturnsSavedCapacidad() {
        // Arrange
        Capacidad capacidad = new Capacidad();
        capacidad.setTecnologiaIds(List.of(1L, 2L, 3L));

        // Simula tecnologías existentes
        Mockito.when(tecnologiaClient.getTecnologiaById(Mockito.anyLong()))
                .thenReturn(Mono.just(new TecnologiaDTO()));

        // Simula guardado exitoso
        Mockito.when(capacidadRepository.save(capacidad))
                .thenReturn(Mono.just(capacidad));

        // Act & Assert
        StepVerifier.create(capacidadService.registrarCapacidad(capacidad))
                .expectNext(capacidad)
                .verifyComplete();

        // Verifica interacciones
        Mockito.verify(tecnologiaClient, Mockito.times(3)).getTecnologiaById(Mockito.anyLong());
        Mockito.verify(capacidadRepository, Mockito.times(1)).save(capacidad);
    }
}