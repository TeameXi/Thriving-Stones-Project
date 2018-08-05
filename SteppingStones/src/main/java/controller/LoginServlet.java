/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.UsersDAO;
import entity.Users;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
                    
        String type = request.getParameter("type");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if ((username == null || username.isEmpty()) && (password == null || password.isEmpty())) {
            errors.put("error", "Missing Email & Password");
        }
        
        if (username == null || username.isEmpty()) {
            errors.put("error", "Missing Email & Password");
        }
        
        if(password == null || password.isEmpty()){
            errors.put("error", "Missing Password");
        }
        
        if(errors.isEmpty()) {
            UsersDAO users = new UsersDAO();
            Users user = users.retrieveUserByUsername(type, username);
            
            if(user != null) {
                String pwd = user.getPassword();
                if(password.equals(pwd)) {
                    if(type.equals("admin")){
                        session.setAttribute("user", user);
                        RequestDispatcher view = request.getRequestDispatcher("HomePage.jsp");
                        view.forward(request, response);
                    }else if (type.equals("tutor")){
                        session.setAttribute("user", user);
                        RequestDispatcher view = request.getRequestDispatcher("tutorHomepage.jsp");
                        view.forward(request, response);
                    }else if (type.equals("student")){
                        session.setAttribute("user", user);
                        RequestDispatcher view = request.getRequestDispatcher("studentHomepage.jsp");
                        view.forward(request, response);
                    }else if (type.equals("parent")){
                        session.setAttribute("user", user);
                        RequestDispatcher view = request.getRequestDispatcher("parentHomepage.jsp");
                        view.forward(request, response);
                    }
                }else{
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
