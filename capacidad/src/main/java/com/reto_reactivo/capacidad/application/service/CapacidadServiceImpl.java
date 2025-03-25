// src/main/java/com/reto_reactivo/capacidad/application/service/CapacidadServiceImpl.java
package com.reto_reactivo.capacidad.application.service;

import com.reto_reactivo.capacidad.adapters.out.client.TecnologiaClient;
import com.reto_reactivo.capacidad.application.dto.CapacidadDTO;
import com.reto_reactivo.capacidad.application.dto.TecnologiaDTO;
import com.reto_reactivo.capacidad.domain.exception.InvalidTecnologiasException;
import com.reto_reactivo.capacidad.domain.model.Capacidad;
import com.reto_reactivo.capacidad.domain.port.out.CapacidadRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CapacidadServiceImpl implements CapacidadService {

    private final CapacidadRepository capacidadRepository;
    private final TecnologiaClient tecnologiaClient;

    public CapacidadServiceImpl(CapacidadRepository capacidadRepository, TecnologiaClient tecnologiaClient) {
        this.capacidadRepository = capacidadRepository;
        this.tecnologiaClient = tecnologiaClient;
    }

    @Override
    public Mono<Capacidad> registrarCapacidad(Capacidad capacidad) {
        // Verifica la cantidad de IDs en la capacidad (la validación básica ya se hace con @Size en la entidad)
        if (capacidad.getTecnologiaIds() == null || capacidad.getTecnologiaIds().size() < 3 || capacidad.getTecnologiaIds().size() > 20) {
            return Mono.error(new InvalidTecnologiasException("La capacidad debe tener entre 3 y 20 tecnologias asociadas"));
        }

        // Verifica que cada ID de tecnología exista en el microservicio de tecnología
        return Flux.fromIterable(capacidad.getTecnologiaIds())
                .flatMap(techId -> tecnologiaClient.getTecnologiaById(techId)
                        .switchIfEmpty(Mono.error(new InvalidTecnologiasException("La tecnología con ID " + techId + " no existe"))))
                .collectList() // Si se validan todas las tecnologías, continúa
                .flatMap(valid -> capacidadRepository.save(capacidad));
    }

    @Override
    public Mono<Capacidad> agregarTecnologias(Long capacidadId, Set<Long> nuevasTecnologias) {
        return capacidadRepository.findById(capacidadId)
                .switchIfEmpty(Mono.error(new RuntimeException("Capacidad no encontrada")))
                .flatMap(capacidad -> {
                    // Combina las tecnologías existentes con las nuevas
                    Set<Long> tecnologiasActualizadas = capacidad.getTecnologiaIds();
                    tecnologiasActualizadas.addAll(nuevasTecnologias);

                    // Valida que el total no exceda 20
                    if (tecnologiasActualizadas.size() > 20) {
                        return Mono.error(new InvalidTecnologiasException("La capacidad no puede tener más de 20 tecnologías"));
                    }
                    capacidad.setTecnologiaIds(tecnologiasActualizadas);

                    // Validar la existencia de cada tecnología nueva
                    return Flux.fromIterable(nuevasTecnologias)
                            .flatMap(techId -> tecnologiaClient.getTecnologiaById(techId)
                                    .switchIfEmpty(Mono.error(new InvalidTecnologiasException("La tecnología con ID " + techId + " no existe"))))
                            .collectList()
                            .flatMap(valid -> capacidadRepository.save(capacidad));
                });
    }

    @Override
    public Flux<CapacidadDTO> listarCapacidades(int page, int size, String sortField, String sortDirection) {
        // Obtener todas las capacidades y ordenarlas en memoria
        return capacidadRepository.findAll()
                .sort(getComparator(sortField, sortDirection))
                .skip((long) page * size)
                .take(size)
                .flatMap(capacidad -> {
                    // Para cada capacidad, obtener detalles de las tecnologías a partir de sus IDs
                    List<Long> techIds = capacidad.getTecnologiaIds().stream().collect(Collectors.toList());
                    return Flux.fromIterable(techIds)
                            .flatMap(techId -> tecnologiaClient.getTecnologiaById(techId)
                                    .map(tech -> new TecnologiaDTO(tech.getId(), tech.getNombre()))) // Solo id y nombre
                            .collectList()
                            .map(tecnologias -> new CapacidadDTO(
                                    capacidad.getId(),
                                    capacidad.getNombre(),
                                    capacidad.getDescripcion(),
                                    tecnologias
                            ));
                });
    }

    private Comparator<? super Capacidad> getComparator(String sortField, String sortDirection) {
        Comparator<Capacidad> comparator;
        if ("cantidad".equalsIgnoreCase(sortField)) {
            comparator = Comparator.comparing(cap -> cap.getTecnologiaIds().size());
        } else { // "nombre" o por defecto
            comparator = Comparator.comparing(Capacidad::getNombre, String.CASE_INSENSITIVE_ORDER);
        }
        if ("desc".equalsIgnoreCase(sortDirection)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

}
