package com.reto_reactivo.bootcampeventos.adapters.out.repository.entregable;

import com.reto_reactivo.bootcampeventos.domain.model.Entregable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EntregableReactiveRepository extends ReactiveCrudRepository<Entregable, Long> {
    // Puedes agregar consultas personalizadas si es necesario.
}
