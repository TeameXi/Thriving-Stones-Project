/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Branch;
import entity.Level;
import entity.Subject;
import entity.Tutor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BranchDAO;
import model.ClassDAO;
import model.LevelDAO;
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
<<<<<<< HEAD
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        String[] time = {"08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00"};
        String[] arr = request.getParameterValues("p");
        String startDate = request.getParameter("startDate");
        System.out.println(arr);
        /*
        if (startDate == null) {
            request.setAttribute("Status", "Start Date is required");
=======

        int branchid = 0;
        int term = 0;
        int levelid = 0;
        int subjectid = 0;
        double fee = 0.0;
        int reminderfee = "on".equals(request.getParameter("reminderfee")) ? 1 : 0;
        String timing = "";
        String classDay = "";
        String startDate = "";
        String endDate = "";

        ArrayList<String> errors = new ArrayList<>();

        if (request.getParameter("branch") == null || request.getParameter("branch").isEmpty()) {
            errors.add("Please select branch");
        } else {
            branchid = Integer.parseInt(request.getParameter("branch"));
        }
>>>>>>> 62718fa5f727adce4a818957f9ff3fac8429e663

        if (request.getParameter("term") == null || request.getParameter("term").isEmpty()) {
            errors.add("Please select term");
        } else {
            term = Integer.parseInt(request.getParameter("term"));
        }

        if (request.getParameter("level") == null || request.getParameter("level").isEmpty()) {
            errors.add("Please select level");
        } else {
            levelid = Integer.parseInt(request.getParameter("level"));
        }

        if (request.getParameter("subject") == null || request.getParameter("subject").isEmpty()) {
            errors.add("Please select subject");
        } else {
            subjectid = Integer.parseInt(request.getParameter("subject"));
        }

        if (request.getParameter("fees") == null || request.getParameter("fees").isEmpty()) {
            errors.add("Please fill in fees");
        } else {
            try{
                fee = Double.parseDouble(request.getParameter("fees"));
            }catch(NumberFormatException e){
                errors.add("fees must be in money format");
            }
        }

        if (request.getParameter("timing") == null || request.getParameter("timing").isEmpty()) {
            errors.add("Please fill in timing");
        } else {
            timing = request.getParameter("timing");
        }
        if (request.getParameter("classDay") == null || request.getParameter("classDay").isEmpty()) {
            errors.add("Please fill in class day");
        } else {
            classDay = request.getParameter("classDay");
        }

        if (request.getParameter("startDate") == null || request.getParameter("startDate").isEmpty()) {
            errors.add("Please fill in start date");
        } else {
            startDate = request.getParameter("startDate");
        }

        if (request.getParameter("endDate") == null || request.getParameter("endDate").isEmpty()) {
            errors.add("Please fill in end date");
        } else {
            endDate = request.getParameter("endDate");
        }

        if (errors.isEmpty()) {
            ClassDAO cDAO = new ClassDAO();
            boolean status = cDAO.insertClass(levelid, subjectid, term, reminderfee, branchid, timing, classDay, fee, startDate, endDate);
            if(!status){
                errors.add("create fail"); 
                request.setAttribute("errors", errors);
            }
            
        } else {
            request.setAttribute("errors", errors);
            
        }
            //Retrieve all branch
            BranchDAO branchDAO = new BranchDAO();
            List<Branch> branchList = branchDAO.retrieveBranches();

            //Retrieve all level
            LevelDAO levelDAO = new LevelDAO();
            List<Level> levelList = levelDAO.retrieveAllLevelLists();

            //retrive all subject
            SubjectDAO subjectDAO = new SubjectDAO();
            List<Subject> subjectList = subjectDAO.retrieveAllSubjectsWithId();

            request.setAttribute("BranchList", branchList);
            request.setAttribute("LevelList", levelList);
            request.setAttribute("SubjectList", subjectList);

            RequestDispatcher view = request.getRequestDispatcher("ScheduleCreation.jsp");
            view.forward(request, response);
<<<<<<< HEAD
        }
        */

=======
>>>>>>> 62718fa5f727adce4a818957f9ff3fac8429e663
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
