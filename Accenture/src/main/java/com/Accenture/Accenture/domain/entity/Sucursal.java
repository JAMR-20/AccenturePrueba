package com.Accenture.Accenture.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidad de dominio que representa una sucursal
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {
    private Long id;
    private String nombre;
    private Long franquiciaId;
    private List<Producto> productos;
}
