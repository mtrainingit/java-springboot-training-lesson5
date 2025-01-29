package com.coface.lesson5;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    List<Usuario> getUsuarios();
    Optional<Usuario> getUsuarioPorId(Long id);
    Long saveUsuario(Usuario usuario);
    Long deleteUsuario(Long id);
}
