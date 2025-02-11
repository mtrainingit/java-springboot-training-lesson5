package com.coface.lesson5.db.dao;

import com.coface.lesson5.db.model.Direccion;
import com.coface.lesson5.db.model.Tarea;
import com.coface.lesson5.db.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class UsuarioJDBCRepository implements UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    private final DireccionRepository direccionRepository;

    private final TareaRepository tareaRepository;

    public UsuarioJDBCRepository(
            JdbcTemplate jdbcTemplate,
            DireccionRepository direccionRepository,
            TareaRepository tareaRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.direccionRepository = direccionRepository;
        this.tareaRepository = tareaRepository;
    }

    @Override
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = jdbcTemplate.query(
                "select * from usuarios",
                (result, rownum) -> new Usuario(
                        result.getLong("id"),
                        result.getString("nombre"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getInt("rol")
                )
        );
        usuarios.stream().forEach(i -> {
            Direccion direccion;
            try {
                direccion = jdbcTemplate.queryForObject(
                        "select * from direcciones where usuario_id = ?",
                        (result, rownum) -> new Direccion(
                                result.getLong("id"),
                                result.getString("direccion"),
                                result.getString("codigo_postal"),
                                i
                        ),
                        i.getId()
                );
            }
            catch (Exception excepcion) {
                direccion = null;
            }
            i.setDireccion(direccion);
        });
        return usuarios;
    }

    @Override
    public Optional<Usuario> getUsuarioPorId(Long id) {
        Optional<Usuario> usuario;
        try {
            usuario = Optional.ofNullable(jdbcTemplate.queryForObject(
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
            usuario = Optional.empty();
        }
        usuario.ifPresent(i -> {
            Direccion direccion;
            try {
                direccion = jdbcTemplate.queryForObject(
                        "select * from direcciones where usuario_id = ?",
                        (result, rownum) -> new Direccion(
                                result.getLong("id"),
                                result.getString("direccion"),
                                result.getString("codigo_postal"),
                                i
                        ),
                        i.getId()
                );
            }
            catch (Exception excepcion) {
                direccion = null;
            }
            i.setDireccion(direccion);
        });
        return usuario;
    }

    @Transactional
    @Override
    public Usuario saveUsuario(Usuario usuario) {
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
            Long id = keyHolder.getKey().longValue();
            usuario.setId(id);
            return usuario;
        }
        else {
            direccionRepository.saveDireccion(usuario.getDireccion());
            jdbcTemplate.update(
                    "update usuarios set nombre = ?, email = ? where id = ?",
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getId()
            );
            for (Tarea tarea : usuario.getTareas()) {
                tareaRepository.saveTarea(tarea);
            }
            return usuario;
        }
    }

    @Transactional
    @Override
    public Long deleteUsuario(Long id) {
        tareaRepository.deleteTareasPorUsuarioId(id);
        direccionRepository.deleteDireccionPorUsuarioId(id);
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

    @Override
    public Page<Usuario> getUsuariosPaginados(int pagina, int tamano, String ordPor, String dirOrd) {
        Sort sort = dirOrd.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordPor).ascending() : Sort.by(ordPor).descending();
        Pageable pageable = PageRequest.of(pagina, tamano, sort);
        int offset = (int) pageable.getOffset();
        String orderBy;
        if (sort.isUnsorted()) {
            orderBy = "";
        }
        else {
            StringBuilder orderByBuilder = new StringBuilder(" order by ");
            sort.forEach(i -> {
                orderByBuilder
                        .append(i.getProperty())
                        .append(" ")
                        .append(i.isAscending() ? "asc" : "desc")
                        .append(", ");
            });
            orderBy = orderByBuilder.substring(0, orderByBuilder.length() - 2);
        }
        List<Usuario> usuarios = jdbcTemplate.query(
                "select * from usuarios " + orderBy + " offset ? rows fetch next ? rows only",
                (result, rownum) -> new Usuario(
                        result.getLong("id"),
                        result.getString("nombre"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getInt("rol")
                ),
                offset,
                tamano
        );
        usuarios.stream().forEach(i -> {
            Direccion direccion;
            try {
                direccion = jdbcTemplate.queryForObject(
                        "select * from direcciones where usuario_id = ?",
                        (result, rownum) -> new Direccion(
                                result.getLong("id"),
                                result.getString("direccion"),
                                result.getString("codigo_postal"),
                                i
                        ),
                        i.getId()
                );
            }
            catch (Exception excepcion) {
                direccion = null;
            }
            i.setDireccion(direccion);
        });
        Long count = jdbcTemplate.queryForObject(
                "select count(*) from usuarios",
                Long.class
        );
        return new PageImpl<>(usuarios, pageable, count);
    }
}
