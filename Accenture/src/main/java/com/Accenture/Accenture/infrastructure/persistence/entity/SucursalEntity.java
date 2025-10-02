package com.Accenture.Accenture.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad JPA para Sucursal
 */
@Entity
@Table(name = "sucursales")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franquicia_id", nullable = false)
    private FranquiciaEntity franquicia;
    
    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductoEntity> productos;
}
