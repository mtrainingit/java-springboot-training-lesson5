package com.coface.lesson5.api.controller;

import com.coface.lesson5.mapper.UsuarioAUsuarioResponseDTOMapper;
import com.coface.lesson5.api.dto.UsuarioCreateRequestDTO;
import com.coface.lesson5.api.dto.UsuarioResponseDTO;
import com.coface.lesson5.api.dto.UsuarioUpdateRequestDTO;
import com.coface.lesson5.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioAUsuarioResponseDTOMapper usuarioResponseDTOMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioAUsuarioResponseDTOMapper usuarioResponseDTOMapper) {
        this.usuarioService = usuarioService;
        this.usuarioResponseDTOMapper = usuarioResponseDTOMapper;
    }

    @GetMapping()
    public List<UsuarioResponseDTO> getUsuarios() {
        return usuarioService.getUsuarios().stream().map(usuarioResponseDTOMapper).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public UsuarioResponseDTO getUsuarioPorId(@PathVariable Long id) {
        return usuarioResponseDTOMapper.apply(usuarioService.getUsuarioPorId(id));
    }

    @PostMapping
    public ResponseEntity<Long> postUsuario(@RequestBody UsuarioCreateRequestDTO usuarioCreateRequestDTO) {
        return new ResponseEntity<>(usuarioService.crearUsusario(usuarioCreateRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Long> updateUsuario(@PathVariable Long id, @RequestBody UsuarioUpdateRequestDTO usuarioUpdateRequestDTO) {
        return new ResponseEntity<>(usuarioService.actualizarUsuario(id, usuarioUpdateRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> deleteUsuario(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.eliminarUsuario(id), HttpStatus.OK);
    }

}
