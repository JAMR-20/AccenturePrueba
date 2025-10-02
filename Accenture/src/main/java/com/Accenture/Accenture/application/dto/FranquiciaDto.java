package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.Accenture.Accenture.application.constants.ApiConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * DTO para transferencia de datos de Franquicia
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FranquiciaDto {
    
    private Long id;
    
    @NotBlank(message = ApiConstants.NOMBRE_FRANQUICIA_OBLIGATORIO)
    @Size(min = 2, max = 100, message = ApiConstants.NOMBRE_DEBE_TENER_MINIMO_CARACTERES)
    private String nombre;
    
    private List<SucursalDto> sucursales;
}
