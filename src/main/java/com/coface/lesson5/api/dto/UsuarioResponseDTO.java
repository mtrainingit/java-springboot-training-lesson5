package com.coface.lesson5.api.dto;

import com.coface.lesson5.db.model.Direccion;
import com.coface.lesson5.db.model.Tarea;

import java.util.List;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String email,
        String rol,
        Direccion direccion,
        List<Tarea> tareas
) {
}
