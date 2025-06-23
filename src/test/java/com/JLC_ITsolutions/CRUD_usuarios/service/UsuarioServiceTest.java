package com.JLC_ITsolutions.CRUD_usuarios.service;

import com.JLC_ITsolutions.CRUD_usuarios.models.UsuarioModel;
import com.JLC_ITsolutions.CRUD_usuarios.repository.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class UsuarioServiceTest {
    @Mock
    private UsuariosRepository usuariosRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }


    // Test guardar

    @Test
    void testGuardarUsuario(){
        UsuarioModel usuarioModel = new UsuarioModel(id:null,nombre:"Juan", apellido:"Bernal", correo:"juan@bernal.com", contrasenia:"j33" );
        UsuarioModel usuarioModelGuardado = new UsuarioModel(id:1s, nombre:"Juan", apellido:"Bernal", correo:"juan@bernal.com", contrasenia:"j33");
        when(usuariosRepository.save(usuarioModel)).thenReturn(usuarioModelGuardado);

        UsuarioModel resultado = usuarioService.guardarUsuario(usuarioModel);
        assertThat(resultado.getId()).isEqualTo(expected:1s);
        veryfy(usuariosRepository).save(usuarioModel);
    }
    
}
