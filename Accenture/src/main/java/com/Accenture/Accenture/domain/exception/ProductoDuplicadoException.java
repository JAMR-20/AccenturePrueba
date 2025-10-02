package com.Accenture.Accenture.domain.exception;

/**
 * Excepción lanzada cuando se intenta crear un producto con un nombre que ya existe en una sucursal específica
 */
public class ProductoDuplicadoException extends RuntimeException {

    public ProductoDuplicadoException(String nombre, Long sucursalId) {
        super("Ya existe un producto con el nombre '" + nombre + "' en la sucursal con ID: " + sucursalId);
    }
}
