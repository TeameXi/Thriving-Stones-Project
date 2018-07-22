/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.JsonObject;
import entity.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

            String studentID = request.getParameter("studentID");
            String name = request.getParameter("name");
            int age = Integer.parseInt(request.getParameter("age"));
            String gender = request.getParameter("gender");
            String lvl = request.getParameter("lvl");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String req_amount = request.getParameter("r_amount");
            String out_amount = request.getParameter("o_amount");

            ArrayList<String> errors = Validation.validateUpdateStudent(studentID, name, age, gender, lvl, phone, req_amount, out_amount);
            if (errors.isEmpty()) {
                JsonObject student = new JsonObject();
                double r_amount = Double.parseDouble(req_amount);
                double o_amount = Double.parseDouble(out_amount);
                student.addProperty("name", name);
                student.addProperty("age", age);
                student.addProperty("gender", gender);
                student.addProperty("level", lvl);
                student.addProperty("address", address);
                student.addProperty("phone", phone);
                student.addProperty("reqAmt", r_amount);
                student.addProperty("outstandingAmt", o_amount);
                String studentJson = student.toString();
                boolean status = StudentDAO.updateStudent(studentID, studentJson);
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
