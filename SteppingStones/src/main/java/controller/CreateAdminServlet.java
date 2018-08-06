/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Admin;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AdminDAO;

@WebServlet(name = "CreateAdminServlet", urlPatterns = {"/CreateAdminServlet"})
public class CreateAdminServlet extends HttpServlet {

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
  
        String admin_username = request.getParameter("admin_name");
        String password = request.getParameter("adminPassword");
        int branch_id = Integer.parseInt(request.getParameter("branch"));
        AdminDAO adminDao = new AdminDAO();
        Admin existingAdmin = adminDao.retrieveAdminByName(admin_username);
        if (existingAdmin != null) {
            request.setAttribute("existingAdmin", existingAdmin.getAdmin_username());
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreateAdmin.jsp");
            dispatcher.forward(request, response);
        } else {
            Admin tempAdmin = new Admin(admin_username, password, branch_id);
            boolean status = adminDao.addAdmin(tempAdmin);
            if(status){
                request.setAttribute("status", "Admin created successfully!");
            }else{
                request.setAttribute("errorMsg", "Error creating admin!");
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreateAdmin.jsp");
            dispatcher.forward(request, response);
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
