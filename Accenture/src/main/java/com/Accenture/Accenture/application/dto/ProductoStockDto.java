package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para respuesta de modificación de stock con información completa
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoStockDto {
    private Long id;
    private String nombre;
    private BigDecimal stockAnterior;
    private BigDecimal stockNuevo;
    private Long sucursalId;
}
