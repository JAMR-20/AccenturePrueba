package com.Accenture.Accenture.application.constants;

/**
 * Constantes para la API
 */
public final class ApiConstants {
    
    public static final String NOMBRE_FRANQUICIA_OBLIGATORIO = "El nombre de la franquicia es obligatorio";
    public static final String NOMBRE_SUCURSAL_OBLIGATORIO = "El nombre de la sucursal es obligatorio";
    public static final String NOMBRE_PRODUCTO_OBLIGATORIO = "El nombre del producto es obligatorio";
    public static final String FRANQUICIA_ID_OBLIGATORIO = "El ID de la franquicia es obligatorio";
    public static final String SUCURSAL_ID_OBLIGATORIO = "El ID de la sucursal es obligatorio";
    public static final String STOCK_OBLIGATORIO = "El stock es obligatorio";
    public static final String STOCK_POSITIVO = "El stock debe ser un número positivo mayor a cero";

    public static final String NOMBRE_DEBE_TENER_MINIMO_CARACTERES = "El nombre debe tener al menos 2 caracteres";

    public static final String PRODUCTO_ELIMINADO_EXITOSAMENTE = "Producto eliminado exitosamente";
    
    public static final String STOCK_ACTUALIZADO_EXITOSAMENTE = "Stock actualizado exitosamente";

    public static final String FRANQUICIA_CREADA_EXITOSAMENTE = "Franquicia creada exitosamente";
    public static final String SUCURSAL_CREADA_EXITOSAMENTE = "Sucursal creada exitosamente";
    public static final String PRODUCTO_CREADO_EXITOSAMENTE = "Producto creado exitosamente";
    
    public static final String FRANQUICIA_ACTUALIZADA_EXITOSAMENTE = "Franquicia actualizada exitosamente";
    public static final String SUCURSAL_ACTUALIZADA_EXITOSAMENTE = "Sucursal actualizada exitosamente";
    public static final String PRODUCTO_ACTUALIZADO_EXITOSAMENTE = "Producto actualizado exitosamente";
    

    public static final int HTTP_BAD_REQUEST = 400;

    public static final String ERROR_VALIDACION = "Error de validación";
    public static final String ERROR_FRANQUICIA_NO_ENCONTRADA = "Franquicia no encontrada";
    public static final String ERROR_FRANQUICIA_DUPLICADA = "Franquicia duplicada";
    public static final String ERROR_SUCURSAL_DUPLICADA = "Sucursal duplicada";
    public static final String ERROR_PRODUCTO_DUPLICADO = "Producto duplicado";
    public static final String ERROR_SUCURSAL_NO_ENCONTRADA = "Sucursal no encontrada";
    public static final String ERROR_PRODUCTO_NO_ENCONTRADO = "Producto no encontrado";

    
    private ApiConstants() {
    }
}
