package com.reto_reactivo.bootcamps.application.service;

import com.reto_reactivo.bootcamps.adapters.out.client.CapacidadClient;
import com.reto_reactivo.bootcamps.application.dto.BootcampDetailsDTO;
import com.reto_reactivo.bootcamps.domain.model.Bootcamp;
import com.reto_reactivo.bootcamps.domain.port.out.BootcampRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Comparator;

@Service
public class BootcampServiceImpl implements BootcampService {


    private final BootcampRepository bootcampRepository;
    private final CapacidadClient capacidadClient;

    public BootcampServiceImpl(BootcampRepository bootcampRepository, CapacidadClient capacidadClient) {
        this.bootcampRepository = bootcampRepository;
        this.capacidadClient = capacidadClient;
    }

    @Override
    public Mono<Bootcamp> registrarBootcamp(Bootcamp bootcamp) {
        // Regla de negocio: un bootcamp debe tener entre 1 y 4 capacidades asociadas
        if (bootcamp.getCapacidadIds() == null ||
                bootcamp.getCapacidadIds().size() < 1 ||
                bootcamp.getCapacidadIds().size() > 4) {
            return Mono.error(new IllegalArgumentException("Un bootcamp debe tener entre 1 y 4 capacidades asociadas"));
        }
        // Aquí se podría agregar lógica extra (por ejemplo, validar que cada ID de capacidad exista en el microservicio de capacidades)
        return bootcampRepository.save(bootcamp);
    }

    @Override
    public Flux<Bootcamp> listarBootcamps(int page, int size, String sortField, String sortDirection) {
        return bootcampRepository.findAll()
                .sort(getComparator(sortField, sortDirection))
                .skip((long) page * size)
                .take(size);
    }

    @Override
    public Mono<Bootcamp> findById(Long id) {
        return bootcampRepository.findById(id);
    }

    @Override
    public Flux<Bootcamp> findAll() {
        return bootcampRepository.findAll();
    }

    @Override
    public Mono<BootcampDetailsDTO> obtenerBootcampConDetalles(Long id) {
        return bootcampRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("No se encontró el bootcamp con ID: " + id)))
                .flatMap(bootcamp ->
                        Flux.fromIterable(bootcamp.getCapacidadIds())
                                .flatMap(capacidadId -> capacidadClient.getCapacidadById(capacidadId))
                                .collectList()
                                .map(capacidades -> new BootcampDetailsDTO(
                                        bootcamp.getId(),
                                        bootcamp.getNombre(),
                                        bootcamp.getDescripcion(),
                                        capacidades
                                ))
                );
    }

    @Override
    public Flux<BootcampDetailsDTO> listarBootcampsConDetalles(int page, int size, String sortField, String sortDirection) {
        return bootcampRepository.findAll()
                .sort(getComparator(sortField, sortDirection))
                .skip((long) page * size)
                .take(size)
                .flatMap(bootcamp ->
                        // Para cada bootcamp, se consultan las capacidades detalladas de forma concurrente.
                        Flux.fromIterable(bootcamp.getCapacidadIds())
                                .flatMap(capId -> capacidadClient.getCapacidadById(capId))
                                .collectList()
                                .map(capacidades -> new BootcampDetailsDTO(
                                        bootcamp.getId(),
                                        bootcamp.getNombre(),
                                        bootcamp.getDescripcion(),
                                        capacidades
                                ))
                );
    }

    private Comparator<Bootcamp> getComparator(String sortField, String sortDirection) {
        Comparator<Bootcamp> comparator;
        if ("cantidad".equalsIgnoreCase(sortField)) {
            comparator = Comparator.comparing(b -> b.getCapacidadIds().size());
        } else { // por defecto ordena por nombre
            comparator = Comparator.comparing(Bootcamp::getNombre, String.CASE_INSENSITIVE_ORDER);
        }
        if ("desc".equalsIgnoreCase(sortDirection)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
