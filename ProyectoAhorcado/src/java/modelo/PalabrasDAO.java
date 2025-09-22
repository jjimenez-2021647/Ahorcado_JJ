package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PalabrasDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int resp;

    // Listar todas las palabras
    public List<Palabras> listar() {
        List<Palabras> listaPalabras = new ArrayList<>();
        String sql = "call sp_ListarPalabras();";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Palabras p = new Palabras();
                p.setCodigoPalabra(rs.getInt(1));
                p.setPalabra(rs.getString(2));
                p.setPista(rs.getString(3));
                listaPalabras.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPalabras;
    }

    // Agregar una nueva palabra
    public int agregar(Palabras p) {
        String sql = "call sp_AgregarPalabras(?, ?);";
        resp = 0;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getPalabra());
            ps.setString(2, p.getPista());
            ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    // Eliminar una palabra por código
    public int eliminar(int codigoPalabra) {
        String sql = "call sp_EliminarPalabras(?);";
        resp = 0;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, codigoPalabra);
            resp = ps.executeUpdate();
            System.out.println("Palabra eliminada. Filas afectadas: " + resp);
        } catch (Exception e) {
            System.out.println("Error al eliminar palabra: " + e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }

    // Buscar una palabra por código
    public Palabras buscar(int codigoPalabra) {
        String sql = "call sp_BuscarPalabras(?);";
        Palabras palabra = null;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, codigoPalabra);
            rs = ps.executeQuery();
            if (rs.next()) {
                palabra = new Palabras();
                palabra.setCodigoPalabra(rs.getInt(1));
                palabra.setPalabra(rs.getString(2));
                palabra.setPista(rs.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return palabra;
    }

    // Actualizar una palabra existente
    public int actualizar(Palabras p) {
        String sql = "call sp_EditarPalabras(?, ?, ?);";
        resp = 0;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getCodigoPalabra());
            ps.setString(2, p.getPalabra());
            ps.setString(3, p.getPista());
            resp = ps.executeUpdate();
            System.out.println("Palabra actualizada. Filas afectadas: " + resp);
        } catch (Exception e) {
            System.out.println("Error al actualizar palabra: " + e.getMessage());
            e.printStackTrace();
        }
        return resp;
    }
    
    public List<Palabras> listarParaJuego() {
    List<Palabras> lista = new ArrayList<>();
    String sql = "call sp_ListarPalabras();"; 
    try {
        con = cn.Conexion();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Palabras p = new Palabras();
            p.setPalabra(rs.getString("palabra"));
            p.setPista(rs.getString("pista"));
            lista.add(p);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}
}
