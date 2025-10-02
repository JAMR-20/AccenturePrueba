package com.Accenture.Accenture.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad JPA para Franquicia
 */
@Entity
@Table(name = "franquicias")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FranquiciaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    @OneToMany(mappedBy = "franquicia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SucursalEntity> sucursales;
}
