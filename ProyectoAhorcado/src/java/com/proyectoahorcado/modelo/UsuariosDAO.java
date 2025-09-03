package com.proyectoahorcado.modelo;

import com.proyectoahorcado.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int resp;

    // Listar usuarios
    public List<Usuarios> listar() {
        String sql = "call sp_ListarUsuarios();";
        List<Usuarios> listaUsuarios = new ArrayList<>();
        try {
            con = cn.Conexion();
            ps = con.prepareCall(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios us = new Usuarios();
                us.setCodigoUsuario(rs.getInt("codigoUsuario"));
                us.setNombreUsuario(rs.getString("nombreUsuario"));
                us.setApellidoUsuario(rs.getString("apellidoUsuario"));
                us.setCorreoUsuario(rs.getString("correoUsuario"));
                us.setContraseñaUsuario(rs.getString("contraseñaUsuario"));
                listaUsuarios.add(us);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }

    // Agregar usuario
    public int agregar(Usuarios us) {
        String sql = "call sp_AgregarUsuario(?, ?, ?, ?);";
        int resp = 0;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, us.getNombreUsuario());
            ps.setString(2, us.getApellidoUsuario());
            ps.setString(3, us.getCorreoUsuario());
            ps.setString(4, us.getContraseñaUsuario());
            resp = ps.executeUpdate();
            System.out.println("Usuario agregado. Filas afectadas: " + resp);
        } catch (Exception e) {
            System.out.println("Error al agregar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }

    // Registrar usuario (solo correo y contraseña)
    public int registrarUsuario(String correoUsuario, String contraseñaUsuario) {
        String sql = "call sp_RegistrarUsuario(?, ?);";
        int resp = 0;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, correoUsuario);
            ps.setString(2, contraseñaUsuario);
            resp = ps.executeUpdate();
            System.out.println("Usuario registrado. Filas afectadas: " + resp);
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }

    // Eliminar usuario
    public int eliminar(int codigoUsuario) {
        String sql = "call sp_EliminarUsuario(?);";
        resp = 0;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, codigoUsuario);
            resp = ps.executeUpdate();
            System.out.println("Usuario eliminado. Filas afectadas: " + resp);
        } catch (Exception e) {
            System.out.println("Error al eliminar Usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }

    // Buscar usuario por código
    public Usuarios buscarPorCodigo(int codigoUsuario) {
        String sql = "call sp_BuscarUsuarios(?);";
        Usuarios us = null;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, codigoUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                us = new Usuarios();
                us.setCodigoUsuario(rs.getInt("codigoUsuario"));
                us.setNombreUsuario(rs.getString("nombreUsuario"));
                us.setApellidoUsuario(rs.getString("apellidoUsuario"));
                us.setCorreoUsuario(rs.getString("correoUsuario"));
                us.setContraseñaUsuario(rs.getString("contraseñaUsuario"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return us;
    }

    // Actualizar usuario
    public int actualizar(Usuarios usuario) {
        String sql = "call sp_EditarUsuario(?, ?, ?, ?, ?)";
        int resp = 0;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, usuario.getCodigoUsuario());
            ps.setString(2, usuario.getNombreUsuario());
            ps.setString(3, usuario.getApellidoUsuario());
            ps.setString(4, usuario.getCorreoUsuario());
            ps.setString(5, usuario.getContraseñaUsuario());

            resp = ps.executeUpdate();
            System.out.println("Usuario actualizado. Filas afectadas: " + resp);
        } catch (Exception e) {
            System.out.println("Error al actualizar Usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }

    // Actualizar usuario login (solo nombre y apellido)
    public int actualizarLogin(Usuarios usuario) {
        String sql = "call sp_EditarUsuarioLogin(?, ?, ?)";
        int resp = 0;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, usuario.getCodigoUsuario());
            ps.setString(2, usuario.getNombreUsuario());
            ps.setString(3, usuario.getApellidoUsuario());

            resp = ps.executeUpdate();
            System.out.println("Usuario actualizado en login. Filas afectadas: " + resp);
        } catch (Exception e) {
            System.out.println("Error al actualizar usuario en login: " + e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }

    // Validar usuario para login - usando buscar por correo y contraseña
    public Usuarios validar(String correoUsuario, String contraseñaUsuario) {
        // Como no tienes SP específico para validar, usaremos listar() y filtrar
        List<Usuarios> listaUsuarios = listar();
        for (Usuarios usuario : listaUsuarios) {
            if (usuario.getCorreoUsuario().equals(correoUsuario) && 
                usuario.getContraseñaUsuario().equals(contraseñaUsuario)) {
                return usuario;
            }
        }
        return null; // Si no encuentra el usuario
    }
}