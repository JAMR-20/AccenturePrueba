package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para operaciones de actualización
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizacionResponseDto {
    private String mensaje;
    private Long id;
    private String nombreNuevo;
    private String timestamp;
}
