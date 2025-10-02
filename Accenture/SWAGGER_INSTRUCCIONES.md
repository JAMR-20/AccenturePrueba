# 📚 Documentación Swagger/OpenAPI

## 🚀 **Acceso a la Documentación**

Una vez que la aplicación esté ejecutándose, puedes acceder a la documentación interactiva en:

### **Swagger UI (Interfaz Visual)**
```
http://localhost:8080/swagger-ui.html
```

### **API Docs (JSON)**
```
http://localhost:8080/api-docs
```

## 🎯 **Características de la Documentación**

### ✅ **Interfaz Interactiva**
- **Probar endpoints directamente** desde el navegador
- **Ejemplos de request/response** automáticos
- **Validación en tiempo real** de los datos
- **Códigos de respuesta** documentados

### ✅ **Organización por Categorías**
- **🏢 Franquicias** - Gestión de franquicias
- **🏪 Sucursales** - Gestión de sucursales  
- **📦 Productos** - Gestión de productos

### ✅ **Información Detallada**
- **Descripción de cada endpoint**
- **Parámetros requeridos y opcionales**
- **Ejemplos de datos de entrada**
- **Códigos de respuesta HTTP**
- **Esquemas de datos (DTOs)**

## 🔧 **Cómo Usar Swagger UI**

### **1. Navegar por la Documentación**
- **Expandir** cada categoría (Franquicias, Sucursales, Productos)
- **Ver** todos los endpoints disponibles
- **Leer** la descripción de cada operación

### **2. Probar Endpoints**
- **Hacer clic** en "Try it out" en cualquier endpoint
- **Completar** los parámetros requeridos
- **Hacer clic** en "Execute"
- **Ver** la respuesta en tiempo real

### **3. Ejemplos de Uso**

#### **Crear Franquicia:**
1. Ir a **Franquicias** → **POST /api/v1/franquicias**
2. Hacer clic en **"Try it out"**
3. En el campo **Request body**, pegar:
```json
{
  "nombre": "McDonald's"
}
```
4. Hacer clic en **"Execute"**

#### **Obtener Productos con Mayor Stock:**
1. Ir a **Productos** → **GET /api/v1/productos/franquicia/{franquiciaId}/mayor-stock**
2. Hacer clic en **"Try it out"**
3. En **franquiciaId**, poner: `1`
4. Hacer clic en **"Execute"**

## 📋 **Ventajas sobre Postman**

### ✅ **Documentación Integrada**
- No necesitas importar colecciones
- La documentación se actualiza automáticamente
- Ejemplos de datos incluidos

### ✅ **Interfaz Visual**
- Más fácil de usar
- Navegación intuitiva
- Respuestas formateadas

### ✅ **Validación Automática**
- Valida los datos antes de enviar
- Muestra errores de validación
- Esquemas de datos claros

## 🎉 **¡Listo para Usar!**

1. **Ejecutar la aplicación:** `mvn spring-boot:run`
2. **Abrir navegador:** http://localhost:8080/swagger-ui.html
3. **Explorar y probar** todos los endpoints
4. **¡Disfrutar de la documentación interactiva!**

**¡Ya no necesitas Postman! Todo está en Swagger UI** 🚀
