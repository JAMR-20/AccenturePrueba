package com.Accenture.Accenture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.Accenture.Accenture.application.constants.ApiConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO para actualizar el stock de un Producto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarStockDto {
    
    @NotNull(message = ApiConstants.STOCK_OBLIGATORIO)
    @Positive(message = ApiConstants.STOCK_POSITIVO)
    private Integer stock;
}
