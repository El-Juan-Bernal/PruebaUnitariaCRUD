package com.JLC_ITsolutions.CRUD_usuarios.controller;

import com.JLC_ITsolutions.CRUD_usuarios.DTO.CuentaDTO;
import com.JLC_ITsolutions.CRUD_usuarios.models.UsuarioModel;
import com.JLC_ITsolutions.CRUD_usuarios.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCuenta() {
        CuentaDTO dto = new CuentaDTO();
        UsuarioModel usuario = new UsuarioModel(1, "Juan", "Bernal", "juan@correo.com", "123");

        when(usuarioService.crearUsuario(dto)).thenReturn(usuario);

        ResponseEntity<UsuarioModel> response = usuarioController.crearCuenta(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(usuario);
    }

    @Test
    void testEliminarUsuario() {
        Integer id = 1;

        ResponseEntity<Void> response = usuarioController.eliminarusuario(id);

        verify(usuarioService).EliminarUsuario(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void testListarUsuarios() {
        List<UsuarioModel> usuarios = Arrays.asList(
                new UsuarioModel(1, "Juan", "Bernal", "juan@correo.com", "123"),
                new UsuarioModel(2, "Abel", "Alarcon", "abel@alarcon.com", "987")
        );

        when(usuarioService.listarUsuarios()).thenReturn(usuarios);

        List<UsuarioModel> response = usuarioController.listarUsuarios();

        assertThat(response).isEqualTo(usuarios);
    }

    @Test
    void testBuscarIdEncontrado() {
        Integer id = 1;
        UsuarioModel usuario = new UsuarioModel(id, "Killua", "Zoldick", "killua@zoldick.com", "kill33");

        when(usuarioService.buscarid(id)).thenReturn(Optional.of(usuario));

        ResponseEntity<UsuarioModel> response = usuarioController.buscarid(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(usuario);
    }

    @Test
    void testBuscarIdNoEncontrado() {
        Integer id = 1;
        when(usuarioService.buscarid(id)).thenReturn(Optional.empty());

        ResponseEntity<UsuarioModel> response = usuarioController.buscarid(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testModificarUsuario() {
        Integer id = 1;
        CuentaDTO dto = new CuentaDTO();
        UsuarioModel modificado = new UsuarioModel(id, "Gon", "Freec", "gon@freec.com", "elGon");

        when(usuarioService.modificarUsuario(id, dto)).thenReturn(modificado);

        ResponseEntity<UsuarioModel> response = usuarioController.modificarUsuario(id, dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(modificado);
    }

    @Test
    void testIniciarSesionExitosa() {
        String correo = "micorreo@mio.com";
        String contrasenia = "123";

        when(usuarioService.iniciarSesion(correo, contrasenia)).thenReturn(true);

        ResponseEntity<String> response = usuarioController.iniciarSesion(correo, contrasenia);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Sesión iniciada");
    }

    @Test
    void testIniciarSesionFallida() {
        String correo = "micorreo2@mio.com";
        String contrasenia = "incorrecta";

        when(usuarioService.iniciarSesion(correo, contrasenia)).thenReturn(false);

        ResponseEntity<String> response = usuarioController.iniciarSesion(correo, contrasenia);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isEqualTo("Error al iniciar la sesión");
    }
}

