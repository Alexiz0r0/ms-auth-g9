package com.jaob.ms_auth.aggregates.constants;

public class Constantes {
    // Mensajes de éxito
    public static final String MESSAGE_SUCCESSFUL = "Operación realizada con éxito.";
    public static final String MESSAGE_CREATED = "Recurso creado correctamente.";
    public static final String MESSAGE_UPDATED = "Recurso actualizado correctamente.";
    public static final String MESSAGE_FOUND = "Recurso encontrado.";
    public static final String MESSAGE_DELETED = "Recurso eliminado.";

    // Mensajes de error
    public static final String MESSAGE_ERROR = "Ocurrió un error interno en el servidor.";
    public static final String MESSAGE_ERROR_CREATION = "No se pudo crear el recurso.";
    public static final String MESSAGE_ERROR_UPDATE = "No se pudo actualizar el recurso.";
    public static final String MESSAGE_NOT_FOUND = "Recurso no encontrado.";
    public static final String MESSAGE_BAD_REQUEST = "La solicitud contiene datos incorrectos o incompletos.";

    public static final String MESSAGE_EXPIRED_TOKEN = "El token JWT ha expirado. Por favor vuelve a iniciar sesión.";
    public static final String MESSAGE_SIGNATURE_INVALID_TOKEN = "Firma de token no válida. El token podría haber sido manipulado.";
    public static final String MESSAGE_INVALID_TOKEN = "El token JWT es inválido o está mal formado.";
    public static final String MESSAGE_UNSUPPORTED_TOKEN = "El token JWT no es compatible.";
    public static final String MESSAGE_ILLEGAL_TOKEN = "El token JWT está vacío o nulo.";

    public static final String MESSAGE_INVALID_ROL = "Tipo de rol inválido: '";
    public static final String MESSAGE_USER_NOT_FOUND = "Usuario no encontrado.";

    // Códigos de estado HTTP
    public static final int CODE_SUCCESSFUL = 200;
    public static final int CODE_CREATED = 201;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_UNAUTHORIZED = 401;
    public static final int CODE_FORBIDDEN = 403;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_ERROR = 500;


    // url
    public static final String FREE_ACCESS_ENDPOINTS = "/auth/**";
    public static final String SUPERADMIN_ENDPOINTS = "/test/superadmin/**";
    public static final String ADMIN_ENDPOINTS = "/test/admin/**";
    public static final String USER_ENDPOINTS= "/test/user/**";
}
