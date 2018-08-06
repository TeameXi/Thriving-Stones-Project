/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.StudentClassDAO;
import entity.Class;
import java.util.ArrayList;
import model.StudentDAO;
import model.StudentGradeDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "CreateTuitionGradeServlet", urlPatterns = {"/CreateTuitionGradeServlet"})
public class CreateTuitionGradeServlet extends HttpServlet {

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
        
        if(request.getParameter("select") != null){
            String classIDStr = request.getParameter("select");
            int classID = Integer.parseInt(classIDStr);
            ArrayList<String> students = StudentClassDAO.listStudentsinSpecificClass(classID);
            Class cls = ClassDAO.getClassByID(classID);
            
            request.setAttribute("students", students);
            request.setAttribute("class", cls);  
        }
        if(request.getParameter("insert") != null){
            String assessmentType = request.getParameter("assessmentType");
            String classIDStr = request.getParameter("classID");
            int classID = Integer.parseInt(classIDStr);
            ArrayList<String> students = StudentClassDAO.listStudentsinSpecificClass(classID);
            //System.out.println(assessmentType + classID + request.getParameter("Deyu"));
            for(String studentName: students){
                String grade = request.getParameter(studentName);
                int studentID = StudentDAO.retrieveStudentID(studentName);
                //System.out.println("studentID " + studentID + "Grade " + grade + "Assess " + assessmentType +"class:" +  classID);
                boolean status = StudentGradeDAO.saveTuitionGrades(studentID, classID, assessmentType, grade);
                if(status){
                    request.setAttribute("status", "Successfully added");
                }else{
                    request.setAttribute("status", "Error while adding grade.");
                }
            }          
        }
        RequestDispatcher view = request.getRequestDispatcher("CreateTuitionGrade.jsp");
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
