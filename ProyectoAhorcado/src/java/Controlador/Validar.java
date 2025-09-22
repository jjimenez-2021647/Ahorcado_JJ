package Controlador;

import modelo.Usuarios;
import modelo.UsuariosDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Validar")
public class Validar extends HttpServlet {

    UsuariosDAO usuariosDAO = new UsuariosDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("Ingresar".equalsIgnoreCase(accion)) {
            String email = request.getParameter("txtCorreo");
            String pass = request.getParameter("txtPassword");

            Usuarios usuarios = usuariosDAO.validar(email, pass);

            if (usuarios != null && usuarios.getCodigoUsuario() > 0) {
                
                response.sendRedirect("Controlador?menu=MenuPrincipal");
            } else {
                request.setAttribute("error", "Correo o contraseña incorrectos");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("Controlador?menu=Principal");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Controlador?menu=Principal");
    }

    @Override
    public String getServletInfo() {
        return "Servlet para validar usuarios";
    }
}
