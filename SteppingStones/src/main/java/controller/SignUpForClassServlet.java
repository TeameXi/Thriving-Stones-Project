/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Student;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.FirebaseConnection;
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
        System.out.println(studentID);
        FirebaseConnection.initFirebase();
        /*
        StudentDAO studentDAO = new StudentDAO();
        ArrayList<Student> stu = studentDAO.retrieveStudentbyID(studentID);
        Student s = null;
        if(stu != null){
            for(int i = 0; i< stu.size(); i++){
                s = stu.get(i);
            }
        }
        
        if(s != null){
            double reqAmt = s.getReqAmt();
            double outstandingAmt = s.getOutstandingAmt();
            System.out.println(reqAmt);
            s.setReqAmt(100);
            s.setOutstandingAmt(200);
            System.out.println(s.getName());
            s.setName("GG");
        }
        System.out.println(s);*/
        if(classValues != null){
            for(String classValue:classValues){
                String[] parts = classValue.split("&");
                String classKey = parts[0];
                double mthlyFees = Double.parseDouble(parts[5]);
                
                Map<String, String> studentList = StudentClassDAO.getStudentsInSpecificClass(classKey);
                studentList.put(studentID, studentName);
                StudentClassDAO.saveClassWithStudents(classKey, studentList);
            }
        }
        
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
