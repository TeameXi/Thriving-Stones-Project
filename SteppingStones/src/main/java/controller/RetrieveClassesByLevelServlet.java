/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Class;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "RetrieveClassesByLevelServlet", urlPatterns = {"/RetrieveClassesByLevelServlet"})
public class RetrieveClassesByLevelServlet extends HttpServlet {

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
          
            JSONArray array = new JSONArray();
            
            int levelID = Integer.parseInt(request.getParameter("levelID"));
            int branchID = Integer.parseInt(request.getParameter("branchID"));

            ArrayList<Class> classList = ClassDAO.getCombinedClassesByLevel(branchID, levelID);
            ClassDAO.getClassesByLevel(levelID, branchID, classList);
            
            if(classList != null || !classList.isEmpty()){
                for(Class c: classList){
                    int classID = c.getClassID();
                    String name;
                    if(c.getType().equals("P")){
                        name = c.getSubject() + " (" + c.getClassDay() + " " + c.getStartTime()+ "-" + c.getEndTime() + ")" + " - Premium Class";
                    }else{
                        name = c.getSubject() + " (" + c.getClassDay() + " " + c.getStartTime()+ "-" + c.getEndTime() + ")";
                    }
                    
                    JSONObject obj = new JSONObject();
                    obj.put("class", classID);
                    obj.put("name", name);
                    
                    array.put(obj);
                }
                String json = array.toString();
                //System.out.println(json);
                out.println(json);
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
