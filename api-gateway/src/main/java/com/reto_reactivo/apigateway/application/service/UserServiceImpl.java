package com.reto_reactivo.apigateway.application.service;

import com.reto_reactivo.apigateway.domain.model.User;
import com.reto_reactivo.apigateway.domain.port.out.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        // Inicializamos BCryptPasswordEncoder; también podrías inyectarlo si lo declaras en SecurityConfig
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Mono<User> createUser(User user) {
        // Encriptamos la contraseña
        String hashed = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashed);
        return userRepository.save(user);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }
}
