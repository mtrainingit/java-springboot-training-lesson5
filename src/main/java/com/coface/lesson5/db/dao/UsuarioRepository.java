package com.coface.lesson5.db.dao;

import com.coface.lesson5.db.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    List<Usuario> getUsuarios();
    Optional<Usuario> getUsuarioPorId(Long id);
    Long saveUsuario(Usuario usuario);
    Long deleteUsuario(Long id);
}
