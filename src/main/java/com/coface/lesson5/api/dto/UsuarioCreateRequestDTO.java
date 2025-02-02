package com.coface.lesson5.api.dto;

public record UsuarioCreateRequestDTO(
        String nombre,
        String email,
        String password
) {
}
