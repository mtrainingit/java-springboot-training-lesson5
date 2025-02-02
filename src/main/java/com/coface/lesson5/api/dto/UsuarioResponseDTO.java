package com.coface.lesson5.api.dto;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String email,
        String rol
) {
}
