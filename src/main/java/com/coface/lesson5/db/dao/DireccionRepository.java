package com.coface.lesson5.db.dao;

import com.coface.lesson5.db.model.Direccion;

public interface DireccionRepository {
    Direccion saveDireccion(Direccion direccion);
    Long deleteDireccionPorUsuarioId(Long id);
}
