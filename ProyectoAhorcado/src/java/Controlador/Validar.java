package Controlador;
 
import com.proyectoahorcado.modelo.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
/**
*
* @author informatica
*/
@WebServlet("/Validar")
public class Validar extends HttpServlet {
 
    UsuariosDAO usuariosDAO = new UsuariosDAO();
    Usuarios usuarios = new Usuarios();
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Validar</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Validar at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
 
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Controlador?menu=Index").forward(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
 
        // Evitar NPE si accion es null
        if ("Ingresar".equalsIgnoreCase(accion)) {
 
            String email = request.getParameter("txtCorreo");
            String pass = request.getParameter("txtPassword");
 
            usuarios = usuariosDAO.validar(email, pass);
 
            if (usuarios != null) {
                HttpSession session = request.getSession(); //Variable para poder almacenar datos en las vitas
                session.setAttribute("codigoUsuario", usuarios.getCodigoUsuario()); //variable del codigo que utilizo en la imagen
                session.setAttribute("nombreUsuario", usuarios.getNombreUsuario());
 
                // Validación exitosa - redirigir a página principal
                request.getRequestDispatcher("Controlador?menu=Principal").forward(request, response);
 
            } else {
                request.setAttribute("error", "Correo o contraseña incorrectos");
                request.getRequestDispatcher("Controlador?menu=Index").forward(request, response);
            }
 
        } else {
            request.getRequestDispatcher("Controlador?menu=Index").forward(request, response);
        }
 
    }
 
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
 
}