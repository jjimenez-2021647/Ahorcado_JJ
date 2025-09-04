package Controlador;

import com.proyectoahorcado.modelo.Palabras;
import com.proyectoahorcado.modelo.PalabrasDAO;
import com.proyectoahorcado.modelo.Usuarios;
import com.proyectoahorcado.modelo.UsuariosDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author joshu
 */
@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String menu = request.getParameter("menu");
        String accion = request.getParameter("accion");
        UsuariosDAO usuariosDao = new UsuariosDAO();

        if (menu == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        if (menu.equals("Principal")) {
            if ("RegistroLogin".equals(accion)) {
                String correoRegistro = request.getParameter("txtUsuarioR");
                String contraseñaRegistro = request.getParameter("txtPasswordR");
                String confirmarContraseña = request.getParameter("confirmar");

                if (contraseñaRegistro.equals(confirmarContraseña)) {
                    Usuarios nuevoUsuario = new Usuarios();
                    nuevoUsuario.setCorreoUsuario(correoRegistro);
                    nuevoUsuario.setContraseñaUsuario(contraseñaRegistro);

                    int resultadoR = usuariosDao.registrarLogin(nuevoUsuario);

                    if (resultadoR > 0) {
                        // Redirige al JSP principal para login
                        response.sendRedirect("index.jsp");
                    } else {
                        request.setAttribute("errorRegistro", "Error al registrar usuario");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("errorRegistro", "Las contraseñas no coinciden");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }

            } else if ("ActualizarLogin".equals(accion)) {
                int codigoUsuarioLogin = Integer.parseInt(request.getParameter("txtCodigoUsuario"));
                String nombreLogin = request.getParameter("txtNombreUsuario");
                String apellidoLogin = request.getParameter("txtApellidoUsuario");

                Usuarios usuarioLogin = new Usuarios();
                usuarioLogin.setCodigoUsuario(codigoUsuarioLogin);
                usuarioLogin.setNombreUsuario(nombreLogin);
                usuarioLogin.setApellidoUsuario(apellidoLogin);

                int filasLogin = usuariosDao.actualizarLogin(usuarioLogin);
                System.out.println("Filas actualizadas en login: " + filasLogin);

                Usuarios usuarioActualizado = usuariosDao.buscarPorCodigo(codigoUsuarioLogin);
                request.setAttribute("usuario", usuarioActualizado);
                request.getRequestDispatcher("/Index/VistaUsuarioCliente.jsp").forward(request, response);

            } else {
                // Si no hay acción específica, solo muestra el index
                response.sendRedirect("index.jsp");
            }
        } else if (menu.equals("MenuPrincipal")) {
            request.getRequestDispatcher("/Index/MenuPrincipal.jsp").forward(request, response);
        } else if (menu.equals("Ahorcado")) {
            request.getRequestDispatcher("/Index/Ahorcado.jsp").forward(request, response);
        } else if (menu.equals("Rompecabezas")) {
            request.getRequestDispatcher("/Index/rompecabezas.jsp").forward(request, response);
        } else if (menu.equals("Rompecabezas2")) {
            request.getRequestDispatcher("/Index/rompecabezas2.jsp").forward(request, response);
        } else if (menu.equals("PalabrasJuego")) {
            PalabrasDAO dao = new PalabrasDAO();
            List<Palabras> lista = dao.listarParaJuego();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < lista.size(); i++) {
                Palabras p = lista.get(i);
                sb.append("{\"palabra\":\"").append(p.getPalabra())
                        .append("\",\"pista\":\"").append(p.getPista()).append("\"}");
                if (i < lista.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");

            PrintWriter out = response.getWriter();
            out.print(sb.toString());
            out.flush();
            return;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
