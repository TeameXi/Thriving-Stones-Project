/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
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

/**
 *
 * @author DEYU
 */
@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/ResetPasswordServlet"})
public class ResetPasswordServlet extends HttpServlet {

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
        
        String newPassword = request.getParameter("newPassword");
        String idStr = request.getParameter("id");
        String role = request.getParameter("role");
        int id = 0;
        if(idStr != null){
            id = Integer.parseInt(idStr);
        }
        
        boolean status = false;
        if(role.equals("tutor")){
            TutorDAO tutorDao = new TutorDAO();
            status = tutorDao.updateTutorPassword(id, newPassword);
        }else if(role.equals("admin")){
            AdminDAO adminDao = new AdminDAO();
            status = adminDao.updateAdminPassword(id, newPassword);
        }else if(role.equals("student")){
            status = StudentDAO.updateStudentPassword(id, newPassword);
        }else if(role.equals("parent")){
            status = ParentDAO.updateParentPassword(id, newPassword);
        }
        
        RequestDispatcher dispatcher;
        if(status){
            request.setAttribute("status", "Reset password successfully! Please login with new password.");
            dispatcher = request.getRequestDispatcher("Login.jsp");
        }else{
            request.setAttribute("errorMsg", "Failed to reset password!");
            dispatcher = request.getRequestDispatcher("ResetPassword.jsp");
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
