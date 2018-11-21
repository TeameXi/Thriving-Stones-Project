/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Student;
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
 * @author MOH MOH SAN
 */
@WebServlet(name = "UpdateStudentServlet", urlPatterns = {"/UpdateStudentServlet"})
public class UpdateStudentServlet extends HttpServlet {

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

        try (PrintWriter out = response.getWriter()) {
            
            int studentID = Integer.parseInt(request.getParameter("studentID"));
            Student stud = StudentDAO.retrieveStudentbyID(studentID);
            String name = request.getParameter("name");
            String lvl = request.getParameter("lvl");
            String address = request.getParameter("address");
            String email = "";
            if (request.getParameter("email") != null){
                email = request.getParameter("email");
            }
            int phone = Integer.parseInt(request.getParameter("phone"));
            double req_amount = stud.getReqAmt();
            if (request.getParameter("r_amount") != null){
                req_amount = Double.parseDouble(request.getParameter("r_amount"));
            }
            double out_amount = stud.getOutstandingAmt();
            if (request.getParameter("o_amount") != null){
                out_amount = Double.parseDouble(request.getParameter("o_amount"));
            }
            
            boolean status = StudentDAO.updateStudent(studentID, name, lvl, address, phone, email, req_amount, out_amount);
            if (status) {
                out.println(1);
            } else {
                out.println(0);
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
