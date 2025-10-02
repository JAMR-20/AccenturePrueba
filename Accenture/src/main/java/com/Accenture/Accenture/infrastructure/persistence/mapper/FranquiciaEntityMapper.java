package com.Accenture.Accenture.infrastructure.persistence.mapper;

import com.Accenture.Accenture.domain.entity.Franquicia;
import com.Accenture.Accenture.infrastructure.persistence.entity.FranquiciaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper entre Franquicia (dominio) y FranquiciaEntity (persistencia)
 */
@Component
public class FranquiciaEntityMapper {
    
    public Franquicia toDomain(FranquiciaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Franquicia.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .sucursales(null)
                .build();
    }
    
    public FranquiciaEntity toEntity(Franquicia franquicia) {
        if (franquicia == null) {
            return null;
        }
        
        return FranquiciaEntity.builder()
                .id(franquicia.getId())
                .nombre(franquicia.getNombre())
                .sucursales(null)
                .build();
    }
}
