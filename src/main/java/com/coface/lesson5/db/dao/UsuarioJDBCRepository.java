package com.coface.lesson5.db.dao;

import com.coface.lesson5.api.dto.UsuarioUpdateRequestDTO;
import com.coface.lesson5.db.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class UsuarioJDBCRepository implements UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Usuario> getUsuarios() {
        return jdbcTemplate.query(
                "select * from usuarios",
                (result, rownum) -> new Usuario(
                        result.getLong("id"),
                        result.getString("nombre"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getInt("rol")
                )
        );
    }

    @Override
    public Optional<Usuario> getUsuarioPorId(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from usuarios where id = ?",
                    (result, rownum) -> new Usuario(
                            result.getLong("id"),
                            result.getString("nombre"),
                            result.getString("email"),
                            result.getString("password"),
                            result.getInt("rol")
                    ),
                    id
            ));
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Long saveUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    (i) -> {
                        PreparedStatement ps = i.prepareStatement(
                                "insert into usuarios (id, nombre, email, password, rol) values (usuarios_id_seq.nextval, ?, ?, ?, ?)",
                                new String[]{"id"}
                        );
                        ps.setString(1, usuario.getNombre());
                        ps.setString(2, usuario.getEmail());
                        ps.setString(3, usuario.getPassword());
                        ps.setInt(4, usuario.getRol());
                        return ps;
                    },
                    keyHolder
            );
            return keyHolder.getKey().longValue();
        }
        else {
            jdbcTemplate.update(
                    "update usuarios set nombre = ?, email = ? where id = ?",
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getId()
            );
            return usuario.getId();
        }
    }

    @Override
    public Long deleteUsuario(Long id) {
        jdbcTemplate.update(
                "delete from usuarios where id = ?",
                id
        );
        return id;
    }

    @Override
    public boolean existeUsuarioPorId(Long id) {
        Long count = jdbcTemplate.queryForObject(
                "select count(*) from usuarios where id = ?",
                Long.class,
                id
        );
        return count != null && count > 0;
    }

    @Override
    public boolean existeUsuarioPorEmail(String email) {
        Long count = jdbcTemplate.queryForObject(
                "select count(*) from usuarios where email = ?",
                Long.class,
                email
        );
        return count != null && count > 0;
    }
}
