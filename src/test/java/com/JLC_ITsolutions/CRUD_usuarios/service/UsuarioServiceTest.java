package com.JLC_ITsolutions.CRUD_usuarios.service;

import com.JLC_ITsolutions.CRUD_usuarios.DTO.CuentaDTO;
import com.JLC_ITsolutions.CRUD_usuarios.models.UsuarioModel;
import com.JLC_ITsolutions.CRUD_usuarios.repository.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuariosRepository repository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

        // Crear cuenta
    @Test
    void testCrearUsuario() {
        CuentaDTO dto = new CuentaDTO();
        dto.setNombre("Juan");
        dto.setApellido("Bernal");
        dto.setCorreo("juan@bernal.com");
        dto.setContrasenia("123");

        UsuarioModel usuarioGuardado = new UsuarioModel(1, "Juan", "Bernal", "juan@bernal.com", "123");

        when(repository.save(any(UsuarioModel.class))).thenReturn(usuarioGuardado);

        UsuarioModel result = usuarioService.crearUsuario(dto);

        assertThat(result).isEqualTo(usuarioGuardado);
        verify(repository).save(any(UsuarioModel.class));
    }

        // Elimiar un usuario
    @Test
    void testEliminarUsuario() {
        Integer id = 1;

        usuarioService.EliminarUsuario(id);

        verify(repository).deleteById(id);
    }

        // Listar usuarios
    @Test
    void testListarUsuarios() {
        List<UsuarioModel> usuarios = Arrays.asList(
                new UsuarioModel(1, "Abel", "Alarcon", "abel@alarcon.com", "987"),
                new UsuarioModel(2, "Nicolas", "Ceballos", "niko@ceballos.com", "12345")
        );

        when(repository.findAll()).thenReturn(usuarios);

        List<UsuarioModel> result = usuarioService.listarUsuarios();

        assertThat(result).isEqualTo(usuarios);
    }

            // Buscar usuario
    @Test
    void testBuscarIdExistente() {
        Integer id = 1;
        UsuarioModel usuario = new UsuarioModel(id, "Sebastian", "Gonzalez", "seba@gonzalez.com", "elSeba");

        when(repository.findById(id)).thenReturn(Optional.of(usuario));

        Optional<UsuarioModel> result = usuarioService.buscarid(id);

        assertThat(result).isPresent().contains(usuario);
    }

    @Test
    void testBuscarIdInexistente() {
        Integer id = 999;

        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<UsuarioModel> result = usuarioService.buscarid(id);

        assertThat(result).isEmpty();
    }

            // Modificar usuario
    @Test
    void testModificarUsuarioExistente() {
        Integer id = 1;
        CuentaDTO dto = new CuentaDTO();
        dto.setNombre("Sara");
        dto.setApellido("Alarcon");
        dto.setCorreo("sara@alarcon.com");
        dto.setContrasenia("Sra123");

        UsuarioModel existente = new UsuarioModel(id, "Antiguo", "Nombre", "correo@viejo.com", "viejo");
        UsuarioModel actualizado = new UsuarioModel(id, "Sara", "Alarcon", "sara@alarcon.com", "Sra123");

        when(repository.findByid(id)).thenReturn(Optional.of(existente));
        when(repository.save(any(UsuarioModel.class))).thenReturn(actualizado);

        UsuarioModel result = usuarioService.modificarUsuario(id, dto);

        assertThat(result).isEqualTo(actualizado);
        verify(repository).save(existente);
    }

    @Test
    void testModificarUsuarioNoExistente() {
        Integer id = 404;
        CuentaDTO dto = new CuentaDTO();
        dto.setNombre("X");
        dto.setApellido("Y");

        when(repository.findByid(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> usuarioService.modificarUsuario(id, dto));
    }

            // Iniciar sesión
    @Test
    void testIniciarSesionCorrecta() {
        String correo = "usuario@correo.com";
        String contrasenia = "clave123";

        UsuarioModel usuario = new UsuarioModel(1, "User", "Demo", correo, contrasenia);

        when(repository.findBycorreo(correo)).thenReturn(Optional.of(usuario));

        boolean result = usuarioService.iniciarSesion(correo, contrasenia);

        assertThat(result).isTrue();
    }

    @Test
    void testIniciarSesionContraseniaIncorrecta() {
        String correo = "usuario@correo.com";
        String contrasenia = "incorrecta";

        UsuarioModel usuario = new UsuarioModel(1, "User", "Demo", correo, "claveReal");

        when(repository.findBycorreo(correo)).thenReturn(Optional.of(usuario));

        boolean result = usuarioService.iniciarSesion(correo, contrasenia);

        assertThat(result).isFalse();
    }

    @Test
    void testIniciarSesionCorreoInexistente() {
        String correo = "noexiste@correo.com";
        String contrasenia = "cualquier";

        when(repository.findBycorreo(correo)).thenReturn(Optional.empty());

        boolean result = usuarioService.iniciarSesion(correo, contrasenia);

        assertThat(result).isFalse();
    }
}

