package com.reto_reactivo.bootcampeventos.adapters.out.repository.iteracion;

import com.reto_reactivo.bootcampeventos.domain.model.Iteracion;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IteracionReactiveRepository extends ReactiveCrudRepository<Iteracion, Long> {
}