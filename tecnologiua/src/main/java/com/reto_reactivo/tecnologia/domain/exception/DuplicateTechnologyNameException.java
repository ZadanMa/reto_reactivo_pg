// src/main/java/com/reto_reactivo/tecnologia/domain/exception/DuplicateTechnologyNameException.java
package com.reto_reactivo.tecnologia.domain.exception;

public class DuplicateTechnologyNameException extends RuntimeException {
    public DuplicateTechnologyNameException(String message) {
        super(message);
    }
}
