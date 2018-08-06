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
import model.SubjectDAO;

/**
 *
 * @author HuiXin
 */
@WebServlet(name = "CreateSubjectServlet", urlPatterns = {"/CreateSubjectServlet"})
public class CreateSubjectServlet extends HttpServlet {

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
        int levelID = 0;
        int branchID = 0;
        String subjectName = request.getParameter("subjectName");
        String branch = request.getParameter("branch");
        if(level != null){
            levelID = Integer.parseInt(level);
        }
        if(level != null){
            branchID = Integer.parseInt(level);
        }
        System.out.println(level + " " + subjectName + " " + branch);
        
        SubjectDAO subjects = new SubjectDAO();
        boolean subjectStatus = subjects.addSubject(levelID, subjectName, branchID);
        
        if(subjectStatus) {
            request.setAttribute("status", "Subject created successfully!");
            RequestDispatcher view = request.getRequestDispatcher("CreateSubject.jsp");
            view.forward(request,response);
        }else{
            request.setAttribute("errorMsg", "Error creating subject!");
            RequestDispatcher view = request.getRequestDispatcher("CreateSubject.jsp");
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
