package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.Accenture.Accenture.application.constants.ApiConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

/**
 * DTO para transferencia de datos de Producto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {
    private Long id;
    
    @NotBlank(message = ApiConstants.NOMBRE_PRODUCTO_OBLIGATORIO)
    @Size(min = 2, max = 100, message = ApiConstants.NOMBRE_DEBE_TENER_MINIMO_CARACTERES)
    private String nombre;
    
    @NotNull(message = ApiConstants.STOCK_OBLIGATORIO)
    @DecimalMin(value = "0.01", message = ApiConstants.STOCK_POSITIVO)
    private BigDecimal stock;
    
    @NotNull(message = ApiConstants.SUCURSAL_ID_OBLIGATORIO)
    private Long sucursalId;
}
