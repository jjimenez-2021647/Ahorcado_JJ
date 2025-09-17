package modelo;

public class Usuarios {
    private int codigoUsuario;
    private String correoUsuario;
    private String contraseñaUsuario;

    public Usuarios() {
    }

    public Usuarios(int codigoUsuario, String correoUsuario, String contraseñaUsuario) {
        this.codigoUsuario = codigoUsuario;
        this.correoUsuario = correoUsuario;
        this.contraseñaUsuario = contraseñaUsuario;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
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
