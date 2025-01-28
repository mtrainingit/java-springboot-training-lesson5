package com.coface.lesson5;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.getUsuarios();
    }

    public List<Usuario> getUsuariosQueEmpiezanConJ() {
        return usuarioRepository.getUsuarios().stream().filter((i) -> i.getNombre().startsWith("J")).collect(Collectors.toList());
    }
}
