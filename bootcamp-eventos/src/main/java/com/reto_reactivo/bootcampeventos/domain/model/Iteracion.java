package com.reto_reactivo.bootcampeventos.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;
import java.util.List;

@Table("iteraciones")
public class Iteracion {

    @Id
    private Long id;

    @NotNull(message = "El bootcampId es obligatorio")
    private Long bootcampId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "El cupo máximo es obligatorio")
    private Integer cupoMaximo;

    // Al menos se debe asignar un tutor
    @Size(min = 1, message = "Debe asignarse al menos un tutor")
    private List<Long> tutorIds;

    // Los participantes se asignarán más adelante, puede venir vacío
    private List<Long> participanteIds;

    // Opcional: se puede mantener un listado de entregables asociados (para otros procesos)
    private List<String> entregables;

    public Iteracion() {}

    public Iteracion(Long id, Long bootcampId, LocalDate fecha, Integer cupoMaximo,
                     List<Long> tutorIds, List<Long> participanteIds, List<String> entregables) {
        this.id = id;
        this.bootcampId = bootcampId;
        this.fecha = fecha;
        this.cupoMaximo = cupoMaximo;
        this.tutorIds = tutorIds;
        this.participanteIds = participanteIds;
        this.entregables = entregables;
    }

    // Getters y setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBootcampId() { return bootcampId; }
    public void setBootcampId(Long bootcampId) { this.bootcampId = bootcampId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Integer getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(Integer cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public List<Long> getTutorIds() { return tutorIds; }
    public void setTutorIds(List<Long> tutorIds) { this.tutorIds = tutorIds; }

    public List<Long> getParticipanteIds() { return participanteIds; }
    public void setParticipanteIds(List<Long> participanteIds) { this.participanteIds = participanteIds; }

    public List<String> getEntregables() { return entregables; }
    public void setEntregables(List<String> entregables) { this.entregables = entregables; }
}
