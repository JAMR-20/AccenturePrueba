package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para creación de Sucursal
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalCreacionResponseDto {
    private String mensaje;
    private Long id;
    private String nombre;
    private Long franquiciaId;
    private String timestamp;
}
