package com.jaob.ms_auth.service.impl;

import com.jaob.ms_auth.entity.Rol;
import com.jaob.ms_auth.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceImplTest {

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtServiceImpl service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setEmail("abc@abc.gg");
        usuario.setRol(Rol.SUPERADMIN);

        String secret = Base64.getEncoder().encodeToString("800b1e5989492ad13b6c142ebd7533ccdcc9944d833be725bf2f43a4588a8d6f".getBytes());
        ReflectionTestUtils.setField(service, "keySignature", secret);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("SUPERADMIN"));

        when(userDetails.getUsername()).thenReturn(usuario.getUsername());
        when(userDetails.getAuthorities()).thenReturn((Collection) authorities);

    }

    @Test
    void extractUserName() {
        //ARRANGE
        String token = service.generarToken(userDetails, usuario);
        //ACT
        String username = service.extractUserName(token);
        //ASSERT
        assertEquals(usuario.getEmail(), username);
    }

    @Test
    void generarToken() {
        //ARRANGE
        //ACT
        String token = service.generarToken(userDetails, usuario);
        //ASSERT
        assertNotNull(token);
        assertTrue(token.length() > 10);
    }

    @Test
    void validarToken() {
        //ARRANGE
        String token = service.generarToken(userDetails, usuario);
        //ACT
        boolean esValido = service.validarToken(token, userDetails);
        //ASSERT
        assertTrue(esValido);
    }

    @Test
    void retonaFalseEnValidarToken() {
        //ARRANGE
        String token = service.generarToken(userDetails, usuario);
        when(userDetails.getUsername()).thenReturn("azul@aa.by");
        //ACT
        boolean esValido = service.validarToken(token, userDetails);
        //ASSERT
        assertFalse(esValido);
    }
}