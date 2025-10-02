package com.Accenture.Accenture.application.mapper;

import com.Accenture.Accenture.application.dto.FranquiciaDto;
import com.Accenture.Accenture.domain.entity.Franquicia;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversión entre Franquicia y FranquiciaDto
 */
@Component
public class FranquiciaMapper {
    
    public FranquiciaDto toDto(Franquicia franquicia) {
        if (franquicia == null) {
            return null;
        }
        
        return FranquiciaDto.builder()
                .id(franquicia.getId())
                .nombre(franquicia.getNombre())
                .sucursales(null) // Se maneja por separado para evitar recursión
                .build();
    }
    
    public Franquicia toEntity(FranquiciaDto franquiciaDto) {
        if (franquiciaDto == null) {
            return null;
        }
        
        return Franquicia.builder()
                .id(franquiciaDto.getId())
                .nombre(franquiciaDto.getNombre())
                .sucursales(null) // Se maneja por separado para evitar recursión
                .build();
    }
}
