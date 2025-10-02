package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.Accenture.Accenture.application.constants.ApiConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para actualizar el nombre de un Producto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarNombreProductoDto {
    
    @NotBlank(message = ApiConstants.NOMBRE_PRODUCTO_OBLIGATORIO)
    @Size(min = 2, max = 100, message = ApiConstants.NOMBRE_DEBE_TENER_MINIMO_CARACTERES)
    private String nombre;
}
