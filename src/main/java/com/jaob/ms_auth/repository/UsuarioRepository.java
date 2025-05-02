package com.jaob.ms_auth.repository;

import com.jaob.ms_auth.entity.Rol;
import com.jaob.ms_auth.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol")
    List<Usuario> findAllByRol(@Param("rol") Rol rol);
}
