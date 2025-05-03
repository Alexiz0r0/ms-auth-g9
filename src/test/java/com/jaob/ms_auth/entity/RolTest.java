package com.jaob.ms_auth.entity;

import com.jaob.ms_auth.aggregates.constants.Constantes;
import com.jaob.ms_auth.exceptions.FormatoIncorrectoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolTest {

    @Test
    void debeRetornarRolSuperadmin() {
        // ARRANGE
        String rol = "SUPERADMIN";
        // ACT
        Rol resultado = Rol.fromStringToRol(rol);
        // ASSERT
        assertEquals(Rol.SUPERADMIN, resultado);
    }

    @Test
    void debeRetornarRolAdmin() {
        // ARRANGE
        String rol = "ADMIN";
        // ACT
        Rol resultado = Rol.fromStringToRol(rol);
        // ASSERT
        assertEquals(Rol.ADMIN, resultado);
    }

    @Test
    void debeRetornarRolUsuario() {
        // ARRANGE
        String rol = "USUARIO";
        // ACT
        Rol resultado = Rol.fromStringToRol(rol);
        // ASSERT
        assertEquals(Rol.USUARIO, resultado);
    }

    @Test
    void debeLanzarFormatoIncorrectoExceptionParaRolInvalido() {
        // ARRANGE
        String rolInvalido = "INVÁLIDO";
        // ACT & ASSERT
        FormatoIncorrectoException exception = assertThrows(FormatoIncorrectoException.class, () -> {
            Rol.fromStringToRol(rolInvalido);
        });
        assertTrue(exception.getMessage().contains(Constantes.MESSAGE_INVALID_ROL));
        assertTrue(exception.getMessage().contains("INVÁLIDO"));
    }
}