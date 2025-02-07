package com.coface.lesson5.db.dao;

import com.coface.lesson5.db.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaJPARepository extends JpaRepository<Tarea,Long> {
}
