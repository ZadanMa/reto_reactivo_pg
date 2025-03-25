package com.reto_reactivo.capacidad.application.dto;

import java.util.List;

public class CapacidadDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<TecnologiaDTO> tecnologias;

    public CapacidadDTO() {}

    public CapacidadDTO(Long id, String nombre, String descripcion, List<TecnologiaDTO> tecnologias) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tecnologias = tecnologias;
    }

    // Getters & Setters
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
    public List<TecnologiaDTO> getTecnologias() {
        return tecnologias;
    }
    public void setTecnologias(List<TecnologiaDTO> tecnologias) {
        this.tecnologias = tecnologias;
    }
}