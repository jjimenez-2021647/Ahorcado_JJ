package Controlador;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author joshu
 */
@WebServlet(name = "ImagenesActualizar", urlPatterns = {"/ImagenesActualizar"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB antes de escribir en disco
        maxFileSize = 1024 * 1024 * 10, // 10MB por archivo
        maxRequestSize = 1024 * 1024 * 15 // 15MB total
)
public class ImagenesActualizar extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ImagenesActualizar</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ImagenesActualizar at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        request.setCharacterEncoding("UTF-8");

        try {
            // Obtener el codigo del usuario
            int codigoUsuario = Integer.parseInt(request.getParameter("codigoUsuario"));

            // Obtener el archivo subido (la imagen)
            Part filePart = request.getPart("imagenUsuario");
            byte[] imagen = null;

            if (filePart != null && filePart.getSize() > 0) {
                //filePart.getInputStream() abre un flujo de entrada para leer el archivo subido (en este caso la imagen), ByteArrayOutputStream va acumulando los bytes.                
                try (InputStream input = filePart.getInputStream(); ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

                    byte[] data = new byte[1024];
                    int bytesRead; //leer los bytes
                    while ((bytesRead = input.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, bytesRead);
                    }
                    imagen = buffer.toByteArray();
                }
            }

            // Guardar en la base de datos
            UsuariosDAO usuariosDao = new UsuariosDAO();
            boolean exito = usuariosDao.actualizarImagen(codigoUsuario, imagen); //guarda en la db llamando el sp a travez del codigo y la imagen diche codigo se obtiene del 
            // usuario que se logueo

            if (exito) {
                HttpSession session = request.getSession(false); // false evitamos crear una nueva sesión si no existe

                if (session != null && session.getAttribute("tipoUsuario") != null) {
                    String tipoUsuario = (String) session.getAttribute("tipoUsuario");

                    if ("Cliente".equalsIgnoreCase(tipoUsuario)) {
                        response.sendRedirect(request.getContextPath() + "/Controlador?menu=VistaUsuarioClienteC&id=" + codigoUsuario);
                    } else if ("Empleado".equalsIgnoreCase(tipoUsuario)) {
                        response.sendRedirect(request.getContextPath() + "/Controlador?menu=VistaUsuarioCliente&id=" + codigoUsuario);
                    } else {
                        request.setAttribute("mensajeError", "Tipo de usuario no válido.");
                        request.getRequestDispatcher("Index/VistaUsuarioCliente.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("mensajeError", "Sesión expirada o tipo de usuario no encontrado.");
                    request.getRequestDispatcher("Index/VistaUsuarioCliente.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensajeError", "Ocurrió un error al actualizar la imagen.");
            request.getRequestDispatcher("Index/VistaUsuarioCliente.jsp").forward(request, response);
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
