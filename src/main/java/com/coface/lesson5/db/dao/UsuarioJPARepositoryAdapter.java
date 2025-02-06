package com.coface.lesson5.db.dao;

import com.coface.lesson5.api.dto.UsuarioUpdateRequestDTO;
import com.coface.lesson5.db.model.Usuario;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class UsuarioJPARepositoryAdapter implements UsuarioRepository {

    private final UsuarioJPARepository usuarioJPARepository;

    public UsuarioJPARepositoryAdapter(UsuarioJPARepository usuarioJPARepository) {
        this.usuarioJPARepository = usuarioJPARepository;
    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioJPARepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioPorId(Long id) {
        return usuarioJPARepository.findById(id);
    }

    @Override
    public Long saveUsuario(Usuario usuario) {
        return usuarioJPARepository.save(usuario).getId();
    }

    @Override
    public Long deleteUsuario(Long id) {
        usuarioJPARepository.deleteById(id);
        return id;
    }

    @Override
    public boolean existeUsuarioPorId(Long id) {
        return usuarioJPARepository.existsById(id);
    }

    @Override
    public boolean existeUsuarioPorEmail(String email) {
        return usuarioJPARepository.existsByEmail(email);
    }
}
