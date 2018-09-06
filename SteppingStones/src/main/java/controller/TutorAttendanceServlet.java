package controller;

import entity.Lesson;
import entity.Tutor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LessonDAO;
import model.TutorDAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zang Yu
 */
@WebServlet(name = "TutorAttendanceServlet", urlPatterns = {"/TutorAttendanceServlet"})
public class TutorAttendanceServlet extends HttpServlet {
    
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
        if(request.getParameter("search") != null){
            String tutorName = (String) request.getParameter("tutorName");
            Tutor tutor = TutorDAO.retrieveSpecificTutor(tutorName);
            if(tutor == null){
                request.setAttribute("errorMsg", tutorName + " Not Exists in Database, Please Create Tutor First.");           
            }else{
                ArrayList<Lesson> lessons = LessonDAO.retrieveLessonsByTutor(tutor.getTutorId());
                request.setAttribute("lessons", lessons);                
                request.setAttribute("tutorName", tutorName);
            }
            
            RequestDispatcher view = request.getRequestDispatcher("MarkTutorAttendance.jsp");
            view.forward(request, response);
        }
        
        if(request.getParameter("select") != null){
            String[] lessonValues = request.getParameterValues("lessonValue");
            String tutorName = request.getParameter("tutorName");            
            Tutor tutor = TutorDAO.retrieveSpecificTutor(tutorName);    
            if(lessonValues != null){
                out.println("testing 2 ");
                for(String lessonVal: lessonValues){
                    out.println("lessonValue : " + lessonValues);
                    int lessonID = Integer.parseInt(lessonVal);
                    boolean status = LessonDAO.updateTutorAttendance(lessonID, 1);                    
                    if(status){                       
                        request.setAttribute("status", "Successfully Registered.");
                        request.setAttribute("tutorName", tutorName);
                    }
                }
            }
            RequestDispatcher view = request.getRequestDispatcher("MarkTutorAttendance.jsp");
            view.forward(request, response);
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
