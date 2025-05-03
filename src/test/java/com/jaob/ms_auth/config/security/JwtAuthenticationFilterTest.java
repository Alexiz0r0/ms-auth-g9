package com.jaob.ms_auth.config.security;

import com.jaob.ms_auth.aggregates.constants.Constantes;
import com.jaob.ms_auth.service.JwtService;
import com.jaob.ms_auth.service.UsuarioService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UserDetails userDetails;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private String token = "Bearer eyJ0eXBlIjoiSldUIi.wiYWxnIjoiSFM1MTIifQ.eyJyb2wiOiJTVVBFUkFET";
    private String email = "abco@abc.com";
    private String jwt = "eyJ0eXBlIjoiSldUIi.wiYWxnIjoiSFM1MTIifQ.eyJyb2wiOiJTVVBFUkFET";

    private static Stream<Arguments> provideJwtExceptions() {
        return Stream.of(
                Arguments.of(new ExpiredJwtException(null, null, ""), Constantes.MESSAGE_EXPIRED_TOKEN),
                Arguments.of(new MalformedJwtException("malformed"), Constantes.MESSAGE_INVALID_TOKEN),
                Arguments.of(new SignatureException("signature"), Constantes.MESSAGE_SIGNATURE_INVALID_TOKEN),
                Arguments.of(new UnsupportedJwtException("unsupported"), Constantes.MESSAGE_UNSUPPORTED_TOKEN),
                Arguments.of(new IllegalArgumentException("illegal"), Constantes.MESSAGE_ILLEGAL_TOKEN)
        );
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void whenNoTokenShouldContinueWithoutAuthentication() throws Exception {
        // ARRANGE
        when(request.getHeader("Authorization")).thenReturn(null);
        // ACT
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        // ASSERT
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void whenTokenShouldAuthenticate() throws Exception {
        // ARRANGE
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtService.extractUserName(jwt)).thenReturn(email);
        when(usuarioService.userDetailsService()).thenReturn(username -> userDetails);
        when(userDetails.getUsername()).thenReturn(email);
        when(jwtService.validarToken(jwt, userDetails)).thenReturn(true);
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());
        // ACT
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        // ASSERT
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void cuandoTokenValidoPeroInvalidaAutenticacionNoSeEstablece() throws Exception {
        // ARRANGE
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtService.extractUserName(jwt)).thenReturn(email);
        when(usuarioService.userDetailsService()).thenReturn(username -> userDetails);
        when(userDetails.getUsername()).thenReturn(email);
        when(jwtService.validarToken(jwt, userDetails)).thenReturn(false);
        // ACT
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        // ASSERT
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @ParameterizedTest
    @MethodSource("provideJwtExceptions")
    void manejarExcepcionesJwtRetornaUnauthorized(Exception exception, String mensajeEsperado) throws Exception {
        // ARRANGE
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtService.extractUserName(anyString())).thenThrow(exception);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        // ACT
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        // ASSERT
        verify(response).setStatus(HttpStatus.UNAUTHORIZED.value());
        String responseBody = stringWriter.toString();
        assertTrue(responseBody.contains(mensajeEsperado));
    }
}