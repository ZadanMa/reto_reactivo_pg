package com.reto_reactivo.bootcampeventos.application.service.entregable;

import com.reto_reactivo.bootcampeventos.domain.model.Entregable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EntregableService {
    Mono<Entregable> crearEntregable(Entregable entregable);
    Mono<Entregable> obtenerEntregable(Long id);
    Flux<Entregable> listarEntregablesPorIteracion(Long iteracionId);
}
