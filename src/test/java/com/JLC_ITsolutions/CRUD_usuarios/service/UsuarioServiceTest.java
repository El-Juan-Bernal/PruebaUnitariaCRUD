package com.JLC_ITsolutions.CRUD_usuarios.service;

import com.JLC_ITsolutions.CRUD_usuarios.DTO.CuentaDTO;
import com.JLC_ITsolutions.CRUD_usuarios.models.UsuarioModel;
import com.JLC_ITsolutions.CRUD_usuarios.repository.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UsuarioServiceTest {
    @Mock
    private UsuariosRepository usuariosRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Crear usuario
    @Test
    void testCrearUsuario() {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setNombre("Juan");
        cuentaDTO.setApellido("Bernal");
        cuentaDTO.setCorreo("juan@bernal.com");
        cuentaDTO.setContrasenia("j33");
        UsuarioModel usuariomodel = new UsuarioModel(1, "Juan", "Bernal", "juan@bernal.com", "j33");
        when(usuariosRepository.save(any(UsuarioModel.class))).thenReturn(usuariomodel);
        UsuarioModel usuarioguardado = usuarioService.crearUsuario(cuentaDTO);
        assertThat(usuarioguardado.getId()).isEqualTo(1);
        assertThat(usuarioguardado.getCorreo()).isEqualTo("juan@bernal.com");
        verify(usuariosRepository).save(any(UsuarioModel.class));
    }

    // Listar usuarios
    @Test
    void testListarUsuarios() {
        UsuarioModel u1 = new UsuarioModel(1, "Juan", "Bernal", "juan@bernal.com", "j33");
        UsuarioModel u2 = new UsuarioModel(2, "Abel", "Alarcón", "abel@alarcon.com", "a33");
        when(usuariosRepository.findAll()).thenReturn(Arrays.asList(u1, u2));
        List<UsuarioModel> resultado = usuarioService.listarUsuarios();
        assertThat(resultado).hasSize(2).contains(u1, u2);
        verify(usuariosRepository).findAll();
    }

    // Buscar usuario por Id
    @Test
    void testBuscarPorId() {
        UsuarioModel usuario = new UsuarioModel(1, "Juan", "Bernal", "juan@bernal.com", "j33");
        when(usuariosRepository.findById(1)).thenReturn(Optional.of(usuario));
        Optional<UsuarioModel> resultado = usuarioService.buscarid(1);
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCorreo()).isEqualTo("juan@bernal.com");
        verify(usuariosRepository).findById(1);
    }
    
}
