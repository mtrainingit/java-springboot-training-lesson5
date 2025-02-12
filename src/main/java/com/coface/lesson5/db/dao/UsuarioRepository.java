package com.coface.lesson5.db.dao;

import com.coface.lesson5.db.model.Tarea;
import com.coface.lesson5.db.model.Usuario;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    List<Usuario> getUsuarios();
    Optional<Usuario> getUsuarioPorId(Long id);
    Usuario saveUsuario(Usuario usuario);
    Long deleteUsuario(Long id);
    boolean existeUsuarioPorId(Long id);
    boolean existeUsuarioPorEmail(String email);

    Page<Usuario> getUsuariosPaginados(int pagina, int tamano, String ordPor, String dirOrd);

    List<Tarea> encontrarTareasPorUsuario(Usuario usuario);
}
