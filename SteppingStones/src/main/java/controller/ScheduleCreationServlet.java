/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Subject;
import entity.Tutor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.SubjectDAO;
import model.TutorDAO;

/**
 *
 * @author Riana
 */
@WebServlet(name = "ScheduleCreationServlet", urlPatterns = {"/ScheduleCreationServlet"})
public class ScheduleCreationServlet extends HttpServlet {

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
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        String[] time = {"08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00"};
        String[] arr = request.getParameterValues("p");
        String startDate = request.getParameter("startDate");
        System.out.println(arr);

        if (startDate == null) {
            request.setAttribute("Status", "Start Date is required");

            //retrive all subject
            SubjectDAO subjectDAO = new SubjectDAO();
            List<Subject> subjectList = subjectDAO.listAllSubjects();

            request.setAttribute("SubjectList", subjectList);
            RequestDispatcher view = request.getRequestDispatcher("ScheduleCreation.jsp");
            view.forward(request, response);
        }

        if (arr == null || arr.length == 0) {
            request.setAttribute("Status", "No Schedule is created");

            //retrive all subject
            SubjectDAO subjectDAO = new SubjectDAO();
            List<Subject> subjectList = subjectDAO.listAllSubjects();

            request.setAttribute("SubjectList", subjectList);
            RequestDispatcher view = request.getRequestDispatcher("ScheduleCreation.jsp");
            view.forward(request, response);
        }

        try {
            for (String a : arr) {
                //index 0 => subject Id
                //index 1 => level + redips clone Id
                //index 2 => time(row)
                //index 3 => day(column)
                String[] resultList = a.split("_");
                //remove redips clone Id from level
                String level = resultList[1].substring(0, resultList[1].length() - 2);
                String subject = resultList[0];
                String classDay = days[Integer.parseInt(resultList[3])];
                String classTime = time[Integer.parseInt(resultList[2])] + " - " + time[Integer.parseInt(resultList[2]) + 1];

                ClassDAO.saveClasses(level, subject, classTime, classDay, 0, startDate);
            }
            request.setAttribute("Status", "Schedule Created");

            //retrive all subject
            SubjectDAO subjectDAO = new SubjectDAO();
            List<Subject> subjectList = subjectDAO.listAllSubjects();

            request.setAttribute("SubjectList", subjectList);
            RequestDispatcher view = request.getRequestDispatcher("ScheduleCreation.jsp");
            view.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("Status", "Error encountered when creating schedule");

            //retrive all subject
            SubjectDAO subjectDAO = new SubjectDAO();
            List<Subject> subjectList = subjectDAO.listAllSubjects();

            request.setAttribute("SubjectList", subjectList);
            RequestDispatcher view = request.getRequestDispatcher("ScheduleCreation.jsp");
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
