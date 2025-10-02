package com.Accenture.Accenture.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidad de dominio que representa una franquicia
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Franquicia {
    private Long id;
    private String nombre;
    private List<Sucursal> sucursales;
}
