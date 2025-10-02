package com.Accenture.Accenture.domain.exception;

/**
 * Excepción lanzada cuando se intenta crear una franquicia con un nombre que ya existe
 */
public class FranquiciaDuplicadaException extends RuntimeException {
    
    public FranquiciaDuplicadaException(String message) {
        super(message);
    }
    
    public FranquiciaDuplicadaException(String nombre, boolean isNombre) {
        super("Ya existe una franquicia con el nombre: " + nombre);
    }
}
