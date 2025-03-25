package com.reto_reactivo.bootcamps.domain.port.in;

import com.reto_reactivo.bootcamps.domain.model.Bootcamp;
import reactor.core.publisher.Mono;

public interface RegistrarBootcampUseCase {
    Mono<Bootcamp> registrarBootcamp(Bootcamp bootcamp);
}
