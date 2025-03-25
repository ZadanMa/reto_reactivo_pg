package com.reto_reactivo.apigateway.application.service;

import com.reto_reactivo.apigateway.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findByUsername(String username);
    Mono<User> createUser(User user);
    Flux<User> findAll();
}
