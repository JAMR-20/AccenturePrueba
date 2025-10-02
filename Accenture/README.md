# API de Franquicias - Sistema Reactivo

Este proyecto implementa un API REST reactivo para el manejo de franquicias, sucursales y productos, desarrollado con Spring Boot y programación reactiva.

## Arquitectura

El proyecto sigue los principios de Clean Architecture con las siguientes capas:

- **Dominio**: Entidades de negocio y interfaces de repositorio
- **Aplicación**: Casos de uso, DTOs y mappers
- **Infraestructura**: Implementaciones de persistencia, controladores REST y configuración

## Tecnologías Utilizadas

- **Spring Boot 3.5.6**
- **Spring WebFlux** (Programación Reactiva)
- **Spring Data JPA** (Persistencia)
- **MySQL 8.0**
- **Lombok**
- **Maven**
- **Bean Validation**

## Características

- Programación Reactiva con WebFlux
- Clean Architecture
- Persistencia con MySQL
- Validaciones robustas con Bean Validation
- Manejo global de excepciones
- Mensajes descriptivos y constantes centralizadas
- Respuestas estructuradas
- Validaciones


## Endpoints de la API

### Franquicias

- `POST /api/v1/franquicias` - Crear franquicia
- `PUT /api/v1/franquicias/{id}/nombre` - Actualizar nombre de franquicia

### Sucursales

- `POST /api/v1/sucursales` - Crear sucursal
- `PUT /api/v1/sucursales/{id}/nombre` - Actualizar nombre de sucursal

### Productos

- `POST /api/v1/productos` - Crear producto
- `PUT /api/v1/productos/{id}/stock` - Actualizar stock de producto
- `PUT /api/v1/productos/{id}/nombre` - Actualizar nombre de producto
- `DELETE /api/v1/productos/{id}` - Eliminar producto
- `GET /api/v1/productos/franquicia/{franquiciaId}/mayor-stock` - Obtener productos con mayor stock por sucursal

## Instalación y Ejecución

### Prerrequisitos

- Java 17+
- Maven 3.6+
- MySQL 8.0+ (XAMPP, MySQL Workbench, o Docker)

### Pasos para ejecutar

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd Accenture
   ```

2. **Configurar MySQL**
   - Crear base de datos: `franquicias_db`
   - Usuario: `root`
   - Password: `password`
   - Puerto: `3306`

3. **Compilar y ejecutar la aplicación**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Verificar que la aplicación esté ejecutándose**
   - La aplicación estará disponible en `http://localhost:8080`
   - MySQL estará disponible en `localhost:3306`

## Ejemplos de Uso

### Crear una Franquicia

```bash
curl -X POST http://localhost:8080/api/v1/franquicias \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "McDonald'\''s"
  }'
```

**Respuesta:**
```json
{
  "mensaje": "Franquicia creada exitosamente",
  "id": 1,
  "nombre": "McDonald's",
  "timestamp": "2025-10-02T10:30:45.123"
}
```

### Crear una Sucursal

```bash
curl -X POST http://localhost:8080/api/v1/sucursales \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "McDonald'\''s Centro",
    "franquiciaId": 1
  }'
```

**Respuesta:**
```json
{
  "mensaje": "Sucursal creada exitosamente",
  "id": 1,
  "nombre": "McDonald's Centro",
  "franquiciaId": 1,
  "timestamp": "2025-10-02T10:30:45.123"
}
```

### Crear un Producto

```bash
curl -X POST http://localhost:8080/api/v1/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Big Mac",
    "stock": 50.00,
    "sucursalId": 1
  }'
```

**Respuesta:**
```json
{
  "mensaje": "Producto creado exitosamente",
  "id": 1,
  "nombre": "Big Mac",
  "stock": 50.00,
  "sucursalId": 1,
  "timestamp": "2025-10-02T10:30:45.123"
}
```

### Actualizar Stock de Producto

```bash
curl -X PUT http://localhost:8080/api/v1/productos/1/stock \
  -H "Content-Type: application/json" \
  -d '{
    "stock": 100
  }'
```

**Respuesta:**
```json
{
  "mensaje": "Stock actualizado exitosamente",
  "productoId": 1,
  "nombreProducto": "Big Mac",
  "stockAnterior": 50.00,
  "stockNuevo": 100.00,
  "timestamp": "2025-10-02T10:30:45.123"
}
```

### Actualizar Nombre de Franquicia

```bash
curl -X PUT http://localhost:8080/api/v1/franquicias/1/nombre \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "McDonald'\''s Actualizado"
  }'
```

**Respuesta:**
```json
{
  "mensaje": "Franquicia actualizada exitosamente",
  "id": 1,
  "nombreNuevo": "McDonald's Actualizado",
  "timestamp": "2025-10-02T10:30:45.123"
}
```

### Obtener productos con mayor stock por sucursal

```bash
curl -X GET http://localhost:8080/api/v1/productos/franquicia/1/mayor-stock
```

**Respuesta:**
```json
[
  {
    "id": 1,
    "nombre": "Big Mac",
    "stock": 100.00,
    "sucursalId": 1,
    "nombreSucursal": "McDonald's Centro"
  }
]
```

## Configuración

### Base de Datos

La aplicación está configurada para usar MySQL con las siguientes propiedades:

- **Host**: localhost:3306
- **Database**: franquicias_db
- **Username**: root
- **Password**: password

### Propiedades de la Aplicación

Las configuraciones están en `src/main/resources/application.properties`:

- Configuración de JPA para entidades
- Configuración de MySQL
- Configuración de logging
- Configuración del servidor (puerto 8080)

## Validaciones Implementadas

### Validaciones de Entrada

- **Nombres obligatorios**: No pueden estar vacíos
- **Nombres con longitud**: Entre 2-100 caracteres
- **Stock positivo**: Debe ser mayor a cero
- **IDs obligatorios**: No pueden ser nulos
- **Prevención de duplicados**: Por entidad (franquicia, sucursal, producto)

### Manejo de Errores

- **400 Bad Request**: Errores de validación con detalles específicos
- **404 Not Found**: Entidades no encontradas
- **409 Conflict**: Nombres duplicados
- **500 Internal Server Error**: Errores inesperados

### Ejemplos de Errores

**Stock Negativo:**
```json
{
  "timestamp": "2025-10-02T10:30:45.123",
  "status": 400,
  "error": "Error de validación",
  "message": "stock: El stock debe ser un número positivo mayor a cero",
  "details": [
    {
      "field": "stock",
      "message": "El stock debe ser un número positivo mayor a cero",
      "rejectedValue": "-50"
    }
  ]
}
```

**Franquicia No Encontrada:**
```json
{
  "timestamp": "2025-10-02T10:30:45.123",
  "status": 404,
  "error": "Franquicia no encontrada",
  "message": "No se encontró una franquicia con ID: 999"
}
```

## Criterios de Aceptación Implementados

**1. Proyecto desarrollado en Spring Boot**
- Spring Boot 3.5.6 con Java 17

**2. Endpoint para agregar franquicia**
- `POST /api/v1/franquicias`

**3. Endpoint para agregar sucursal a franquicia**
- `POST /api/v1/sucursales`

**4. Endpoint para agregar producto a sucursal**
- `POST /api/v1/productos`

**5. Endpoint para eliminar producto de sucursal**
- `DELETE /api/v1/productos/{id}`

**6. Endpoint para modificar stock de producto**
- `PUT /api/v1/productos/{id}/stock`

**7. Endpoint para mostrar producto con mayor stock por sucursal**
- `GET /api/v1/productos/franquicia/{franquiciaId}/mayor-stock`

**8. Persistencia con MySQL**
- Configuración completa de MySQL con JPA

## Funcionalidades Adicionales Implementadas

**Endpoints de Actualización**
- `PUT /api/v1/franquicias/{id}/nombre` - Actualizar nombre de franquicia
- `PUT /api/v1/sucursales/{id}/nombre` - Actualizar nombre de sucursal
- `PUT /api/v1/productos/{id}/nombre` - Actualizar nombre de producto

 **Validaciones Robustas**
- Validaciones de entrada con Bean Validation
- Prevención de duplicados por entidad
- Mensajes descriptivos con constantes centralizadas

**Respuestas Estructuradas**
- Respuestas sin campos null
- Mensajes personalizados para cada operación
- Timestamps en todas las respuestas

## Buenas Prácticas Implementadas

- **Clean Architecture**: Separación clara de responsabilidades
- **Programación Reactiva**: Uso de WebFlux
- **Validaciones**: Bean Validation en DTOs con mensajes descriptivos
- **Manejo de Errores**: Controlador global de excepciones
- **Constantes**: Centralización de mensajes y códigos
- **DTOs Específicos**: Para cada operación (creación, actualización)
- **Logging**: Configuración de logs para debugging

## Testing

Para ejecutar las pruebas:

```bash
mvn test
```

