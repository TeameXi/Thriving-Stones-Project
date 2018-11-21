/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
@WebServlet(name = "CreateTutorHourlyRate", urlPatterns = {"/CreateTutorHourlyRate"})
public class CreateTutorHourlyRate extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            String action = request.getParameter("action");
            String dataArr = request.getParameter("pay_rate_arr");
            JSONArray tutorPayArr = new JSONArray(dataArr);
            if(!dataArr.equals("") && action.equals("individual")){
//                System.out.println("Individual");
                // individual level case
                
                ArrayList<String>payRateRecord = new ArrayList<>();
                for(int i =0; i < tutorPayArr.length(); i++){
                    String sqlOneRecord = "";
                    JSONObject currObj = tutorPayArr.getJSONObject(i);
                    int tutorId = currObj.getInt("id");
                    int levelId = currObj.getInt("level_id");
                    int subjectId = currObj.getInt("subject_id");
                    int branchId = currObj.getInt("branch_id");
                    double payRate = currObj.getDouble("hourly_pay");
                    sqlOneRecord +="("+tutorId+","+levelId+","
                            +subjectId+","+branchId+","+payRate+")";
                    payRateRecord.add(sqlOneRecord);
                }

                if(payRateRecord.size() > 0){
                    if(TutorHourlyRateDAO.massUpdateTutorsHourlyRate(payRateRecord)){
                        out.println(1);
                    }else{
                        out.println(-1);
                    }
                }else{
                    out.println(-1);   
                }
          
            }else if(!dataArr.equals("") && action.equals("combined")){
                // combined case
                ArrayList<String>payRateRecord = new ArrayList<>();
                for(int i =0; i < tutorPayArr.length(); i++){
                    String sqlOneRecord = "";
                    JSONObject currObj = tutorPayArr.getJSONObject(i);
                    int tutorId = Integer.parseInt(currObj.getString("id"));
                    String levelIds = currObj.getString("level_id");
                    if (levelIds.endsWith(",")) {
                        levelIds = levelIds.substring(0, levelIds.length() - 1);
                    }

                    
                    int levelId = Integer.parseInt(levelIds.split(",")[0]);
                    int subjectId = currObj.getInt("subject_id");
                    int branchId = currObj.getInt("branch_id");
                    double payRate = currObj.getDouble("hourly_pay");
                    sqlOneRecord +="("+tutorId+","+levelId+","
                            +subjectId+","+branchId+","+payRate+",'"+levelIds+"',1)";
                    payRateRecord.add(sqlOneRecord);
                }

                if(payRateRecord.size() > 0){
                    if(TutorHourlyRateDAO.massUpdateTutorsHourlyRateForCombine(payRateRecord)){
                        out.println(1);
                    }else{
                        out.println(-1);
                    }
                }else{
                    out.println(-1);   
                }
            }
            else{
                out.println(-1);  
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
