package com.Accenture.Accenture.domain.exception;

/**
 * Excepción lanzada cuando se intenta crear una sucursal con un nombre que ya existe para una franquicia específica
 */
public class SucursalDuplicadaException extends RuntimeException {
    
    public SucursalDuplicadaException(String message) {
        super(message);
    }
    
    public SucursalDuplicadaException(String nombre, Long franquiciaId) {
        super("Ya existe una sucursal con el nombre '" + nombre + "' para la franquicia con ID: " + franquiciaId);
    }
}
