package com.coface.lesson5.db.dao;

import com.coface.lesson5.db.model.Usuario;
import com.coface.lesson5.db.model.UsuarioReducidoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioJPARepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    // JPQL
    @Query("select new com.coface.lesson5.db.model.UsuarioReducidoDTO(u.nombre, u.email) from Usuario u")
    List<UsuarioReducidoDTO> findUsuariosReducidosJPQL();

    // Native query
    @Query(value = "select nombre, email from usuarios", nativeQuery = true)
    List<UsuarioReducidoDTO> findUsuariosReducidosNativeQuery();

    /*
     * funcional
     * (si
     *     1 - oracle tiene configurada la ejecución por nombre sin call
     *     2 - la función tiene el mismo nombre del stored procedure al que se llama
     * )
     */
    @Procedure(
            name = "insertar_usuario"
    )
    Long insertar_usuario(
            @Param("p_nombre") String nombre,
            @Param("p_email") String email,
            @Param("p_password") String password,
            @Param("p_rol") Integer rol
    );

    /*
     * funcional
     * (pero no apto para traer el parámetro de salida de vuelta)
     */
    @Query(
            value = "call insertar_usuario(:p_nombre, :p_email, :p_password, :p_rol, :p_id)",
            nativeQuery = true
    )
    void insertarUsuarioQuery(
            @Param("p_nombre") String nombre,
            @Param("p_email") String email,
            @Param("p_password") String password,
            @Param("p_rol") Integer rol,
            @Param("p_id") Long id
    );

    default Long insertarUsuarioStoredProcedureQuery(EntityManager em, Usuario usuario) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("insertar_usuario");
        query.registerStoredProcedureParameter("p_nombre", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_rol", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.OUT);

        query.setParameter("p_nombre", usuario.getNombre());
        query.setParameter("p_email", usuario.getEmail());
        query.setParameter("p_password", usuario.getPassword());
        query.setParameter("p_rol", usuario.getRol());

        query.execute();

        return (Long) query.getOutputParameterValue("p_id");
    }
}
