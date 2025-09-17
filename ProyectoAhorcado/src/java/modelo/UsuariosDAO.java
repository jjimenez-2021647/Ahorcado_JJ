package modelo;

import config.Conexion;
import java.sql.CallableStatement;
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

    public Usuarios validar(String correoUsuario, String contraseñaUsuario) {
        //instanciar el objeto de la entidad Usuario
        Usuarios usuarios = new Usuarios();
        //agregar una variable de tipo Select * from Usuarios where nombreUsuario = ? and contraseñaUsuario = ?" String para muestra de consulta sql
        String sql = "call sp_BuscarUsuariosNC(?, ?)";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, correoUsuario);
            ps.setString(2, contraseñaUsuario);
            rs = ps.executeQuery();
            while (rs.next()) {
                usuarios.setCodigoUsuario(rs.getInt("codigo_usuario"));
                usuarios.setCorreoUsuario(rs.getString("correo_usuario"));
                usuarios.setContraseñaUsuario(rs.getString("contraseña_usuario"));
            }
        } catch (Exception e) {
            System.out.println("El usuario o contraseña son incorrectos");
            e.printStackTrace();
        }
        return usuarios;
    }

    public List listar() {
        String sql = "call sp_ListarUsuarios();";
        List<Usuarios> listaUsuarios = new ArrayList<>();
        try {
            con = cn.Conexion();
            ps = con.prepareCall(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios us = new Usuarios();
                us.setCodigoUsuario(rs.getInt(1));
                us.setCorreoUsuario(rs.getString(2));
                us.setContraseñaUsuario(rs.getString(3));
                listaUsuarios.add(us);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }

    public int agregar(Usuarios us) {
        String sql = "call sp_AgregarUsuario( ?, ?, ?, ?, ?, ?, ?);";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, us.getCorreoUsuario());
            ps.setString(2, us.getContraseñaUsuario());
            ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

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

    public Usuarios imagenCodigo(int codigoUsuario) {
        Usuarios usuario = null;
        String sql = "call sp_BuscarUsuariosImagen(?)";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, codigoUsuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuarios();
                usuario.setCodigoUsuario(rs.getInt(1));
                usuario.setCorreoUsuario(rs.getString(2));
                usuario.setContraseñaUsuario(rs.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

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
                us.setCodigoUsuario(rs.getInt(1));              
                us.setCorreoUsuario(rs.getString(2));
                us.setContraseñaUsuario(rs.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return us;
    }

    public int actualizar(Usuarios usuario) {
        String sql = "call sp_EditarUsuario(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int resp = 0;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, usuario.getCodigoUsuario());
            ps.setString(2, usuario.getCorreoUsuario());
            ps.setString(3, usuario.getContraseñaUsuario());

            resp = ps.executeUpdate();
            System.out.println("Usuario actualizado. Filas afectadas: " + resp);
        } catch (Exception e) {
            System.out.println("Error al actualizar Usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }

    public int registrarLogin(Usuarios usuario) {
        String sql = "call sp_RegistroLogin(?, ?, ?);";
        int resp = 0;
        try (Connection con = cn.Conexion(); CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, usuario.getCorreoUsuario());
            cs.setString(2, usuario.getContraseñaUsuario());
            cs.registerOutParameter(3, java.sql.Types.INTEGER); // Filas afectadas
            cs.execute();
            resp = cs.getInt(3);
            System.out.println("Usuario registrado en login. Filas afectadas: " + resp);
        } catch (Exception e) {
            System.out.println("Error al registrar usuario en login: " + e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }

    public int actualizarLogin(Usuarios usuario) {
        String sql = "call sp_EditarUsuarioLogin(?, ?, ?, ?, ?, ?)";
        int resp = 0;
        try (Connection con = cn.Conexion(); CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, usuario.getCodigoUsuario());
            resp = cs.executeUpdate();

            System.out.println("Usuario actualizado en login. Filas afectadas: " + resp);
        } catch (Exception e) {
            System.out.println("Error al actualizar usuario en login: " + e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }
}
