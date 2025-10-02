package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para respuesta de modificación de stock
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResponseDto {
    private String mensaje;
    private Long productoId;
    private String nombreProducto;
    private BigDecimal stockAnterior;
    private BigDecimal stockNuevo;
    private String timestamp;
}
