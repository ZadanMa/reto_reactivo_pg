package com.reto_reactivo.apigateway.adapters.out.repository;

import com.reto_reactivo.apigateway.domain.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByUsername(String username);
}
