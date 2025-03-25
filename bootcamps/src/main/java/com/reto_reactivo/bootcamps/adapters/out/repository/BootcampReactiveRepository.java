package com.reto_reactivo.bootcamps.adapters.out.repository;

import com.reto_reactivo.bootcamps.domain.model.Bootcamp;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BootcampReactiveRepository extends ReactiveCrudRepository<Bootcamp, Long> {
    // findAll(), findById(), save() están disponibles por defecto
}
