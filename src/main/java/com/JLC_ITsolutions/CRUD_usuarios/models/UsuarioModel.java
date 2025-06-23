package com.JLC_ITsolutions.CRUD_usuarios.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuentas")

public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @NotBlank
    // @Column(nullable = false)
    private String nombre;

    @NotBlank
    // @Column(nullable = false)
    private String apellido;

    @NotBlank
    @Email
    @Column(unique=true)
    private String correo;

    @NotBlank
    // @Column(nullable = false)
    private String contrasenia;

    // comentario para pruebas :) 

}
