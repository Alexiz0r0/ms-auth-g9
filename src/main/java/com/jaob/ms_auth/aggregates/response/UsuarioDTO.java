package com.jaob.ms_auth.aggregates.response;

import com.jaob.ms_auth.entity.Rol;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDTO {
    Long id;
    private String nombre;
    private String email;
    private Rol rol;
}
