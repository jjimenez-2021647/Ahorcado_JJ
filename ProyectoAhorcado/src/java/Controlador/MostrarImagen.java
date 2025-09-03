package Controlador;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MostrarImagen")
public class MostrarImagen extends HttpServlet {

    private UsuariosDAO usuariosDAO = new UsuariosDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id"); //idParam es el que obtiene el id de la url cuando dice id="codigoUsuario obtenido"
        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro id");
            return;
        }
        
        //Primero obtenemos el id del Id param casteando a entero
        int idUsuario = Integer.parseInt(idParam);
        UsuariosDAO usuariosDao = new UsuariosDAO(); 
        Usuarios usuario = usuariosDao.imagenCodigo(idUsuario); //luego declaramos la variable usuario 
        //la cual es de tipo Usuario que va a ser igual al metodo de imagen codigo que ese metodo es el de una consulta que devuelve los datos segun el codigo ingresado, 
        //en este caso el del idParam que es el que se obtiene del usuario logeado

        if (usuario != null && usuario.getImagenUsuario() != null) {
            // Imagen del usuario
            response.setContentType("image/jpeg");
            response.getOutputStream().write(usuario.getImagenUsuario());
        } else {
            // Imagen por defecto si no existe, InputStream es el flujo de entreda, entonces ImagenPorDefault va a devolver un flujo de entrada
            InputStream ImagenPorDefault = getServletContext().getResourceAsStream("/Images/Cliente.jpeg"); //getServletContext es como el que busca el 
            //getServletContext es como el que busca el contexto de la aplicacion como el {pageContext.request.contextPath}, getResourceAsStream busca la imagen en la direccion
            if (ImagenPorDefault != null) {
                response.setContentType("image/jpeg"); //Tipo de dato que se enviara al navegador
                byte[] buffer = new byte[1024]; //este buffer es para leer poco a poco la imagen en bytes
                int bytesRead;
                //bytesRead cuantos bytes se leyeron, cuando llega al final del archivo es decir cuando ya se leyeron todos los bytes devuelve -1
                while ((bytesRead = ImagenPorDefault.read(buffer)) != -1) {
                    response.getOutputStream().write(buffer, 0, bytesRead); //toma todos los bytes leido y recibe el flujo de salida para el navegador 
                    //buffer arreglo donde estan los bytes, 0 la posicion inicial, bytesRead es el que dice cuando bytes deben de salir 
                }
                ImagenPorDefault.close(); //se cierra el InputStream porque ya no necesita leer mas bytes
            }
        }
        response.getOutputStream().close(); //Cierra el flujo de salida al final
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para mostrar imagen de usuario desde DB";
    }
}
