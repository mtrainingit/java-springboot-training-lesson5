package com.coface.lesson5.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("detail-usuario")
public class UsuarioDetailService implements UserDetailsService {

    private final UsuarioService usuarioService;

    public UsuarioDetailService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioService.getUsuarioPorEmail(username);
    }
}
