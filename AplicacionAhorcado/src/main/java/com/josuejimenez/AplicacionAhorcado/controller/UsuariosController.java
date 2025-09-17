package com.josuejimenez.AplicacionAhorcado.controller;

import com.josuejimenez.AplicacionAhorcado.model.Palabras;
import com.josuejimenez.AplicacionAhorcado.model.Usuarios;
import com.josuejimenez.AplicacionAhorcado.service.UsuariosService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {
    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }


    @GetMapping
    public List<Usuarios> getAllUsuarios(){
        return usuariosService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public Object getUsuariosById(@PathVariable Integer id){
        Usuarios usuarios = usuariosService.getUsuariosById(id);
        if (usuarios == null) {
            return "El usuario no se encontro";
        }
        return usuarios;
    }

    @PostMapping
    public String createUsuarios(@RequestBody Usuarios usuarios){
        if (usuarios.getId() != null) {
            return "El id no es un campo de Post";
        }
        try{
            Usuarios result = usuariosService.saveUsuarios(usuarios);
            if ("Mensaje-ContraseñaVacia".equals(result.getContraseñaUsuario())) {
                return "La contraseña no pueda estar vacia";
            }
            if ("Mensaje-Correo".equals(result.getCorreoUsuario())) {
                return "El correo ya está registrado";
            }
            return "Usuario agregado exitosamente";
        }catch (CorreoInvalido e){
            return e.getMessage();
        }
    }

    @PutMapping("/{id}")
    public String updateUsuarios(@PathVariable Integer id, @RequestBody Usuarios usuarios){
        try{
            Usuarios result = usuariosService.updateUsuarios(id, usuarios);
            if ("Mensaje-ContraseñaVacia".equals(result.getContraseñaUsuario())) {
                return "La contraseña no pueda estar vacia";
            }
            if (result == null) {
                return "El usuario no se encontro";
            }
            if ("Mensaje-Correo".equals(result.getCorreoUsuario())) {
                return "El correo ya está registrado";
            }
            return "Usuario actualizado correctamente";
        }catch (CorreoInvalido e){
            return e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUsuarios(@PathVariable Integer id){
        Usuarios usuarios = usuariosService.getUsuariosById(id);
        if (usuarios == null) {
            return "Error: El usuario no existe o ya fue eliminado";
        }
        usuariosService.deleteUsuarios(id);
        return "Usuario eliminado correctamente";
    }
}
