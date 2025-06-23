package com.JLC_ITsolutions.CRUD_usuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JLC_ITsolutions.CRUD_usuarios.DTO.CuentaDTO;
import com.JLC_ITsolutions.CRUD_usuarios.models.UsuarioModel;
import com.JLC_ITsolutions.CRUD_usuarios.repository.UsuariosRepository;

@Service
public class UsuarioService {
    @Autowired

    private UsuariosRepository repository;
    public UsuarioModel crearUsuario(CuentaDTO cuentadto){
        UsuarioModel usuario=new UsuarioModel();
        usuario.setNombre(cuentadto.getNombre());

        usuario.setApellido(cuentadto.getApellido());

        usuario.setCorreo(cuentadto.getCorreo());

        usuario.setContrasenia(cuentadto.getContrasenia());
        return repository.save(usuario);
    }

    public void EliminarUsuario(Integer id){
        repository.deleteById(id);
    }

    public List<UsuarioModel>listarUsuarios(){
        return repository.findAll();
    }

    public Optional<UsuarioModel>buscarid(Integer id){
        return repository.findById(id);
    }

    public UsuarioModel modificarUsuario(Integer id, CuentaDTO dto){
        UsuarioModel usuario=repository.findByid(id).orElseThrow();
        usuario.setNombre(dto.getNombre());

        usuario.setApellido(dto.getApellido());

        usuario.setCorreo(dto.getCorreo());

        usuario.setContrasenia(dto.getContrasenia());

        return repository.save(usuario);
    }

    public boolean iniciarSesion(String correo, String contrasenia){
        Optional<UsuarioModel> usuario=repository.findBycorreo(correo);

        return usuario.isPresent()&& usuario.get().getContrasenia().equals(contrasenia);
    }

}
