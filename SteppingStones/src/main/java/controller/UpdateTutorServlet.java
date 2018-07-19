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
        Map<String, Object> updates = new HashMap<>();

        try {
            String tutorID = request.getParameter("tutorID");
            String name = request.getParameter("name");
            int age = Integer.parseInt(request.getParameter("age"));
            String phone = (String) request.getParameter("phone");
            String email = request.getParameter("email");

            if (!Validation.isValidID(tutorID)) {
                if (name != null && !name.equals("")) {
                    updates.put("name", name);
                }

                updates.put("age", age);

                if (Validation.isValidPhoneNo(phone)) {
                    updates.put("phone", phone);

                }

                if (Validation.isValidEmail(email)) {
                    updates.put("email", email);
                }

                if (!updates.isEmpty()) {
                    TutorDAO tutors = new TutorDAO();
                    tutors.updateTutor(tutorID, updates);
                    request.setAttribute("status", "Updated");
                    RequestDispatcher view = request.getRequestDispatcher("UpdateTutor.jsp");
                    view.forward(request, response);
                }
            }

        } catch (Exception e) {
            request.setAttribute("status", "Fail");
        }
        RequestDispatcher view = request.getRequestDispatcher("UpdateTutor.jsp");
        view.forward(request, response);
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
