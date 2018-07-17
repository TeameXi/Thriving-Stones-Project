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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        System.out.print("Im here");
        Map<String, Object> authObj = new LinkedHashMap<>();
            ArrayList<String> errorList = new ArrayList<>();
            String status = "";

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (username == null) {
                status = "error";
                errorList.add("missing username");
            }
            if (password == null) {
                status = "error";
                errorList.add("missing password");
            }

            if (username != null && username.isEmpty()) {
                status = "error";
                errorList.add("blank username");
            }

            if (password != null && password.isEmpty()) {
                status = "error";
                errorList.add("blank password");
            }

            if (!(status.equals("error"))) {
                
                List<Users> user =  UsersDAO.getUser(username);
                
                if (!user.isEmpty() && user.get(0).authenticateUser(user.get(0), password)) {
                    request.getSession(true).setAttribute("user", user);
                    request.getRequestDispatcher("HomePage.jsp").forward(request,response);
                } else {

                    authObj.put("status", "error");
                    String[] msgArray = {"invalid username/password"};
                    authObj.put("messages", msgArray);
                    request.getSession(true).setAttribute("response", authObj);
                    request.getRequestDispatcher("Login.jsp").forward(request,response);
                }
            } else {
                authObj.put("status", "error");
                Collections.sort(errorList);
                authObj.put("messages", errorList);
                request.getSession(true).setAttribute("response", authObj);
                request.getRequestDispatcher("Login.jsp").forward(request,response);
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
