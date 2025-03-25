package com.reto_reactivo.apigateway.domain.port.out;

import com.reto_reactivo.apigateway.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> findByUsername(String username);
    Mono<User> save(User user);

    Flux<User> findAll();
}
