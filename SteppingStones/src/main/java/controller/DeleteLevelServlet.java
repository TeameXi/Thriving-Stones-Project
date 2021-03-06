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
import model.SubjectDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "DeleteLevelServlet", urlPatterns = {"/DeleteLevelServlet"})
public class DeleteLevelServlet extends HttpServlet {

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
        
        try(PrintWriter out = response.getWriter()){
            if(!"".equals(request.getParameter("subjectID"))){
                int subjectID = Integer.parseInt(request.getParameter("subjectID"));
                int branchID = Integer.parseInt(request.getParameter("branchID"));
                String level_id = request.getParameter("levelID");
                if(level_id.contains(",")){
                    //Combined class
                    //System.out.println("levelID " + level_id + " subjectID" + subjectID + "branchID" + branchID);
                    boolean status = SubjectDAO.deleteSubjectLevelRelForCombined(level_id,subjectID,branchID);
                    if (status == true) {
                        out.println(1);
                    } else {
                        out.println(0);
                    }
                }else{
                    // Normal class
                    int levelID = Integer.parseInt(level_id);
                    //System.out.println("levelID " + levelID + " subjectID" + subjectID + "branchID" + branchID);
                    SubjectDAO subjectDAO = new SubjectDAO();
                    boolean status =  subjectDAO.deleteSubjectLevelRel(subjectID, levelID, branchID);
                    if (status == true) {
                        out.println(1);
                    } else {
                        out.println(0);
                    }
                }
            }else{
                out.println(0);
            }
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
