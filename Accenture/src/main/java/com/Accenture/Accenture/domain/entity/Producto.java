package com.Accenture.Accenture.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidad de dominio que representa un producto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private Long id;
    private String nombre;
    private BigDecimal stock;
    private Long sucursalId;
}
