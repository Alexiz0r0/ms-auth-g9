package com.jaob.ms_auth.service.impl;

import com.jaob.ms_auth.aggregates.constants.Constantes;
import com.jaob.ms_auth.aggregates.request.UsuarioSignInRequest;
import com.jaob.ms_auth.aggregates.request.UsuarioSignUpRequest;
import com.jaob.ms_auth.aggregates.response.ResponseBase;
import com.jaob.ms_auth.aggregates.response.SignInResponse;
import com.jaob.ms_auth.aggregates.response.UsuarioDTO;
import com.jaob.ms_auth.entity.Rol;
import com.jaob.ms_auth.entity.Usuario;
import com.jaob.ms_auth.repository.UsuarioRepository;
import com.jaob.ms_auth.service.JwtService;
import com.jaob.ms_auth.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.InputMismatchException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @Mock
    private AuthenticationManager manager;
    @Mock
    private UsuarioRepository repository;
    @Mock
    private JwtService jwtService;
    @Mock
    private UsuarioService service;
    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private Usuario usuario;
    private UsuarioSignUpRequest requestUp;
    private UsuarioSignInRequest requestIn;

    private String email = "abc@abc.gg";
    private String pwd = "1234";
    private String token = "gwu3G+KNN0.gj8zh55BvXy0L.fknTuDTH3phe2g";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(pwd));
        usuario.setRol(Rol.SUPERADMIN);
        requestUp = new UsuarioSignUpRequest();
        requestUp.setEmail(email);
        requestUp.setPassword(pwd);
        requestUp.setRol("SUPERADMIN");
        requestIn = new UsuarioSignInRequest();
        requestIn.setEmail(email);
        requestIn.setPassword(pwd);

    }

    @Test
    void registrar() {
        //ARRANGE
        when(repository.save(any(Usuario.class))).thenReturn(usuario);
        //ACT
        ResponseBase<UsuarioDTO> response = authenticationService.registrar(requestUp);
        //ASSERT
        assertNotNull(response);
        assertEquals(Constantes.CODE_CREATED, response.getStatusCode());
        assertEquals(Constantes.MESSAGE_CREATED, response.getMessage());
        assertFalse(response.isHasError());
        assertNotNull(response.getData());

        verify(repository).save(any(Usuario.class));
    }

    @Test
    void login() {
        //ARRANGE

        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(usuario));
        when(service.userDetailsService()).thenReturn(username -> userDetails);
        when(jwtService.generarToken(any(), eq(usuario))).thenReturn(token);
        //ACT
        ResponseBase<SignInResponse> response = authenticationService.login(requestIn);
        //ASSERT
        assertNotNull(response);
        assertEquals(Constantes.CODE_SUCCESSFUL, response.getStatusCode());
        assertEquals(Constantes.MESSAGE_SUCCESSFUL, response.getMessage());
        assertFalse(response.isHasError());
        assertNotNull(response.getData());
        assertEquals(token, response.getData().getToken());

        verify(manager).authenticate(any());
    }

    @Test
    void validate() {
        //ARRANGE
        String tokenWithBearer = "Bearer " + token;
        when(jwtService.extractUserName(token)).thenReturn(email);
        when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
        //ACT
        ResponseBase<UsuarioDTO> response = authenticationService.validate(tokenWithBearer);
        //ASSERT
        assertNotNull(response);
        assertEquals(Constantes.CODE_SUCCESSFUL, response.getStatusCode());
        assertEquals(Constantes.MESSAGE_SUCCESSFUL, response.getMessage());
        assertFalse(response.isHasError());
        assertNotNull(response.getData());
        assertEquals(email, response.getData().getEmail());

        verify(jwtService).extractUserName(token);
        verify(repository).findByEmail(email);
    }

    @Test
    void lanzarExcepcionTokenInvalido() {
        //ARRANGE
        String tokenWithBearer = " " + token;
        //ACT
        InputMismatchException exception = assertThrows(InputMismatchException.class, () -> authenticationService.validate(tokenWithBearer));
        //ASSERT
        assertEquals(Constantes.MESSAGE_ILLEGAL_TOKEN, exception.getMessage());

        verify(jwtService, never()).extractUserName(any());
        verify(repository, never()).findByEmail(any());
    }
}