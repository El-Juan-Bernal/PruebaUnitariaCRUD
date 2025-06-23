package com.JLC_ITsolutions.CRUD_usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JLC_ITsolutions.CRUD_usuarios.DTO.CuentaDTO;
import com.JLC_ITsolutions.CRUD_usuarios.models.UsuarioModel;
import com.JLC_ITsolutions.CRUD_usuarios.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuario")

public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioModel> crearCuenta(@RequestBody @Valid CuentaDTO dto) {
        UsuarioModel creada = service.crearUsuario(dto);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    
    @DeleteMapping ("/id")
    public ResponseEntity<Void>eliminarusuario(@PathVariable Integer id){
        service.EliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping 
    public List<UsuarioModel>listarUsuarios(){
        return service.listarUsuarios();
    }

    @GetMapping("/buscar")
    public ResponseEntity<UsuarioModel> buscarid(@PathVariable Integer id){
        return service.buscarid(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    @PutMapping ("/id")
    public ResponseEntity<UsuarioModel>modificarUsuario(@PathVariable Integer id, @Valid @RequestBody CuentaDTO dto){
        return ResponseEntity.ok(service.modificarUsuario(id, dto));
    }

    @PostMapping("/iniciarSesion")
    public ResponseEntity<String> iniciarSesion(@RequestParam String correo, @RequestParam String contrasenia){
        boolean valido=service.iniciarSesion(correo, contrasenia);
        return valido ? ResponseEntity.ok("Sesión iniciada")
        :ResponseEntity.status(401).body("Error al iniciar la sesión");
    }



}

