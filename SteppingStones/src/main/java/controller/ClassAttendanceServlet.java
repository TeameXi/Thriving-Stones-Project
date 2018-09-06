/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.LessonDAO;
import entity.Lesson;
import entity.Student;
import javax.servlet.RequestDispatcher;
import model.StudentClassDAO;

/**
 *
 * @author Zang Yu
 */
@WebServlet(name = "ClassAttendanceServlet", urlPatterns = {"/ClassAttendanceServlet"})
public class ClassAttendanceServlet extends HttpServlet {
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
        PrintWriter out = response.getWriter();
        out.println("hi");
        int classID = 0;
        int lessonID = 0;
        if(request.getParameter("class_id")!=null){
            classID = Integer.parseInt(request.getParameter("class_id"));
        }
        if(request.getParameter("lesson_id")!=null){
            lessonID = Integer.parseInt(request.getParameter("lesson_id"));
        }        
        if(request.getParameter("search") != null){
            ArrayList<Student> studentList = StudentClassDAO.listAllStudentByClass(classID);
            request.setAttribute("studentList", studentList);
            
        }
        RequestDispatcher view = request.getRequestDispatcher("ClassAttendance.jsp");
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
