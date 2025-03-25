package com.reto_reactivo.apigateway.config;

import com.reto_reactivo.apigateway.application.service.UserService;
import com.reto_reactivo.apigateway.domain.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserService userService) {
        return username -> userService.findByUsername(username)
                .map(this::mapToUserDetails)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Usuario no encontrado: " + username)));
    }

    private UserDetails mapToUserDetails(com.reto_reactivo.apigateway.domain.model.User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().startsWith("ROLE_") ? user.getRole().substring(5) : user.getRole()) // Ajusta el rol
                .build();
    }

    @Bean
    public UserDetailsRepositoryReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService uds, PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authManager = new UserDetailsRepositoryReactiveAuthenticationManager(uds);
        authManager.setPasswordEncoder(passwordEncoder); // CONFIGURA BCrypt
        return authManager;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         UserDetailsRepositoryReactiveAuthenticationManager authManager,
                                                         AuthHeadersFilter authHeadersFilter) {
        return http
                .csrf(csrf -> csrf.disable())
                .authenticationManager(authManager)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/admin/users").permitAll()
                        .pathMatchers("/admin").hasRole("TUTOR")
                        .pathMatchers("/admin/login/hola").hasRole("ADMIN")
                        .anyExchange().hasRole("ADMIN")
                )
                // Inserta el filtro de headers después de la autenticación
                .addFilterAfter(authHeadersFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(withDefaults -> {})
                .build();
    }
}
