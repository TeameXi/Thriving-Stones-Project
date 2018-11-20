/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import model.StudentGradeDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "CreateTuitionGradeServlet", urlPatterns = {"/CreateTuitionGradeServlet"})
public class CreateTuitionGradeServlet extends HttpServlet {

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
            String dataArr = request.getParameter("grade_arr");
//            System.out.println(dataArr);
            if(!dataArr.equals("")){
                JSONArray studentArr = new JSONArray(dataArr);
                ArrayList<String>gradeValues = new ArrayList<>();
              
                for(int i =0; i < studentArr.length(); i++){
                    String sqlOneRecord = "";
                    JSONObject currObj = studentArr.getJSONObject(i);
                    
                    int studentId = currObj.getInt("student_id");
                    int classId = currObj.getInt("class_id");
                    
                    double CA1_0_top = currObj.getDouble("CA1_0_top");
                    int CA1_0_base = currObj.getInt("CA1_0_base");
                    double CA1_0_grade = 0;
                    if(CA1_0_base > 0){
                        CA1_0_grade = (double)CA1_0_top/CA1_0_base;
                    }
                    
                    double SA1_0_top = currObj.getDouble("SA1_0_top");
                    int SA1_0_base = currObj.getInt("SA1_0_base");
                    double SA1_0_grade = 0;
                    if(SA1_0_base > 0){
                        SA1_0_grade = (double)SA1_0_top/SA1_0_base;
                    }
                    
                    double CA2_0_top = currObj.getDouble("CA2_0_top");
                    int CA2_0_base = currObj.getInt("CA2_0_base");
                    double CA2_0_grade = 0;
                    if(CA2_0_base > 0){
                        CA2_0_grade = (double)CA2_0_top/CA2_0_base;
                    }
                    
                    double SA2_0_top = currObj.getDouble("SA2_0_top");
                    int SA2_0_base = currObj.getInt("SA2_0_base");
                    double SA2_0_grade = 0;
                    if(SA2_0_base > 0){
                        SA2_0_grade = (double)SA2_0_top/SA2_0_base;
                    }
                    
                    // For school
                    double CA1_1_top = currObj.getDouble("CA1_1_top");
                    int CA1_1_base = currObj.getInt("CA1_1_base");
                    double CA1_1_grade = 0;
                    if(CA1_1_base > 0){
                        CA1_1_grade = (double)CA1_1_top/CA1_1_base;
                    }
                    
                    double SA1_1_top = currObj.getDouble("SA1_1_top");
                    int SA1_1_base = currObj.getInt("SA1_1_base");
                    double SA1_1_grade = 0;
                    if(SA1_1_base > 0){
                        SA1_1_grade = (double)SA1_1_top/SA1_1_base;
                    }

                    double CA2_1_top = currObj.getDouble("CA2_1_top");
                    int CA2_1_base = currObj.getInt("CA2_1_base");
                    double CA2_1_grade = 0;
                    if(CA2_1_base > 0){
                        CA2_1_grade = (double)CA2_1_top/CA2_1_base;
                    }
                    
                    double SA2_1_top = currObj.getDouble("SA2_1_top");
                    int SA2_1_base = currObj.getInt("SA2_1_base");
                    double SA2_1_grade = 0;
                    if(SA2_1_base > 0){
                        SA2_1_grade = (double)SA2_1_top/SA2_1_base;
                    }
                    
                    int level = currObj.getInt("level");
                    
                    sqlOneRecord +="("+studentId+","+classId+","
                            +CA1_0_top+","+CA1_0_base+","+CA1_0_grade+","
                            +SA1_0_top+","+SA1_0_base+","+SA1_0_grade+","
                            +CA2_0_top+","+CA2_0_base+","+CA2_0_grade+","
                            +SA2_0_top+","+SA2_0_base+","+SA2_0_grade+","
                            +CA1_1_top+","+CA1_1_base+","+CA1_1_grade+","
                            +SA1_1_top+","+SA1_1_base+","+SA1_1_grade+","
                            +CA2_1_top+","+CA2_1_base+","+CA2_1_grade+","
                            +SA2_1_top+","+SA2_1_base+","+SA2_1_grade+","+level+")";
                    gradeValues.add(sqlOneRecord);
//                    System.out.println(sqlOneRecord);
                }
                
                if(gradeValues.size() > 0){
                    if(StudentGradeDAO.massSaveTutionGrades(gradeValues)){
                        out.println(1);
                    }else{
                        out.println(-1);
                    }
                }else{
                    out.println(-1);   
                }
          
            }else{
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
