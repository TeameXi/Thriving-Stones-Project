/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import model.TutorDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "PaginationTutorServlet", urlPatterns = {"/PaginationTutorServlet"})
public class PaginationTutorServlet extends HttpServlet {

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
        
        
        String pageIdStr = request.getParameter("page");
        String branchIdStr = request.getParameter("branch");
        String toShowStr = request.getParameter("toShow");
        int pageId = 0;
        if(pageIdStr != null){
            pageId = Integer.parseInt(pageIdStr);
        }
        int branchId = 0;
        if(branchIdStr != null){
            branchId = Integer.parseInt(branchIdStr);
        }
        int toShow = 0;
        if(toShowStr != null){
            toShow = Integer.parseInt(toShowStr);
        }
        int start = pageId * toShow - toShow;
        
        TutorDAO tutorDao = new TutorDAO();
        ArrayList<Tutor> tutors;
        if(pageId == 1){
            tutors = tutorDao.retrieveAllTutorsByLimit(0,toShow, branchId);
        }else{
            tutors = tutorDao.retrieveAllTutorsByLimit(start,toShow, branchId);
        }

        request.setAttribute("tutors", tutors);
        request.setAttribute("id", pageIdStr);
        RequestDispatcher view = request.getRequestDispatcher("DisplayTutors.jsp");
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
