package com.reto_reactivo.apigateway.adapters.in.web;

public class CreateUserRequest {

    private String username;
    private String password;
    private String email;
    private String role;

    // Constructor por defecto (requerido para la deserialización)
    public CreateUserRequest() {
    }

    // Constructor con parámetros (opcional)
    public CreateUserRequest(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
