/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Student;
import entity.StudentGrade;
import entity.Validation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.StudentDAO;
import model.StudentGradeDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "RetrieveUpdateGradesServlet", urlPatterns = {"/RetrieveUpdateGradesServlet"})
public class RetrieveUpdateGradesServlet extends HttpServlet {

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
        
        /*
        
        String studentID = request.getParameter("studentID");
        Student stu = StudentDAO.retrieveStudentbyID(studentID);        
        
        if(request.getParameter("retrieve") != null){
            request.setAttribute("StudentData", stu);
            RequestDispatcher view = request.getRequestDispatcher("RetrieveUpdateStudentGrade.jsp");
            view.forward(request, response);
        }
        
        if(request.getParameter("update") != null){
            request.setAttribute("StudentData", stu);
            ArrayList<String> subjects = new ArrayList<>();
            if(stu != null){
                Map<String, Map<String,StudentGrade>> stuGrades = stu.getGrades();
                if(stuGrades.get("Center") != null){
                    Map<String,StudentGrade> subGrades = stuGrades.get("Center");
                    Set<String> subs = subGrades.keySet();
                    System.out.println("Enter here");
                    subjects.addAll(subs);
                }
            }
            System.out.println(subjects);
            request.setAttribute("subjects", subjects);
            RequestDispatcher view = request.getRequestDispatcher("UpdateStudentGrades.jsp");
            view.forward(request, response);
        }
        
        if(request.getParameter("insert") != null){
            String sub = request.getParameter("subjects");
            String assessmentType = request.getParameter("assessmentType");
            String grade = request.getParameter("grade");
            ArrayList<String> errors = Validation.validateUpdateGrade(studentID, sub, assessmentType, grade);
            
            if(errors.isEmpty()){
                StudentGradeDAO.saveCenterGrades(studentID, sub, assessmentType, grade);
                request.setAttribute("status", "Grade Updated Successfully!");
            }else{
                request.setAttribute("errorMsg", errors);
            } 
            RequestDispatcher view = request.getRequestDispatcher("RetrieveUpdateStudentGrade.jsp");
            view.forward(request, response);
        }
        */
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
