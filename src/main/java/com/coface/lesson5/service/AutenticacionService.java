package com.coface.lesson5.service;

import com.coface.lesson5.db.model.Usuario;
import com.coface.lesson5.mapper.UsuarioAUsuarioResponseDTOMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService {

    private final AuthenticationManager authenticationManager;

    public AutenticacionService(
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
    }

    public Usuario login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        return (Usuario) authentication.getPrincipal();
    }

}
