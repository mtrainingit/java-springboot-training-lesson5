package com.coface.lesson5;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            @Qualifier("bcrypt") PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.getUsuarios();
    }

    public Usuario getUsuarioPorId(Long id) {
        return usuarioRepository.getUsuarioPorId(id).orElseThrow(() -> new RecursoNoEncontradoException("No se pudo encontrar un usuario con el id " + id));
    }

    public Long crearUsusario(UsuarioCreateRequestDTO usuarioCreateRequestDTO) {
        String password = passwordEncoder.encode(usuarioCreateRequestDTO.password());
        return usuarioRepository.saveUsuario(new Usuario(
                usuarioCreateRequestDTO.nombre(),
                usuarioCreateRequestDTO.email(),
                password,
                2
        ));
    }

    public Long actualizarUsuario(Long id, UsuarioUpdateRequestDTO usuarioUpdateRequestDTO) {
        Usuario usuario = usuarioRepository.getUsuarioPorId(id).orElseThrow(() -> new RecursoNoEncontradoException("No se pudo encontrar un usuario con id " + id));
        if (usuarioUpdateRequestDTO.nombre() != null && !usuarioUpdateRequestDTO.nombre().isEmpty()) {
            usuario.setNombre(usuarioUpdateRequestDTO.nombre());
        }
        if (usuarioUpdateRequestDTO.email() != null && !usuarioUpdateRequestDTO.email().isEmpty()) {
            usuario.setEmail(usuarioUpdateRequestDTO.email());
        }
        return usuarioRepository.saveUsuario(usuario);
    }

    public Long eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.getUsuarioPorId(id).orElseThrow(() -> new RecursoNoEncontradoException("No se pudo encontrar un usuario con id " + id));
        return usuarioRepository.deleteUsuario(id);
    }
}
