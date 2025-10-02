package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.Accenture.Accenture.application.constants.ApiConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para actualizar el nombre de una Sucursal
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarNombreSucursalDto {
    
    @NotBlank(message = ApiConstants.NOMBRE_SUCURSAL_OBLIGATORIO)
    @Size(min = 2, max = 100, message = ApiConstants.NOMBRE_DEBE_TENER_MINIMO_CARACTERES)
    private String nombre;
}
