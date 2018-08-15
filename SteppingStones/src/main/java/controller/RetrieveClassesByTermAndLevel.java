/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.LevelDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author MOH MOH SAN
 */
@WebServlet(name = "RetrieveClassesByTermAndLevel", urlPatterns = {"/RetrieveClassesByTermAndLevel"})
public class RetrieveClassesByTermAndLevel extends HttpServlet {

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
          
            int term = 0;
            int branch_id = 0;
            int level_id = 0;
            String level_name = request.getParameter("level");
            

            
            if(request.getParameter("term") != ""){
                term = Integer.parseInt(request.getParameter("term"));
            }
            
            if(request.getParameter("branch_id")!= ""){
                branch_id = Integer.parseInt(request.getParameter("branch_id"));
            }
          
           
            if(level_name != ""){
                level_id = LevelDAO.retrieveLevelID(level_name);
            }
           
            
            if(term == 0 || branch_id == 0 || level_id == 0){
                out.println(-1);
            }else{
                Map<String,ArrayList<entity.Class>> classMapLists= ClassDAO.groupClassesByTimingAndDay(level_id,term,branch_id,level_name);
                
                JSONArray classObjLists = new JSONArray();
                
                for (String key : classMapLists.keySet()) {
                    ArrayList<entity.Class> tempClass = classMapLists.get(key);
                }
                
//                JSONArray classObjLists =new JSONArray();
//                ArrayList<entity.Class> classLists = ClassDAO.getClassesByTermAndLevel(level_id, term, branch_id,level_name);
//                
//                
//                for(entity.Class c : classLists){
//                    int classId = c.getClassID();
//                    String subject = c.getSubject();
//                    double fees = c.getMthlyFees();
//                    String timing = c.getClassTime();
//                    String day = c.getClassDay();
//                    String start_date = c.getStartDate();
//                    String end_date = c.getEndDate();
//                    
//                  
//                    JSONObject obj = new JSONObject();
//                    obj.put("id", classId);
//                    obj.put("subject", subject);
//                    obj.put("fees", fees);
//                    obj.put("timing", timing);
//                    obj.put("day", day);
//                    obj.put("start_date", start_date);
//                    obj.put("end_date", end_date);
//                    classObjLists.put(obj);
//                }
//                
//                if(classObjLists != null &&  classObjLists.length() > 0){
//                    out.println(classObjLists);
//                }else{
//                    out.println(0);
//                }
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
