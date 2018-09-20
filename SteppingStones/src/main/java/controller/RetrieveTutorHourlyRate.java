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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TutorHourlyRateDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author MOH MOH SAN
 */
@WebServlet(name = "RetrieveTutorHourlyRate", urlPatterns = {"/RetrieveTutorHourlyRate"})
public class RetrieveTutorHourlyRate extends HttpServlet {

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
            int branchId = 0;
            int levelId = 0;
            int subjectId = 0;
            if(!request.getParameter("branch_id").equals("")){
                branchId = Integer.parseInt(request.getParameter("branch_id"));
            } 
            
            if(!request.getParameter("level_id").equals("")){
                levelId = Integer.parseInt(request.getParameter("level_id"));
            }
            
            if(!request.getParameter("subject_id").equals("")){
                subjectId = Integer.parseInt(request.getParameter("subject_id"));
            }
            
            if(branchId == 0 || levelId == 0 || subjectId == 0){
                out.println(-1);
            }else{
                
                JSONObject parentObj = new JSONObject();
                ArrayList<Tutor> newtutorPayLists =  TutorHourlyRateDAO.tutorListNotInPayTable(branchId, subjectId, levelId);
                JSONArray newtutorJSONPayLists = new JSONArray();
                
                for(Tutor t:newtutorPayLists){
                    JSONObject tObj = new JSONObject();
                    tObj.put("id", t.getTutorId());
                    tObj.put("name", t.getName());
                    
                    newtutorJSONPayLists.put(tObj);
                }
                parentObj.put("newTutor", newtutorJSONPayLists);
                
                
                ArrayList<Tutor> existingtutorPayLists =  TutorHourlyRateDAO.tutorListInPayTable(branchId, subjectId, levelId);
                JSONArray existingtutorJSONPayLists = new JSONArray();
                for(Tutor t:existingtutorPayLists){
                    JSONObject tObj = new JSONObject();
                    tObj.put("id", t.getTutorId());
                    tObj.put("name", t.getName());
                    tObj.put("pay",t.getPay());
                    
                    existingtutorJSONPayLists.put(tObj);
                }
                parentObj.put("oldTutor", existingtutorJSONPayLists);
                
                
                out.println(parentObj);
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
