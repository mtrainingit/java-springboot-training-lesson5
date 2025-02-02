package com.coface.lesson5.db.dao;

import com.coface.lesson5.db.model.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DummyRepository implements UsuarioRepository {

    private List<Usuario> usuarios;

    public DummyRepository() {
        this.usuarios = new ArrayList<>(Arrays.asList(
                new Usuario(1L, "José Luis Soto", "soto.joseluis@coface.com", "mimuysegurapassword", 1),
                new Usuario(2L, "Jerome Prat", "prat.jerome@coface.com", "mimuysegurapassword", 2),
                new Usuario(3L, "Raúl González", "gonzalez.raul@coface.com", "mimuysegurapassword", 2)
        ));
    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    @Override
    public Optional<Usuario> getUsuarioPorId(Long id) {
        return usuarios.stream().filter(u -> u.getId() == id).findFirst();
    }

    @Override
    public Long saveUsuario(Usuario usuario) {
        Long id;
        if (usuario.getId() == null) {
            Optional<Usuario> usuarioConMayorId = usuarios.stream().max((u1, u2) -> Long.compare(u1.getId(), u2.getId()));
            id = usuarioConMayorId.isPresent() ? usuarioConMayorId.get().getId() + 1 : 1L;
            usuario.setId(id);
            usuarios.add(usuario);
        }
        else {
            Usuario usuarioToModify = usuarios.stream().filter(u -> u.getId() == usuario.getId()).findFirst().get();
            usuarioToModify = usuario;
            id = usuario.getId();
        }
        return id;
    }

    @Override
    public Long deleteUsuario(Long id) {
        usuarios.remove(usuarios.stream().filter(u -> u.getId() == id).findFirst().get());;
        return id;
    }

}
