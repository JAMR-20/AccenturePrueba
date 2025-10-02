package com.Accenture.Accenture.domain.exception;

/**
 * Excepción lanzada cuando no se encuentra una franquicia
 */
public class FranquiciaNotFoundException extends RuntimeException {

    public FranquiciaNotFoundException(Long id) {
        super("Franquicia con ID " + id + " no encontrada");
    }
}
