/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AttendanceDAO;
import org.json.JSONObject;

/**
 *
 * @author Xin
 */
@WebServlet(name = "MarkStudentAttendanceServlet", urlPatterns = {"/MarkStudentAttendanceServlet"})
public class MarkStudentAttendanceServlet extends HttpServlet {

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
            try (PrintWriter out = response.getWriter()) {
                int studentID = Integer.parseInt(request.getParameter("studentID"));
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                int classID = Integer.parseInt(request.getParameter("classID"));
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));

                AttendanceDAO attendance = new AttendanceDAO();
                boolean attendanceTaken = attendance.retrieveStudentAttendance(studentID, lessonID);
                boolean status = false;
                
                if(attendanceTaken){
                    status = attendance.updateStudentAttendance(studentID, lessonID, classID, tutorID, false);
                } else{
                    status = attendance.updateStudentAttendance(studentID, lessonID, classID, tutorID, true);
                }

                JSONObject obj = new JSONObject();
                obj.put("status", status);
                String json = obj.toString();
                out.println(json);
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
