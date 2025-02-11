package com.coface.lesson5.db.dao;

import com.coface.lesson5.db.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioJPARepository.save(usuario);
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

    @Override
    public Page<Usuario> getUsuariosPaginados(int pagina, int tamano, String ordPor, String dirOrd) {
        Sort sort = dirOrd.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordPor).ascending() : Sort.by(ordPor).descending();
        Pageable pageable = PageRequest.of(pagina, tamano, sort);
        return usuarioJPARepository.findAll(pageable);
    }
}
