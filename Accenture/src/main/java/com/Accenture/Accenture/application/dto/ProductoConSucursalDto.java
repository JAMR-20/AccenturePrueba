package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para mostrar productos con información de sucursal
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoConSucursalDto {
    private Long productoId;
    private String nombreProducto;
    private BigDecimal stock;
    private Long sucursalId;
    private String nombreSucursal;
}
