package com.jaob.ms_auth.entity;

import com.jaob.ms_auth.aggregates.constants.Constantes;
import com.jaob.ms_auth.exceptions.FormatoIncorrectoException;

public enum Rol {
    SUPERADMIN, ADMIN, USUARIO;

    public static Rol fromStringToRol(String value) {
        return switch (value) {
            case "SUPERADMIN" -> SUPERADMIN;
            case "ADMIN" -> ADMIN;
            case "USUARIO" -> USUARIO;
            default -> throw new FormatoIncorrectoException(Constantes.MESSAGE_INVALID_ROL + value + "'.");
        };
    }
}
