package com.coface.lesson5.db.dao;

import com.coface.lesson5.db.model.Direccion;
import com.coface.lesson5.db.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DireccionDummyRepository implements DireccionRepository {

    private List<Direccion> direcciones;

    public DireccionDummyRepository() {
        direcciones = new ArrayList<>();
    }

    @Override
    public Direccion saveDireccion(Direccion direccion) {
        if (direccion.getId() == null) {
            Optional<Direccion> direccionConMayorId = direcciones.stream().max((d1, d2) -> Long.compare(d1.getId(), d2.getId()));
            Long id = direccionConMayorId.isPresent() ? direccionConMayorId.get().getId() + 1 : 1L;
            direccion.setId(id);
            direcciones.add(direccion);
        }
        else {
            Direccion direccionToModify = direcciones.stream().filter(d -> d.getId() == direccion.getId()).findFirst().get();
            direccionToModify = direccion;
        }
        return direccion;
    }

    @Override
    public Long deleteDireccionPorUsuarioId(Long id) {
        direcciones.removeIf(direccion -> direccion.getUsuario().getId() == id);
        return id;
    }
}
