package com.coface.lesson5.service;

import com.coface.lesson5.db.model.Usuario;
import com.coface.lesson5.exception.RecursoNoEncontradoException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;

@Service("usuario")
public class UsuarioDetailService implements UserDetailsService {

    private final UsuarioService usuarioService;

    public UsuarioDetailService(
            UsuarioService usuarioService
    ) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return usuarioService.getUsuarioPorEmail(username);
        }
        catch (RecursoNoEncontradoException exception) {
            throw new UsernameNotFoundException(exception.getMessage());
        }
    }
}
