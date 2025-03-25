package com.reto_reactivo.bootcamps.application.service;

import com.reto_reactivo.bootcamps.application.dto.BootcampDetailsDTO;
import com.reto_reactivo.bootcamps.domain.model.Bootcamp;
import com.reto_reactivo.bootcamps.domain.port.in.RegistrarBootcampUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BootcampService {

    // Método para registrar un bootcamp, ya validando que tenga entre 1 y 4 capacidades.
    Mono<Bootcamp> registrarBootcamp(Bootcamp bootcamp);

    // Método para listar bootcamps con paginación y ordenación (por nombre o cantidad de capacidades)
    Flux<Bootcamp> listarBootcamps(int page, int size, String sortField, String sortDirection);

    // Método para encontrar un bootcamp por su ID
    Mono<Bootcamp> findById(Long id);

    // Método para listar todos los bootcamps sin paginación
    Flux<Bootcamp> findAll();

    // Método para listar bootcamp con detalles de forma individual
    Mono<BootcampDetailsDTO> obtenerBootcampConDetalles(Long id);

    // Nuevo método: Listar bootcamps con detalle de capacidades y tecnologías
    Flux<BootcampDetailsDTO> listarBootcampsConDetalles(int page, int size, String sortField, String sortDirection);
}