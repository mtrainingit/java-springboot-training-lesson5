package com.coface.lesson5.service;

import com.coface.lesson5.api.dto.UsuarioCreateRequestDTO;
import com.coface.lesson5.api.dto.UsuarioResponseDTO;
import com.coface.lesson5.api.dto.UsuarioUpdateRequestDTO;
import com.coface.lesson5.db.dao.UsuarioRepository;
import com.coface.lesson5.db.model.Direccion;
import com.coface.lesson5.db.model.Tarea;
import com.coface.lesson5.db.model.Usuario;
import com.coface.lesson5.db.model.UsuarioReducidoDTO;
import com.coface.lesson5.exception.ConflictoCampoUnicoException;
import com.coface.lesson5.exception.RecursoNoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            @Qualifier("usuario-jpa") UsuarioRepository usuarioRepository,
            @Qualifier("bcrypt") PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.getUsuarios();
    }

    public Usuario getUsuarioPorId(Long id) {
        return usuarioRepository.getUsuarioPorId(id).orElseThrow(() -> new RecursoNoEncontradoException("No se pudo encontrar un usuario con el id " + id));
    }

    public Usuario getUsuarioPorEmail(String email) {
        return usuarioRepository.getUsuarioPorEmail(email).orElseThrow(() -> new RecursoNoEncontradoException("No se pudo encontrar un usuario con el email " + email));
    }

    @Transactional
    public Long crearUsusario(UsuarioCreateRequestDTO usuarioCreateRequestDTO) {
        if (usuarioRepository.existeUsuarioPorEmail(usuarioCreateRequestDTO.email())) {
            throw new ConflictoCampoUnicoException("El email " + usuarioCreateRequestDTO.email() + " ya existe");
        }
        String password = passwordEncoder.encode(usuarioCreateRequestDTO.password());
        Usuario usuario = usuarioRepository.saveUsuario(new Usuario(
                usuarioCreateRequestDTO.nombre(),
                usuarioCreateRequestDTO.email(),
                password,
                2
        ));
        usuario.setDireccion(new Direccion(
                usuarioCreateRequestDTO.direccion(),
                usuarioCreateRequestDTO.codigoPostal(),
                usuario
        ));
        usuarioRepository.saveUsuario(usuario);
        return usuario.getId();
    }

    @Transactional
    public Long actualizarUsuario(Long id, UsuarioUpdateRequestDTO usuarioUpdateRequestDTO) {
        Usuario usuario = usuarioRepository.getUsuarioPorId(id).orElseThrow(() -> new RecursoNoEncontradoException("No se pudo encontrar un usuario con id " + id));
        if (usuarioUpdateRequestDTO.nombre() != null && !usuarioUpdateRequestDTO.nombre().isEmpty()) {
            usuario.setNombre(usuarioUpdateRequestDTO.nombre());
        }
        if (usuarioUpdateRequestDTO.email() != null && !usuarioUpdateRequestDTO.email().isEmpty()) {
            if (usuarioRepository.existeUsuarioPorEmail(usuarioUpdateRequestDTO.email())) {
                throw new ConflictoCampoUnicoException("El email " + usuarioUpdateRequestDTO.email() + " ya existe");
            }
            usuario.setEmail(usuarioUpdateRequestDTO.email());
        }
        return usuarioRepository.saveUsuario(usuario).getId();
    }

    @Transactional
    public Long eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.getUsuarioPorId(id).orElseThrow(() -> new RecursoNoEncontradoException("No se pudo encontrar un usuario con id " + id));
        return usuarioRepository.deleteUsuario(id);
    }

    public Page<Usuario> getUsuariosPaginados(int pagina, int tamano, String ordPor, String dirOrd) {
        return usuarioRepository.getUsuariosPaginados(pagina, tamano, ordPor, dirOrd);
    }

    @Transactional
    public Long asignarTarea(Long id, Tarea tarea) {
        Usuario usuario = usuarioRepository.getUsuarioPorId(id).orElseThrow(() -> new RecursoNoEncontradoException("No se pudo encontrar un usuario con id " + id));
        usuario.setTareas(usuarioRepository.encontrarTareasPorUsuario(usuario));
        tarea.setUsuario(usuario);
        usuario.asignaTarea(tarea);
        return usuarioRepository.saveUsuario(usuario).getId();
    }

    @Transactional
    public Usuario getTareasDeUsuario(Long id) {
        Usuario usuario = usuarioRepository.getUsuarioPorId(id).orElseThrow(() -> new RecursoNoEncontradoException("No se pudo encontrar un usuario con id " + id));
        usuario.setTareas(usuarioRepository.encontrarTareasPorUsuario(usuario));
        return usuario;
    }

    public List<UsuarioReducidoDTO> getUsuariosReducidos() {
        return usuarioRepository.getUsuariosReducidos();
    }
}
