package com.coface.lesson5;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UsuarioAUsuarioResponseDTOMapper implements Function<Usuario, UsuarioResponseDTO> {

    @Override
    public UsuarioResponseDTO apply(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol() == 1 ? "ROLE_ADMIN" : "ROLE_USER"
        );
    }
}
