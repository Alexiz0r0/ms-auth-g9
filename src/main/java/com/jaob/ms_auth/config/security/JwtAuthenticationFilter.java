package com.jaob.ms_auth.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaob.ms_auth.aggregates.constants.Constantes;
import com.jaob.ms_auth.aggregates.response.ResponseBase;
import com.jaob.ms_auth.service.JwtService;
import com.jaob.ms_auth.service.UsuarioService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String tokenHeader = request.getHeader("Authorization");
        final String tokenWithoutBearer;
        final String userEmail;

        try {

            if (!StringUtils.hasText(tokenHeader) || !StringUtils.startsWithIgnoreCase(tokenHeader, "Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            tokenWithoutBearer = tokenHeader.substring(7);
            userEmail = jwtService.extractUserName(tokenWithoutBearer);

            if (Objects.nonNull(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                UserDetails userDetails = service.userDetailsService().loadUserByUsername(userEmail);

                if (jwtService.validarToken(tokenWithoutBearer, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(authenticationToken);
                    SecurityContextHolder.setContext(securityContext);
                }
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            handleJwtException(response, Constantes.MESSAGE_EXPIRED_TOKEN);
        } catch (MalformedJwtException e) {
            handleJwtException(response, Constantes.MESSAGE_INVALID_TOKEN);
        } catch (SignatureException e) {
            handleJwtException(response, Constantes.MESSAGE_SIGNATURE_INVALID_TOKEN);
        } catch (UnsupportedJwtException e) {
            handleJwtException(response, Constantes.MESSAGE_UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            handleJwtException(response, Constantes.MESSAGE_ILLEGAL_TOKEN);
        }
    }

    private void handleJwtException(HttpServletResponse response, String message) throws IOException {
        ResponseBase<String> customResponse = new ResponseBase<>(
                Constantes.CODE_UNAUTHORIZED,
                true,
                message,
                null);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), customResponse);
    }


}
