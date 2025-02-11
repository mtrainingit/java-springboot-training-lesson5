package com.coface.lesson5.db.dao;

import com.coface.lesson5.db.model.Tarea;
import com.coface.lesson5.db.model.Usuario;

import java.util.List;

public interface TareaRepository {

    Tarea saveTarea(Tarea tarea);

    List<Tarea> encontrarTareasPorUsuario(Usuario usario);

    Long deleteTareasPorUsuarioId(Long id);
}
