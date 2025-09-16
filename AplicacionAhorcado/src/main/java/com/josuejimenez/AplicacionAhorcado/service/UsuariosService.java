package com.josuejimenez.AplicacionAhorcado.service;

import com.josuejimenez.AplicacionAhorcado.model.Usuarios;

import java.util.List;

public interface UsuariosService {
    List<Usuarios> getAllUsuarios();
    Usuarios getUsuariosById(Integer id);
    Usuarios saveUsuarios(Usuarios usuarios);
    Usuarios updateUsuarios(Integer id, Usuarios usuarios);
    void deleteUsuarios(Integer id);
}
