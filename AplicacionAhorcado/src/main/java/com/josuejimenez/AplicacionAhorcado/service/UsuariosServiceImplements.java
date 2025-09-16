package com.josuejimenez.AplicacionAhorcado.service;

import com.josuejimenez.AplicacionAhorcado.controller.ValidacionCorreo;
import com.josuejimenez.AplicacionAhorcado.model.Usuarios;
import com.josuejimenez.AplicacionAhorcado.repository.UsuariosRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosServiceImplements implements UsuariosService{

    private final UsuariosRepository usuariosRepository;

    public UsuariosServiceImplements(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public List<Usuarios> getAllUsuarios() {
        return usuariosRepository.findAll();
    }

    @Override
    public Usuarios getUsuariosById(Integer id) {
        return usuariosRepository.findById(id).orElse(null);
    }

    @Override
    public Usuarios saveUsuarios(Usuarios usuarios) {
        ValidacionCorreo.validarCorreo(usuarios.getCorreoUsuario());
        List<Usuarios> lista = usuariosRepository.findAll();
        for (Usuarios u : lista) {
            if (u.getCorreoUsuario().equalsIgnoreCase(usuarios.getCorreoUsuario())) {
                usuarios.setCorreoUsuario("Mensaje-Correo");
                return usuarios;
            }
        }
        return usuariosRepository.save(usuarios);
    }

    @Override
    public Usuarios updateUsuarios(Integer id, Usuarios usuarios) {
        ValidacionCorreo.validarCorreo(usuarios.getCorreoUsuario());
        Usuarios existinUsuario = usuariosRepository.findById(id).orElse(null);
        if (existinUsuario != null) {
            List<Usuarios> lista = usuariosRepository.findAll();
            for (Usuarios u : lista) {
                if (!u.getId().equals(id)) {
                    if (u.getCorreoUsuario().equalsIgnoreCase(usuarios.getCorreoUsuario())) {
                        usuarios.setCorreoUsuario("Mensaje-Correo");
                        return usuarios;
                    }
                }
            }
            existinUsuario.setCorreoUsuario(usuarios.getCorreoUsuario());
            return usuariosRepository.save(existinUsuario);
        }
        return null;
    }

    @Override
    public void deleteUsuarios(Integer id) {
        usuariosRepository.deleteById(id);
    }
}
