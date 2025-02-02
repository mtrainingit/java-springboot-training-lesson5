package com.coface.lesson5.mapper;

import com.coface.lesson5.db.model.Usuario;
import com.coface.lesson5.api.dto.UsuarioResponseDTO;
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
