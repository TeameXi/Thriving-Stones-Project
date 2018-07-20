/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
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
        Map<String, Object> updates = new HashMap<>();

        try (PrintWriter out = response.getWriter()) {
            String tutorID = request.getParameter("tutorID");
            String name = request.getParameter("name");
            int age = Integer.parseInt(request.getParameter("age"));
            String gender = request.getParameter("gender");
            String email = request.getParameter("email");
            String phone = (String) request.getParameter("phone");

            if (Validation.isValidGender(gender) && Validation.isValidEmail(email) && Validation.isValidID(tutorID)
                    && Validation.isValidPhoneNo(phone) && Validation.isValidAge(age) && name != null && !name.equals("")) {
                updates.put("name", name);
                updates.put("email", email);
                updates.put("phone", phone);
                updates.put("gender", gender);
            }

            if (age > 0) {
                updates.put("age", age);
            }

            if (!updates.isEmpty()) {
                TutorDAO tutors = new TutorDAO();
                boolean status = tutors.updateTutor(tutorID, updates);
                if (status) {
                    out.println(1);
                } else {
                    out.println(0);
                }

            }

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
