/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.StudentClassDAO;
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
        
        
        
        String studentName = request.getParameter("studentName"); 
        
        if(request.getParameter("retrieve") != null){
            if(StudentDAO.retrieveStudentID(studentName) != 0){
                LinkedHashMap<String, ArrayList<String>> gradeLists = StudentGradeDAO.retrieveStudentTuitionGrade(studentName);
                request.setAttribute("gradeLists", gradeLists);
                request.setAttribute("studentName", studentName);
            }else{
                request.setAttribute("status", studentName + " not found in database.");
            }           
            RequestDispatcher view = request.getRequestDispatcher("RetrieveUpdateStudentGrade.jsp");
            view.forward(request, response);
        }
        
//        if(request.getParameter("update") != null){
//            int studentID = StudentDAO.retrieveStudentID(studentName);
//            if(studentID != 0){
//                Map<Integer, String> classSub = StudentClassDAO.retrieveStudentClassSub(studentID);
//                request.setAttribute("classSub", classSub);
//                request.setAttribute("studentID", studentID);
//                request.setAttribute("studentName", studentName);
//            }else{
//                request.setAttribute("status", studentName + " not found in database.");
//            }
//            RequestDispatcher view = request.getRequestDispatcher("UpdateStudentGrades.jsp");
//            view.forward(request, response);
//        }
            
//        if(request.getParameter("insert") != null){
//            int studentID = Integer.parseInt(request.getParameter("studentID"));
//            int classID = Integer.parseInt(request.getParameter("subjects"));
//            String assessmentType = request.getParameter("assessmentType");
//            String grade = request.getParameter("grade");
//            boolean status = StudentGradeDAO.updateTuitionGrade(studentID, classID, assessmentType, grade);
//            if(status){
//                request.setAttribute("status", "Grade Updated Successfully.");
//            }else{
//                request.setAttribute("status", "Error while Updating Grade.");
//            }
//            RequestDispatcher view = request.getRequestDispatcher("RetrieveUpdateStudentGrade.jsp");
//            view.forward(request, response);
//        }
        
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
