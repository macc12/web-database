/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import DAO.IBaseDatos;
import DAO.PersonaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import VO.Persona;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Labing I5
 */
public class RegistroServlet extends HttpServlet {

    //private DAOPersona dao;
    private PersonaDAO dao;
    private IBaseDatos model;

    @Override
    public void init() throws ServletException {
        //this.dao = new DAOPersona();
        this.dao = new PersonaDAO();
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
            RequestDispatcher rq = request.getRequestDispatcher("index.jsp");
            if (request.getParameter("borrar") != null) {
                String id = request.getParameter("borrar");
                Persona persona = this.dao.find(Integer.parseInt(id));
                this.dao.delete(persona);
            } else if (request.getParameter("editar") != null) {
                String id = request.getParameter("editar");
                Persona persona = this.dao.find(Integer.parseInt(id));
                request.setAttribute("persona", persona);
                rq.forward(request, response);
            }
            ArrayList<Persona> personas = (ArrayList<Persona>) this.dao.findAll();
            request.setAttribute("lista", personas);
            rq.forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RegistroServlet.class.getName()).log(Level.SEVERE, null, ex);
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

        if (request.getParameter("Boton1") != null) {
            System.out.println("Presiona el boton 1");
        } else {
            System.out.println("Presiona el boton21");
        }

        String[] vehiculos = request.getParameterValues("vehicle");
        for (String vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
        if (request.getParameter("editar") != null) {
            //Estoy editando
            String cedula = request.getParameter("cedula");
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            if (nombre != null && apellido != null && nombre.length() > 0) {
                try {
                    Persona persona = this.dao.find(Integer.parseInt(cedula));
                    persona.setNombre(nombre);
                    persona.setApellido(apellido);
                    this.dao.update(persona);
                } catch (SQLException ex) {
                    Logger.getLogger(RegistroServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            //Estoy registrando uno 

            String cedula = request.getParameter("cedula");
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            //Validaciones - SQL Inyection - Luego
            if (nombre != null && apellido != null && nombre.length() > 0) {
                try {
                    Persona persona
                            = new Persona(Integer.parseInt(cedula), nombre, apellido);
                    
                    if (!this.dao.insert(persona)) {
                        response.sendRedirect("index.jsp?error=ErrorDatos");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(RegistroServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        response.sendRedirect("index.jsp");

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
