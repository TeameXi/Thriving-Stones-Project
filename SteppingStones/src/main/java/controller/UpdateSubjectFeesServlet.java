/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Level;
import entity.Subject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LevelDAO;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "UpdateSubjectFeesServlet", urlPatterns = {"/UpdateSubjectFeesServlet"})
public class UpdateSubjectFeesServlet extends HttpServlet {

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
            String action = request.getParameter("action");
            
            if(action.equals("retrieve")){
                int branchID = Integer.parseInt(request.getParameter("branchID"));
                JSONArray array = new JSONArray();
                ArrayList<Level> levels = new LevelDAO().retrieveAllLevelLists();
                
                for(Level l : levels){
                    JSONObject obj = new JSONObject();
                    obj.put("id", l.getLevel_id());
                    obj.put("name", l.getLevelName());
                    obj.put("subjects", LevelDAO.retrieveAllSubjectsBelongToLevelAndBranch(l.getLevel_id(), branchID).size());
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
            } else if (action.equals("retrieveSubjects")){
                int branchID = Integer.parseInt(request.getParameter("branchID"));
                int levelID = Integer.parseInt(request.getParameter("levelID"));
                
                JSONArray array = new JSONArray();
                ArrayList<Subject> subjects = LevelDAO.retrieveAllSubjectsBelongToLevelAndBranch(levelID, branchID);
                
                for(Subject s : subjects){
                    JSONObject obj = new JSONObject();
                    obj.put("id", s.getSubjectId());
                    obj.put("name", s.getSubjectName());
                    obj.put("fees", "<input class='form-control' style='text-align: center;' type='text' id='update_fees" + s.getSubjectId() + "' name='updatedFees' value='" + s.getFees() + "'></input>");
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
            } else if (action.equals("update")){
                int branchID = Integer.parseInt(request.getParameter("branchID"));
                int levelID = Integer.parseInt(request.getParameter("levelID"));
                int subjectID = Integer.parseInt(request.getParameter("subjectID"));
                float fees = Float.parseFloat(request.getParameter("fees"));
                
                boolean status = new LevelDAO().updateSubjectFees(branchID, subjectID, levelID, fees);
                
                
                
                JSONObject toReturn = new JSONObject().put("data", status);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("combineClassUpdate")){
                String costStr = request.getParameter("cost");
                double cost = 0;
                if(!costStr.equals("")){
                    cost = Double.parseDouble(costStr);
                }
                
                int branch = Integer.parseInt(request.getParameter("branchId"));
             
                String [] ids = request.getParameter("id").split("_");
                System.out.println("SErvlet Side");
                System.out.println(cost);
                int subjectId = Integer.parseInt(ids[0]);
                String levelIds = ids[1];
               
                boolean status = LevelDAO.updateSubjectFeesForCombineClass(branch, subjectId, levelIds, cost);
                out.println(status);
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
