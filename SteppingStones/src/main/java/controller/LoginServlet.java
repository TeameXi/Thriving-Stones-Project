/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.UsersDAO;
import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AdminDAO;
import model.ParentDAO;
import model.StudentDAO;
import model.TutorDAO;

/**
 *
 * @author Riana
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
        HttpSession session = request.getSession();

        String type = request.getParameter("type");
        String username = request.getParameter("username");
        String password = request.getParameter("password").trim();
        UsersDAO users = new UsersDAO();
        Users user = users.retrieveUserByUsername(type, username, password);
        
        if (user != null) {
            if (type.equals("admin")) {
                AdminDAO adminDAO = new AdminDAO();
                user.setBranchId(adminDAO.retrieveAdminById(user.getRespective_id()).getBranch_id());
                
                session.setAttribute("user", user);
                session.setAttribute("role", "admin");
                response.sendRedirect("Dashboard.jsp");

            } else if (type.equals("tutor")) {
                TutorDAO tutorDAO = new TutorDAO();
                user.setBranchId(tutorDAO.retrieveSpecificTutorById(user.getRespective_id()).getBranch_id());
                
                session.setAttribute("user", user);
                session.setAttribute("role", "tutor");
                response.sendRedirect("tutorHomepage.jsp");

            } else if (type.equals("student")) {
                StudentDAO studentDAO = new StudentDAO();
                user.setBranchId(StudentDAO.retrieveStudentbyID(user.getRespective_id()).getBranchlID());
                
                session.setAttribute("user", user);
                session.setAttribute("role", "student");
                response.sendRedirect("studentHomepage.jsp");

            } else if (type.equals("parent")) {
                ParentDAO parentDAO = new ParentDAO();
                user.setBranchId(parentDAO.retrieveSpecificParentById(user.getRespective_id()).getBranch_id());
                
                session.setAttribute("user", user);
                session.setAttribute("role", "parent");
                response.sendRedirect("Dashboard.jsp");

            } else {
                response.sendRedirect("Login.jsp?error=true");
            }
            
        } else {
            response.sendRedirect("Login.jsp?error=true");
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
