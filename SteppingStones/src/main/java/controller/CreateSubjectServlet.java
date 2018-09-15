/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
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
              
        String branch = request.getParameter("branch");
        int branchID = 0;
        if(branch != null && !branch.equals("")){
            branchID = Integer.parseInt(branch);
        }
        String subjectName = request.getParameter("subjectName");
        String[] level = request.getParameterValues("level_id[]");
        String[] fee = request.getParameterValues("subject_cost[]");
        boolean subjectStatus = false;
        if(level != null){
            for(int i=0;i<level.length;i++){
                int levelID = Integer.parseInt(level[i]);
                double monthlyFee  = 0;
                if(!fee[i].equals("")){
                    monthlyFee = Double.parseDouble(fee[i]);
                }
                
                subjectStatus = SubjectDAO.addSubject(levelID, subjectName, branchID,monthlyFee);
            }    
        }
        if(subjectStatus) {
            request.setAttribute("status", "Subject created successfully!");
            RequestDispatcher view = request.getRequestDispatcher("DisplaySubjects.jsp");
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
