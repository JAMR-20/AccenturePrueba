package com.Accenture.Accenture.application.mapper;

import com.Accenture.Accenture.application.dto.ProductoConSucursalDto;
import com.Accenture.Accenture.application.dto.ProductoDto;
import com.Accenture.Accenture.domain.entity.Producto;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversión entre Producto y ProductoDto
 */
@Component
public class ProductoMapper {
    
    public ProductoDto toDto(Producto producto) {
        if (producto == null) {
            return null;
        }
        
        return ProductoDto.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .stock(producto.getStock())
                .sucursalId(producto.getSucursalId())
                .build();
    }
    
    public Producto toEntity(ProductoDto productoDto) {
        if (productoDto == null) {
            return null;
        }
        
        return Producto.builder()
                .id(productoDto.getId())
                .nombre(productoDto.getNombre())
                .stock(productoDto.getStock())
                .sucursalId(productoDto.getSucursalId())
                .build();
    }
    
    public ProductoConSucursalDto toDtoConSucursal(Producto producto, String nombreSucursal) {
        if (producto == null) {
            return null;
        }
        
        return ProductoConSucursalDto.builder()
                .productoId(producto.getId())
                .nombreProducto(producto.getNombre())
                .stock(producto.getStock())
                .sucursalId(producto.getSucursalId())
                .nombreSucursal(nombreSucursal)
                .build();
    }
}
