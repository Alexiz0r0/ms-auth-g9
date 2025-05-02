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
import com.jaob.ms_auth.service.AuthenticationService;
import com.jaob.ms_auth.service.JwtService;
import com.jaob.ms_auth.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager manager;
    private final UsuarioRepository repository;
    private final JwtService jwtService;
    private final UsuarioService service;


    @Override
    public ResponseBase<UsuarioDTO> registrar(UsuarioSignUpRequest request) {
        Usuario usuario = generarUsuarioWithPasswordEncoder(request);
        Usuario usuario1 = repository.save(usuario);
        return new ResponseBase<>(
                Constantes.CODE_CREATED,
                false,
                Constantes.MESSAGE_CREATED,
                generarUsuarioDTO(usuario1));
    }


    @Override
    public ResponseBase<SignInResponse> login(UsuarioSignInRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()));
        Usuario usuario = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(Constantes.MESSAGE_USER_NOT_FOUND));
        UserDetails userDetails = service.userDetailsService().loadUserByUsername(usuario.getUsername());
        String token = jwtService.generarToken(userDetails, usuario);
        return new ResponseBase<>(
                Constantes.CODE_SUCCESSFUL,
                false,
                Constantes.MESSAGE_SUCCESSFUL,
                generateSignInResponse(usuario, token));
    }

    @Override
    public ResponseBase<UsuarioDTO> validate(String token) {
        if (!token.startsWith("Bearer ")) {
            throw new InputMismatchException(Constantes.MESSAGE_ILLEGAL_TOKEN);
        }
        String tokenWithoutBearer = token.replace("Bearer ", "");
        String userEmail = jwtService.extractUserName(tokenWithoutBearer);
        Usuario usuario = repository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(Constantes.MESSAGE_USER_NOT_FOUND));
        return new ResponseBase<>(
                Constantes.CODE_SUCCESSFUL,
                false,
                Constantes.MESSAGE_SUCCESSFUL,
                generarUsuarioDTO(usuario));
    }

    private Usuario generarUsuarioWithPasswordEncoder(UsuarioSignUpRequest request) {
        return Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .rol(Rol.fromStringToRol(request.getRol()))
                .build();
    }

    private UsuarioDTO generarUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .build();
    }

    private SignInResponse generateSignInResponse(Usuario usuario, String token) {
        return SignInResponse.builder()
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .token(token)
                .build();
    }
}
