package com.jaob.ms_auth.service.impl;

import com.jaob.ms_auth.aggregates.constants.Constantes;
import com.jaob.ms_auth.aggregates.response.ResponseBase;
import com.jaob.ms_auth.aggregates.response.UsuarioDTO;
import com.jaob.ms_auth.entity.Rol;
import com.jaob.ms_auth.entity.Usuario;
import com.jaob.ms_auth.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioServiceImpl service;

    private Usuario usuario;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
    }

    @Test
    void userDetailsService() {
        //ARRANGE
        String email = "abc@abc.com";
        usuario.setEmail(email);
        when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
        //ACT
        UserDetailsService userDetailsService = service.userDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        //ASSERT
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());

        verify(repository).findByEmail(email);
    }

    @Test
    void listarSuperAdmins() {
        //ARRANGE
        List<Usuario> usuarios1 = List.of(usuario);
        when(repository.findAllByRol(Rol.SUPERADMIN)).thenReturn(usuarios1);
        //ACT
        ResponseBase<List<UsuarioDTO>> response = service.listarSuperAdmins();
        //ASSERT
        assertNotNull(response);
        assertEquals(Constantes.CODE_SUCCESSFUL, response.getStatusCode());
        assertFalse(response.isHasError());
        assertEquals(Constantes.MESSAGE_SUCCESSFUL, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());

        verify(repository).findAllByRol(Rol.SUPERADMIN);
    }

    @Test
    void listarAdmins() {
        //ARRANGE
        List<Usuario> usuarios2 = List.of(usuario);
        when(repository.findAllByRol(Rol.ADMIN)).thenReturn(usuarios2);
        //ACT
        ResponseBase<List<UsuarioDTO>> response = service.listarAdmins();
        //ASSERT
        assertNotNull(response);
        assertEquals(Constantes.CODE_SUCCESSFUL, response.getStatusCode());
        assertFalse(response.isHasError());
        assertEquals(Constantes.MESSAGE_SUCCESSFUL, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());

        verify(repository).findAllByRol(Rol.ADMIN);
    }

    @Test
    void listarUsuarios() {
        //ARRANGE
        List<Usuario> usuarios3 = List.of(usuario);
        when(repository.findAllByRol(Rol.USUARIO)).thenReturn(usuarios3);
        //ACT
        ResponseBase<List<UsuarioDTO>> response = service.listarUsuarios();
        //ASSERT
        assertNotNull(response);
        assertEquals(Constantes.CODE_SUCCESSFUL, response.getStatusCode());
        assertFalse(response.isHasError());
        assertEquals(Constantes.MESSAGE_SUCCESSFUL, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());

        verify(repository).findAllByRol(Rol.USUARIO);
    }

    @Test
    void lanzarExcepcionUserNotFound() {
        //ARRANGE
        String email = "abc@abc.com";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());
        //ACT
        UserDetailsService userDetailsService = service.userDetailsService();
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));
        //ASSERT
        assertEquals(Constantes.MESSAGE_USER_NOT_FOUND, exception.getMessage());

        verify(repository).findByEmail(email);
    }
}