package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para creación de Franquicia
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FranquiciaCreacionResponseDto {
    private String mensaje;
    private Long id;
    private String nombre;
    private String timestamp;
}
