package com.reto_reactivo.apigateway.adapters.in.web;

import com.reto_reactivo.apigateway.application.service.UserService;
import com.reto_reactivo.apigateway.domain.model.User;
import jakarta.ws.rs.GET;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public Mono<User> createUser(@RequestBody CreateUserRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        newUser.setRole(request.getRole());
        return userService.createUser(newUser);
    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/login/hola")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<String> hola() {
        return Mono.just("Hola, mundo!");
    }

}
