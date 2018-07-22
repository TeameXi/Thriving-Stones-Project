/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.JsonObject;
import entity.Student;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.StudentClassDAO;
import model.StudentDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "SignUpForClassServlet", urlPatterns = {"/SignUpForClassServlet"})
public class SignUpForClassServlet extends HttpServlet {

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
        
        String studentID = request.getParameter("studentID");
        String studentName = request.getParameter("studentName");
        String[] classValues =request.getParameterValues("classValue");
        
        Student s = StudentDAO.retrieveStudentbyID(studentID);

        double reqAmt = s.getReqAmt();
        double outstandingAmt = s.getOutstandingAmt();

        if(classValues != null){
            for(String classValue:classValues){
                String[] parts = classValue.split("&");
                String classKey = parts[0];
                double mthlyFees = Double.parseDouble(parts[5]);
                reqAmt = reqAmt + (mthlyFees*3);
                outstandingAmt = outstandingAmt + (mthlyFees*3);

                s.setReqAmt(reqAmt);
                s.setOutstandingAmt(outstandingAmt);
                StudentClassDAO.saveClassWithStudent(classKey, studentID, studentName);
            }
        }
        JsonObject student = new JsonObject();
        student.addProperty("reqAmt", reqAmt);
        student.addProperty("outstandingAmt", outstandingAmt);
        String studentJson = student.toString();
        StudentDAO.updateStudent(studentID, studentJson);
        
        request.setAttribute("status", "Sign Up successfully!");
        RequestDispatcher view = request.getRequestDispatcher("CreateNewStudent.jsp");
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
