package com.coface.lesson5;

public record UsuarioCreateRequestDTO(
        String nombre,
        String email,
        String password
) {
}
