/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.UsersDAO;
import entity.Users;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HashMap<String, String> errors = new HashMap<>();
        HttpSession session = request.getSession();
                    
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        
        if ((email == null || email.isEmpty()) && (password == null || password.isEmpty())) {
            errors.put("error", "Missing Email & Password");
        }
        
        if (email == null || email.isEmpty()) {
            errors.put("error", "Missing Email & Password");
        }
        
        if(password == null || password.isEmpty()){
            errors.put("error", "Missing Password");
        }
        
        if(errors.isEmpty()) {
            UsersDAO users = new UsersDAO();
            Users user = users.retrieveUserByEmail(email);
            
            if(user != null) {
                String pwd = user.getPassword();
                if(password.equals(pwd)) {
                    session.setAttribute("user", user);
                    RequestDispatcher view = request.getRequestDispatcher("HomePage.jsp");
                    view.forward(request, response);
                }else{
                    System.out.println(pwd);
                    errors.put("error", "Incorrect Password");
                    session.setAttribute("response", errors);
                    RequestDispatcher view = request.getRequestDispatcher("Login.jsp");
                    view.forward(request, response);
                }
            }
        }else {
            session.setAttribute("response", errors);
            RequestDispatcher view = request.getRequestDispatcher("Login.jsp");
            view.forward(request, response);
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
