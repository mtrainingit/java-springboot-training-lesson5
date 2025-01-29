package com.coface.lesson5;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String email,
        String rol
) {
}
