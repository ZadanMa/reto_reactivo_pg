package com.reto_reactivo.apigateway.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("users")
public class User {

    @Id
    private Long id;
    private String username;
    private String email;
    private String password;

    // Para simplicidad, guardamos un solo rol.
    // Si deseas múltiples roles, podrías usar un Set<Role> o JSON
    private String role;

    public User() {}

    public User(Long id, String username, String password, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

}
