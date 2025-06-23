package com.JLC_ITsolutions.CRUD_usuarios.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JLC_ITsolutions.CRUD_usuarios.models.UsuarioModel;

public interface UsuariosRepository extends JpaRepository<UsuarioModel,Integer> {
    Optional<UsuarioModel>findBycorreo(String correo);

    Optional<UsuarioModel> findByid(Integer id);


}
