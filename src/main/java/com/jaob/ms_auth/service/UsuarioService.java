package com.jaob.ms_auth.service;

import com.jaob.ms_auth.aggregates.response.ResponseBase;
import com.jaob.ms_auth.aggregates.response.UsuarioDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioService {

    UserDetailsService userDetailsService();

    ResponseBase<List<UsuarioDTO>> listarSuperAdmins();

    ResponseBase<List<UsuarioDTO>> listarAdmins();

    ResponseBase<List<UsuarioDTO>> listarUsuarios();
}
