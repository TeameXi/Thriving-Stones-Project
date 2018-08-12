/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BranchDAO;
import model.LevelDAO;
import model.StudentDAO;
import model.TutorDAO;

/**
 *
 * @author Riana
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {

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
        Users user = (Users)request.getSession(false).getAttribute("user");
        String role = (String) request.getSession(false).getAttribute("role");
        if(role.equals("admin")){
            //retrieve number of branch
            BranchDAO branchDAO = new BranchDAO();
            int numberOfBranch = branchDAO.retrieveNumberOfBranch();

            //retrieve number of tutor
            TutorDAO tutorDAO = new TutorDAO();
            int numberOfTutor = tutorDAO.retrieveNumberOfTutor();

            //retrieve number of student
            StudentDAO studentDAO = new StudentDAO();
            int numberOfStudent = studentDAO.retrieveNumberOfStudent();
            
            //retrieve number of student per level
            LevelDAO levelDAO = new LevelDAO();
            Map<String, Integer> studentPerLevel = levelDAO.retrieveStudentPerLevel();
            
            request.setAttribute("NumberOfBranch", numberOfBranch);
            request.setAttribute("NumberOfTutor", numberOfTutor);
            request.setAttribute("NumberOfStudent", numberOfStudent);
            request.setAttribute("StudentPerLevel", studentPerLevel);
        }
        
        
        RequestDispatcher view=request.getRequestDispatcher("Dashboard.jsp");
        view.forward(request,response);
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
