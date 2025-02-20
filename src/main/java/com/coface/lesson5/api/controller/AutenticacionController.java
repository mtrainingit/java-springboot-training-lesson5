package com.coface.lesson5.api.controller;

import com.coface.lesson5.api.dto.LoginDTO;
import com.coface.lesson5.db.model.Usuario;
import com.coface.lesson5.service.AutenticacionService;
import com.coface.lesson5.utils.JWTUtility;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/autenticacion")
public class AutenticacionController {

    private final AutenticacionService autenticacionService;
    private final JWTUtility jwtUtility;

    public AutenticacionController(AutenticacionService autenticacionService, JWTUtility jwtUtility) {
        this.autenticacionService = autenticacionService;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Usuario usuario = autenticacionService.login(loginDTO.username(), loginDTO.password());
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        jwtUtility.expedirToken(
                                usuario.getId().toString(),
                                usuario.getUsername(),
                                Map.of("scopes", usuario.getAuthorities())
                        )
                )
                .body(usuario.getId());
    }
}
