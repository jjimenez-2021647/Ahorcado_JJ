package com.josuejimenez.AplicacionAhorcado.model;

import jakarta.persistence.*;

@Entity
@Table(name="Usuarios")
public class Usuarios {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="codigo_usuario")
        private Integer id;

    @Column(name="nombre_usuario")
    private String nombreUsuario;

    @Column(name="apellido_usuario")
    private String apellidoUsuario;

    @Column(name="correo_usuario")
    private String correoUsuario;

    @Column(name="contraseña_usuario")
    private String contraseñaUsuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getContraseñaUsuario() {
        return contraseñaUsuario;
    }

    public void setContraseñaUsuario(String contraseñaUsuario) {
        this.contraseñaUsuario = contraseñaUsuario;
    }
}
