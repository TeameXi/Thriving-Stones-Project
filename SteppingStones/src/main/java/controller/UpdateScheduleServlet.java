/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.LessonDAO;

/**
 *
 * @author HuiXin
 */
@WebServlet(name = "UpdateScheduleServlet", urlPatterns = {"/UpdateScheduleServlet"})
public class UpdateScheduleServlet extends HttpServlet {

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
        String level = request.getParameter("level");
        String subject = request.getParameter("subject");
        String day = request.getParameter("day");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String tutor = request.getParameter("tutor");
        ArrayList<String> errors = new ArrayList<>();
        System.out.println(level + " " + subject + " " + startTime + " " + endTime + " " + tutor);
        if(level == null || level.isEmpty()) {
            errors.add("Please select level");
        }
        
        if(subject == null || subject.isEmpty()) {
            errors.add("Please select subject");
        }
        if(day == null || day.isEmpty()) {
            errors.add("Please select day");
        }
        
        if(startTime == null || startTime.isEmpty()) {
            errors.add("Please select start time");
        }
        
        if(endTime == null || endTime.isEmpty()) {
            errors.add("Please select end time");
        }
        
        if(!errors.isEmpty()) {
            System.out.println("HEREEEE");
            request.setAttribute("errors", errors);
            RequestDispatcher view = request.getRequestDispatcher("UpdateSchedule.jsp");
            view.forward(request,response);
        }else{
            if(tutor != null || !tutor.isEmpty()){
                System.out.println("HALPPPP");
                LessonDAO lesson = new LessonDAO();
                lesson.updateLesson(tutor, level, subject);
            }
            String timing = day + " " + startTime + "-" + endTime;
            ClassDAO cDAO = new ClassDAO();
            boolean status = cDAO.updateClass(level, subject, timing);
            System.out.println(status);
            request.setAttribute("status", status);
            RequestDispatcher view = request.getRequestDispatcher("UpdateSchedule.jsp");
            view.forward(request,response);
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
