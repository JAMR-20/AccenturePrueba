package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuesta de eliminación exitosa
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EliminacionResponseDto {
    private String mensaje;
    private Long id;
    private String timestamp;
}
