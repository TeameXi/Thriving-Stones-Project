/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AdminDAO;
import model.ParentDAO;
import model.StudentDAO;
import model.TutorDAO;
import model.UsersDAO;

/**
 *
 * @author Riana
 */
@WebServlet(name = "UpdatePasswordServlet", urlPatterns = {"/UpdatePasswordServlet"})
public class UpdatePasswordServlet extends HttpServlet {

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
        String newPassword = request.getParameter("newPassword");
        String idStr = request.getParameter("id");
        int id = 0;
        if(idStr != null){
            id = Integer.parseInt(idStr);
        }
        UsersDAO users = new UsersDAO();
        Users user = users.retrieveUserByID(id);
        
        boolean status = false;
        if (user != null) {
            if(user.getRole().equals("admin")){
                AdminDAO adminDAO = new AdminDAO();
                status = adminDAO.updateAdminPassword(id, newPassword);
            }
            if(user.getRole().equals("tutor")){
                TutorDAO tutorDAO = new TutorDAO();
                status = tutorDAO.updateTutorPassword(id, newPassword);
            }
            if(user.getRole().equals("parent")){
                status = ParentDAO.updateParentPassword(id, newPassword);
            }
            if(user.getRole().equals("student")){
                status = StudentDAO.updateStudentPassword(id, newPassword);
            }
        }
        RequestDispatcher dispatcher;
        if(status){
            request.setAttribute("status", "Reset password successfully! Please login with new password.");
            dispatcher = request.getRequestDispatcher("Login.jsp");
        }else{
            request.setAttribute("errorMsg", "Failed to reset password!");
            request.setAttribute("user", user);
            dispatcher = request.getRequestDispatcher("ChangePassword.jsp");
        }
        dispatcher.forward(request, response);
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
