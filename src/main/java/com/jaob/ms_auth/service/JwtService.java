package com.jaob.ms_auth.service;

import com.jaob.ms_auth.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUserName(String token);

    String generarToken(UserDetails userDetails, Usuario usuario);

    boolean validarToken(String token, UserDetails userDetails);


}
