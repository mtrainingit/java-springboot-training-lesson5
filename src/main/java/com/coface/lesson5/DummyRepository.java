package com.coface.lesson5;

import java.util.Arrays;
import java.util.List;

public class DummyRepository implements UsuarioRepository {

    @Override
    public List<Usuario> getUsuarios() {
        return Arrays.asList(
                new Usuario(1L, "José Luis Soto", "soto.joseluis@coface.com", "mimuysegurapassword", 1),
                new Usuario(2L, "Jerome Prat", "prat.jerome@coface.com", "mimuysegurapassword", 2),
                new Usuario(3L, "Raúl González", "gonzalez.raul@coface.com", "mimuysegurapassword", 2)
        );
    }
}
