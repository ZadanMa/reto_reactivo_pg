package com.reto_reactivo.apigateway.adapters.out.repository;

import com.reto_reactivo.apigateway.domain.model.User;
import com.reto_reactivo.apigateway.domain.port.out.UserRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserReactiveRepository reactiveRepository;

    public UserRepositoryImpl(UserReactiveRepository reactiveRepository) {
        this.reactiveRepository = reactiveRepository;
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return reactiveRepository.findByUsername(username);
    }

    @Override
    public Mono<User> save(User user) {
        return reactiveRepository.save(user);
    }
    public Flux<User> findAll() {
        return reactiveRepository.findAll();
    }
}
