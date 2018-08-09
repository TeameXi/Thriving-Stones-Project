/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Level;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LevelDAO;
import model.SubjectDAO;
import org.json.JSONObject;

/**
 *
 * @author MOH MOH SAN
 */
@WebServlet(name = "RetrieveSubjectServlet", urlPatterns = {"/RetrieveSubjectServlet"})
public class RetrieveSubjectServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
          
            JSONObject obj = new JSONObject();
            int subjectID = Integer.parseInt(request.getParameter("subjectID"));
            int branchID = Integer.parseInt(request.getParameter("branchID"));
            SubjectDAO subjects = new SubjectDAO();
            String subjectName = subjects.retrieveSubject(subjectID);

            ArrayList<Level> levelLists = LevelDAO.retrieveLevelBySubject1(subjectID, branchID);
            
            ArrayList<String> lvlNames = new ArrayList<>();
            ArrayList<Integer> lvlIds = new ArrayList<>();
            for(Level l : levelLists){
                lvlNames.add(l.getLevelName());
                lvlIds.add(l.getLevel_id());
            }
            if(subjectName != null){
                obj.put("branch_id", branchID);
                obj.put("id",subjectID);
                obj.put("name",subjectName);
                obj.put("lvl_names",lvlNames);
                obj.put("lvl_ids",lvlIds);

            }
            
            out.println(obj);
       
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
