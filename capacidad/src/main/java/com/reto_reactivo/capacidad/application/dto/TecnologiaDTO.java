package com.reto_reactivo.capacidad.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TecnologiaDTO {
    private Long id;
    private String nombre;
    private String descripcion;

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TecnologiaDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    // Constructor por defecto (necesario para la deserialización)
    public TecnologiaDTO() {}
}