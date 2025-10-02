package com.Accenture.Accenture.application.mapper;

import com.Accenture.Accenture.application.dto.SucursalDto;
import com.Accenture.Accenture.domain.entity.Sucursal;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversión entre Sucursal y SucursalDto
 */
@Component
public class SucursalMapper {
    
    public SucursalDto toDto(Sucursal sucursal) {
        if (sucursal == null) {
            return null;
        }
        
        return SucursalDto.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .franquiciaId(sucursal.getFranquiciaId())
                .productos(null) // Se maneja por separado para evitar recursión
                .build();
    }
    
    public Sucursal toEntity(SucursalDto sucursalDto) {
        if (sucursalDto == null) {
            return null;
        }
        
        return Sucursal.builder()
                .id(sucursalDto.getId())
                .nombre(sucursalDto.getNombre())
                .franquiciaId(sucursalDto.getFranquiciaId())
                .productos(null) // Se maneja por separado para evitar recursión
                .build();
    }
}
