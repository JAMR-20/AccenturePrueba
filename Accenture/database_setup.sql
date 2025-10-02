-- Script para configurar la base de datos en XAMPP/phpMyAdmin
-- Ejecutar este script en phpMyAdmin

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS franquicias_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE franquicias_db;

-- Crear tabla franquicias
CREATE TABLE IF NOT EXISTS franquicias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Crear tabla sucursales
CREATE TABLE IF NOT EXISTS sucursales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    franquicia_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (franquicia_id) REFERENCES franquicias(id) ON DELETE CASCADE,
    UNIQUE KEY unique_nombre_por_franquicia (nombre, franquicia_id)
);

-- Crear tabla productos
CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    stock DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    sucursal_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (sucursal_id) REFERENCES sucursales(id) ON DELETE CASCADE,
    UNIQUE KEY unique_nombre_por_sucursal (nombre, sucursal_id)
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_sucursales_franquicia_id ON sucursales(franquicia_id);
CREATE INDEX idx_productos_sucursal_id ON productos(sucursal_id);
CREATE INDEX idx_productos_stock ON productos(stock);

-- Insertar datos de prueba
INSERT INTO franquicias (nombre) VALUES 
('McDonald\'s'),
('Burger King'),
('KFC'),
('Pizza Hut');

INSERT INTO sucursales (nombre, franquicia_id) VALUES 
('McDonald\'s Centro', 1),
('McDonald\'s Norte', 1),
('Burger King Plaza', 2),
('KFC Mall', 3),
('Pizza Hut Delivery', 4);

INSERT INTO productos (nombre, stock, sucursal_id) VALUES 
('Big Mac', 50.00, 1),
('McNuggets', 30.00, 1),
('Whopper', 25.00, 3),
('Chicken Wings', 40.00, 4),
('Pizza Margherita', 15.00, 5),
('Coca Cola', 100.00, 1),
('Fries', 60.00, 2);
