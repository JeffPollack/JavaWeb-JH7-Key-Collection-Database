/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keys;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import jdbc.ConnectionPool;

/**
 *
 * @author Jeff
 */
public class KeySelectServlet extends HttpServlet {

    private String q_surround(String s) {
        return "\'" + s + "\'";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext servletContext = getServletContext();
        ConnectionPool connectionPool = (ConnectionPool) servletContext.getAttribute("connectionPool");
        ArrayList<String> arr1 = new ArrayList<String>();
        Connection connection ;
        Statement statement  ;
        try (PrintWriter out = response.getWriter()) {
                        connection = connectionPool.getConnection();
            statement = connection.createStatement();
            String bSelect = request.getParameter("valajax");
            //String building = request.getParameter("building");
            // add the items from the sql into the string arrays
            String query = "SELECT * FROM KeyCollection WHERE building="+q_surround(bSelect); 
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                arr1.add(resultSet.getString("room"));
            }
            
            response.getWriter().write("Select the Room : <select name='room'>");
            for (int i = 0; i < arr1.size(); i++) {
                String room = arr1.get(i).toString();
                response.getWriter().write("<option value='"+room+"'>" + arr1.get(i) + "</option>");
                request.setAttribute("room", room);
            }
            //request.setAttribute("building", building);
            
            response.getWriter().write("</select>");
            
        } catch (SQLException ex) {
            System.out.println(ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(KeySelectServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(KeySelectServlet.class.getName()).log(Level.SEVERE, null, ex);
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
