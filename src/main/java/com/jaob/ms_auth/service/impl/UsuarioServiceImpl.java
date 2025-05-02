package com.jaob.ms_auth.service.impl;

import com.jaob.ms_auth.aggregates.constants.Constantes;
import com.jaob.ms_auth.aggregates.response.ResponseBase;
import com.jaob.ms_auth.aggregates.response.UsuarioDTO;
import com.jaob.ms_auth.entity.Rol;
import com.jaob.ms_auth.entity.Usuario;
import com.jaob.ms_auth.repository.UsuarioRepository;
import com.jaob.ms_auth.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return repository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(Constantes.MESSAGE_USER_NOT_FOUND));
            }
        };
    }

    @Override
    public ResponseBase<List<UsuarioDTO>> listarSuperAdmins() {
        List<Usuario> usuarios = repository.findAllByRol(Rol.SUPERADMIN);
        List<UsuarioDTO> dtos = usuarios.stream()
                .map(this::generarUsuarioDTO)
                .toList();
        return new ResponseBase<>(
                Constantes.CODE_SUCCESSFUL,
                false,
                Constantes.MESSAGE_SUCCESSFUL,
                dtos);
    }

    @Override
    public ResponseBase<List<UsuarioDTO>> listarAdmins() {
        List<Usuario> usuarios = repository.findAllByRol(Rol.ADMIN);
        List<UsuarioDTO> dtos = usuarios.stream()
                .map(this::generarUsuarioDTO)
                .toList();
        return new ResponseBase<>(
                Constantes.CODE_SUCCESSFUL,
                false,
                Constantes.MESSAGE_SUCCESSFUL,
                dtos);
    }

    @Override
    public ResponseBase<List<UsuarioDTO>> listarUsuarios() {
        List<Usuario> usuarios = repository.findAllByRol(Rol.USUARIO);
        List<UsuarioDTO> dtos = usuarios.stream()
                .map(this::generarUsuarioDTO)
                .toList();
        return new ResponseBase<>(
                Constantes.CODE_SUCCESSFUL,
                false,
                Constantes.MESSAGE_SUCCESSFUL,
                dtos);
    }

    private UsuarioDTO generarUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .build();
    }
}
