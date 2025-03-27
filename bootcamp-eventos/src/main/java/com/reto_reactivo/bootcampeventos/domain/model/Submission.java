package com.reto_reactivo.bootcampeventos.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("submissions")
public class Submission {

    @Id
    private Long id;

    // Referencia al entregable al que corresponde esta entrega
    private Long entregableId;

    // Identificador del participante que realiza la entrega
    private Long participanteId;

    // Contenido de la entrega: puede ser texto, enlace, etc.
    private String contenido;

    // Fecha y hora en que se envió la entrega
    private LocalDateTime fechaEnvio;

    // Feedback del tutor, que podrá actualizarse posteriormente
    private String feedback;

    public Submission() {}

    public Submission(Long id, Long entregableId, Long participanteId, String contenido, LocalDateTime fechaEnvio, String feedback) {
        this.id = id;
        this.entregableId = entregableId;
        this.participanteId = participanteId;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.feedback = feedback;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntregableId() {
        return entregableId;
    }

    public void setEntregableId(Long entregableId) {
        this.entregableId = entregableId;
    }

    public Long getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(Long participanteId) {
        this.participanteId = participanteId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
