/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.StudentDAO;

/**
 *
 * @author Shawn
 */
@WebServlet(name = "PaymentStudentServlet", urlPatterns = {"/PaymentStudentServlet"})
public class PaymentStudentServlet extends HttpServlet {

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
        int branchID = Integer.parseInt(request.getParameter("branch_id"));
        if (request.getParameter("search") != null) {
            String student = (String) request.getParameter("student");
            String[] parts = student.split("-");
            String studentName = "";
            String studentEmail = "";
            int phone = 0;

            if (parts.length == 2) {
                studentName = parts[0].trim();
                if (parts[1].contains("@")) {
                    studentEmail = parts[1].trim();
                } else {
                    phone = Integer.parseInt(parts[1].trim());
                }
            }
//            System.out.println(phone + "& " + studentEmail);
            int studentID = 0;
            if (studentEmail.isEmpty()) {
                studentID = StudentDAO.retrieveStudentIDWithPhone(studentName, phone);
            } else {
                studentID = StudentDAO.retrieveStudentIDWithEmail(studentName, studentEmail);
            }
            request.getSession().setAttribute("from", "payment");
            response.sendRedirect("PaymentPage.jsp?studentID=" + studentID);
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
