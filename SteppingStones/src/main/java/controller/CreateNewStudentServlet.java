/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Student;
import entity.Class;
import entity.StudentGrade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.StudentDAO;
import model.StudentGradeDAO;
import entity.Validation;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "CreateNewStudentServlet", urlPatterns = {"/CreateNewStudentServlet"})
public class CreateNewStudentServlet extends HttpServlet {

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
        String studentName = request.getParameter("studentName");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");
        String lvl = request.getParameter("lvl");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String sub1 = request.getParameter("Sub1");
        String fCA1 = request.getParameter("FCA1");
        String fCA2 = request.getParameter("FCA2");
        String fSA1 = request.getParameter("FSA1");
        String fSA2 = request.getParameter("FSA2");
        StudentGrade fGrade = new StudentGrade(fCA1, fCA2, fSA1, fSA2);
        String sub2 = request.getParameter("Sub2");
        String sCA1 = request.getParameter("SCA1");
        String sCA2 = request.getParameter("SCA2");
        String sSA1 = request.getParameter("SSA1");
        String sSA2 = request.getParameter("SSA2");
        StudentGrade sGrade = new StudentGrade(sCA1, sCA2, sSA1, sSA2);
        String sub3 = request.getParameter("Sub3");
        String tCA1 = request.getParameter("TCA1");
        String tCA2 = request.getParameter("TCA2");
        String tSA1 = request.getParameter("TSA1");
        String tSA2 = request.getParameter("TSA2");
        StudentGrade tGrade = new StudentGrade(tCA1 , tCA2, tSA1 , tSA2);
           
        if(request.getParameter("insert") != null){
            ArrayList<String> errors = Validation.validateStudent(studentID, studentName, age, gender, lvl, phone, sub1, sub2, sub3);
            Student existingStudent = StudentDAO.retrieveStudentbyID(studentID);
            if(errors.isEmpty() && existingStudent == null){
                StudentDAO.insertStudent(studentID, studentName, age, gender, lvl, address, phone, 0, 0); 
                StudentGradeDAO.saveSchoolGrade(studentID, sub1, fGrade, sub2, sGrade, sub3, tGrade);
                Map<String, Class> classes = ClassDAO.getClassByLevel(lvl);
                request.setAttribute("level", lvl);
                request.setAttribute("studentID", studentID);
                request.setAttribute("studentName", studentName);
                request.setAttribute("class", classes);
                RequestDispatcher view = request.getRequestDispatcher("SignUpForClass.jsp");
                view.forward(request, response);
            }else{
                if(existingStudent != null){
                    request.setAttribute("studentExist", "There was already a record of student with ID: " + studentID);
                }
                request.setAttribute("errorMsg", errors);
                RequestDispatcher view = request.getRequestDispatcher("CreateNewStudent.jsp");
                view.forward(request, response);
            }
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
