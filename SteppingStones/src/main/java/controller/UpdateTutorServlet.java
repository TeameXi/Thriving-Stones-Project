/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TutorDAO;

/**
 *
 * @author Hui Xin
 */
@WebServlet(name = "UpdateTutorServlet", urlPatterns = {"/UpdateTutorServlet"})
public class UpdateTutorServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            Map<String, Object> updates = new HashMap<>();
                    
            String tutorID = request.getParameter("tutorID");
            String name = request.getParameter("name");
            String age = (String) request.getParameter("age");
            String phone = (String) request.getParameter("phone");
            String gender = request.getParameter("gender");
            String email = request.getParameter("email");
            
            if(tutorID != null && !tutorID.equals("")){
                if(name != null && !name.equals("")){
                    updates.put("name", name);
                }
                
                if(age != null && !age.equals("")){
                    updates.put("age", age);
                }
                
                if(phone != null && !phone.equals("")){
                    if(phone.length() == 8){
                        updates.put("phone", phone);
                    }
                }
                
                if(gender != null && !gender.equals("")){
                    String genderUpdate = gender.toUpperCase();
                    if(genderUpdate.equals("F") || genderUpdate.equals("M")){
                        updates.put("gender", gender);
                    }
                }
                
                if(email != null && !email.equals("")){
                    if(email.contains("@")){
                        updates.put("email", email);
                    }
                }
                
                if(!updates.isEmpty()){
                    TutorDAO tutors = new TutorDAO();
                    tutors.updateTutor(tutorID, updates);
                    request.setAttribute("status", "Updated");
                    RequestDispatcher view = request.getRequestDispatcher("UpdateTutor.jsp");
                    view.forward(request, response);
                }
            }
            
            request.setAttribute("status", "Fail");
            RequestDispatcher view = request.getRequestDispatcher("UpdateTutor.jsp");
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
