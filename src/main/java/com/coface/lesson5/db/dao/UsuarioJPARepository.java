package com.coface.lesson5.db.dao;

import com.coface.lesson5.api.dto.UsuarioUpdateRequestDTO;
import com.coface.lesson5.db.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioJPARepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    // JPQL
    @Query("select u from Usuario u where u.rol = :rol")
    Optional<Usuario> findByRol1(Integer rol);

    // Native query
    @Query(value = "select nombre, email from usuarios where id = 1", nativeQuery = true)
    Optional<UsuarioUpdateRequestDTO> findByRol2();
}
