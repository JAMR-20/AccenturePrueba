package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO de respuesta para creación de Producto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCreacionResponseDto {
    private String mensaje;
    private Long id;
    private String nombre;
    private BigDecimal stock;
    private Long sucursalId;
    private String timestamp;
}
