/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.EventoDAO;
import com.ipn.mx.modelo.dto.Evento;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author frida
 */
@WebServlet(name = "EventoServlet", urlPatterns = {"/EventoServlet"})
public class EventoServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException {

        String accion = request.getParameter("accion");    
    //System.out.println(accion);
        if (accion.equals("listaDeEventos")) {
            listaDeEventos(request, response);
        } else {
            if (accion.equals("nuevo")) {
                nuevoEvento(request, response);
            } else {
                if (accion.equals("eliminar")) {
                    eliminarEvento(request, response);
                } else {
                    if (accion.equals("actualizar")) {
                        actualizarEvento(request, response);
                    } else {
                        if (accion.equals("guardar")) {
                            almacenarEvento(request, response);
                        } else {
                            if (accion.equals("ver")) {
                                mostrarEvento(request, response);
                            }
                        }
                    }
                }
            }
        }
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet EventoServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet EventoServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EventoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EventoServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private void listaDeEventos(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Lista De Eventos</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h1>Lista De Eventos</h1>");

            out.println("<a href='EventoServlet?accion=nuevo' class=\"btn btn-danger\">Nuevo </a>");

            out.println("<div class='table-responsive'>");
            out.println("<table class='table table-striped table-bordered'>");

            out.println("<tr>");
            out.println("<th>");
            out.println("clave de evento");
            out.println("</th>");

            out.println("<th>");
            out.println("nombre de evento");
            out.println("</th>");

            out.println("<th>");
            out.println("sede");
            out.println("</th>");

            out.println("<th>");
            out.println("fecha de Inicio");
            out.println("</th>");

            out.println("<th>");
            out.println("fecha de termino");
            out.println("</th>");

            out.println("<th>");
            out.println("Acciones");
            out.println("</th>");
            out.println("</tr>");

            int idEvento;
            String nombreEvento;
            String sede;
            Date fechaInicio;
            Date fechaFin;

            EventoDAO dao = new EventoDAO();
            try {
                List lista = dao.readAll();
                for (int i = 0; i < lista.size(); i++) {
                    Evento e = (Evento) lista.get(i);
                    idEvento = e.getIdEvento();
                    nombreEvento = e.getNombreEvento();
                    sede = e.getSede();
                    fechaInicio = e.getFechaInicio();
                    fechaFin = e.getFechaFin();

                    out.println("<tr>");
                    out.println("<td>" + idEvento + "</td>");
                    out.println("<td>" + nombreEvento + "</td>");
                    out.println("<td>" + sede + "</td>");
                    out.println("<td>" + fechaInicio + "</td>");
                    out.println("<td>" + fechaFin + "</td>");
                    out.println("<td>");                    
                    out.println("<a href='EventoServlet?accion=eliminar&id=" + idEvento + "' class='btn btn-outline-danger'>Eliminar</a>");
                    out.println("<a href='EventoServlet?accion=actualizar&id=" + idEvento + "&nombre=" + nombreEvento + "&sede=" + sede + "&FI=" + fechaInicio + "&FF=" + fechaFin + "' class='btn btn-outline-primary'>Actualizar</a>");
                    out.println("<a href='EventoServlet?accion=ver&id=" + idEvento + "&nombre=" + nombreEvento + "' class='btn btn-outline-success'>Ver_Evento</a>");

                    out.println("</td>");
                    out.println("</tr>");

                }
            } catch (SQLDataException e) {

            }

            out.println("</table>");
            out.println("</div>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void nuevoEvento(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("eventosForm.html");
        } catch (IOException e) {
        }
    }

    private void eliminarEvento(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EventoDAO dao = new EventoDAO();
        Evento e = new Evento();

        try {
            e.setIdEvento(Integer.parseInt(request.getParameter("id")));
            //e = dao.read(e);
            dao.delete(e);
            //listaDeEventos(request, response); //te reescribe la url
            response.sendRedirect("EventoServlet?accion=listaDeEventos"); // esta no te lo rescribe
            //RequestDispatcher vista = request.getRequestDispatcher("EventoServlet?accion=listaDeEventos");
            //vista.forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EventoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarEvento(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EventoDAO dao = new EventoDAO();
        Evento e = new Evento();

        try {
            e.setIdEvento(Integer.parseInt(request.getParameter("id")));
            e.setNombreEvento(request.getParameter("nombre"));
            e.setSede(request.getParameter("sede"));
            e.setFechaInicio(Date.valueOf(request.getParameter("FI")));
            e.setFechaTermino(Date.valueOf(request.getParameter("FF")));
            dao.read(e);

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Lista De Eventos</title>");
                out.println("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<h1>Actualizar Datos Del Eventos</h1>");

                out.println("<form action='EventoServlet?accion=guardar' method='post'>");
                out.println("<div class='container'>");
                out.println("<div class=\"form-row\">");
                
                out.println("<div class=\"form-group col-md-6\">");
                out.println("<label for='disabledTextInput'>id</label>");
                out.println("<input type=\"text\" class=\"form-control\" id='disabledTextInput' value=" + e.getIdEvento()+ " name=\"id\" >");
                out.println("</div>");
                

                out.println("<div class=\"form-group col-md-6\">");
                out.println("<label for=\"txtnombre\">Nombre</label>");
                out.println("<input type=\"text\" class=\"form-control\" id=\"txtnombre\" value=" + e.getNombreEvento() + " name=\"txtnombre\" placeholder=\"Nombre\" required=\"required\">");
                out.println("</div>");
                out.println("<div class=\"form-group col-md-6\">");
                out.println("<label for=\"txtsede\">Sede</label>");
                out.println("<input type=\"text\" class=\"form-control\" id=\"txtsede\" value=" + e.getSede() + " name=\"txtsede\" placeholder=\"Sede\" required=\"required\">");
                out.println("</div>");
                out.println("</div>");
                out.println("<div class=\"form-row\">");
                out.println("<div class=\"form-group col-md-6\">");
                out.println("<label for=\"txtsede\">Fecha Inicio</label>");
                out.println("<input type=\"date\" class=\"form-control\" id=\"txtfechaInicio\" value=" + e.getFechaInicio() + " name=\"txtfechaInicio\" >");
                out.println("</div>");
                out.println("<div class=\"form-group col-md-6\">");
                out.println("<label for=\"txtsede\">Fecha Fin</label>");
                out.println("<input type=\"date\" class=\"form-control\" id=\"txtfechaFin\" value=" + e.getFechaFin() + " name=\"txtfechaFin\" >");
                out.println("</div>");
                out.println("<button type=\"submit\" class=\"btn btn-primary\" >Actualizar</button>");
                //out.println("<button type=\"submit\" class=\"btn btn-primary\">Enviar</button>");
                out.println("</div>");
                out.println("<a href='EventoServlet?accion=listaDeEventos' class='btn btn-primary'>Regresar</a>");
                out.println("</div>");
                out.println("</form>");

                //out.println("<a href='EventoServlet?accion=listaDeEventos' class='btn btn-primary'>Regresar</a>");

                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }

        } catch (SQLException ex) {
            Logger.getLogger(EventoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void almacenarEvento(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EventoDAO dao = new EventoDAO();
        Evento e = new Evento();
        System.out.println("prueba");
        if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
            e.setNombreEvento(request.getParameter("txtnombre"));
            e.setSede(request.getParameter("txtsede"));
            e.setFechaInicio((Date.valueOf(request.getParameter("txtfechaInicio"))));
            e.setFechaTermino((Date.valueOf(request.getParameter("txtfechaFin"))));
            
            try {
                dao.create(e);
                response.sendRedirect("EventoServlet?accion=listaDeEventos");                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

       }else{
            e.setIdEvento(Integer.parseInt(request.getParameter("id")));
              e.setNombreEvento(request.getParameter("txtnombre"));
              e.setSede(request.getParameter("txtsede"));
              e.setFechaInicio((Date.valueOf(request.getParameter("txtfechaInicio"))));
              e.setFechaTermino((Date.valueOf(request.getParameter("txtfechaFin"))));
              
            try {
                dao.update(e);
                response.sendRedirect("EventoServlet?accion=listaDeEventos"); // esta no te lo rescribe
            } catch (SQLException ex) {
                Logger.getLogger(EventoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void mostrarEvento(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //_----------
        EventoDAO dao = new EventoDAO();
        Evento e = new Evento();

        try {
            e.setIdEvento(Integer.parseInt(request.getParameter("id")));
            e.setNombreEvento(request.getParameter("nombre"));
            dao.read(e);

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Lista De Eventos</title>");
                out.println("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<h1>Datos del Evento</h1>");

                out.println("<div class='card'>");
                out.println("<h5 class='card-header'>Datos del Evento</h5>");
                out.println("<div class='card-body'>");
                out.println("</h1>Id_Evento: </h1>" + e.getIdEvento());
                out.println("<hr>");
                out.println("</h1>Nombre_Evento: </h1>" + e.getNombreEvento());
                out.println("<hr>");
                out.println("</h1> Sede_Evento: </h1>" + e.getSede());
                out.println("<hr>");
                out.println("</h1> Fecha_Inicio: </h1>" + e.getFechaInicio());
                out.println("<hr>");
                out.println("</h1> Fecha_Fin: </h1>" + e.getFechaFin());
                out.println("</div>");
                out.println("</div>");
                out.println("<a href='EventoServlet?accion=listaDeEventos' class='btn btn-primary'>Regresar</a>");

                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }

        } catch (SQLException ex) {
            Logger.getLogger(EventoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
