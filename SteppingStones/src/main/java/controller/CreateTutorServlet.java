/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Tutor;
import entity.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.FirebaseConnection;
import model.TutorDAO;
import model.UsersDAO;

@WebServlet(name = "CreateTutorServlet", urlPatterns = {"/CreateTutorServlet"})
public class CreateTutorServlet extends HttpServlet {

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

        try {
            String tutorID = request.getParameter("tutorID");
            String name = request.getParameter("name");
            int age = Integer.parseInt(request.getParameter("age"));
            String gender = request.getParameter("gender");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            FirebaseConnection.initFirebase();

            if (Validation.isValidGender(gender) && Validation.isValidPassword(password) && Validation.isValidEmail(email) && Validation.isValidID(tutorID)
                    && Validation.isValidPhoneNo(phone)) {
                TutorDAO tDAO = new TutorDAO();
                Tutor tempTutor = new Tutor(name, age, phone, gender, email, password);
                tDAO.addTutor(tutorID, tempTutor);
                UsersDAO uDAO = new UsersDAO();
                uDAO.addUser(tempTutor);
                request.setAttribute("status", "Added tutor successfully");
            } else {
                request.setAttribute("status", "Failed to add tutor");
            }
        } catch (Exception e) {
            request.setAttribute("status", "Failed to add tutor");
        }
        RequestDispatcher view = request.getRequestDispatcher("CreateTutor.jsp");
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
