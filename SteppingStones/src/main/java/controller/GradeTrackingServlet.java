/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Grade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.StudentGradeDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author MOH MOH SAN
 */
@WebServlet(name = "GradeTrackingServlet", urlPatterns = {"/GradeTrackingServlet"})
public class GradeTrackingServlet extends HttpServlet {

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
            String student = request.getParameter("studentID");
            int studentID = 0;
            if(!student.equals("")){
                studentID = Integer.parseInt(student);
            }
            

            TreeMap<Integer,ArrayList<Grade>> allGradesOfStudentMap = StudentGradeDAO.listAllGradesOfIndiviudalStudent(studentID);
            int lastKey = allGradesOfStudentMap.lastKey();
            
            ArrayList<Grade> gradesForLatestLevel = allGradesOfStudentMap.get(lastKey);
            
//            JSONObject gradeObjectForEachlvl = new JSONObject();
            JSONArray gradeJSONArrForEachlvl = new JSONArray();
            
            for(Grade g: gradesForLatestLevel){
                JSONObject gradeObjForEachSubject = new JSONObject();
                
                String studentName = g.getStudentName();
                
                int levelId  = g.getLevelId();
                String levelName="Primary "+levelId;
                if(levelId > 6){
                    levelId = levelId-6;
                    levelName = "Secondary "+levelId;
                }
                
                // for Tuition Data series
                ArrayList<Double> tuition_gradeArr = new ArrayList<>();
                JSONObject tuitionSeriesObj = new JSONObject();
                tuitionSeriesObj.put("type","column");
                tuitionSeriesObj.put("name", "Tuition");
                double CA1_tuition = g.getCA1_tuition_grade();
                double SA1_tuition = g.getSA1_tuition_grade();
                double CA2_tuition = g.getCA2_tuition_grade();
                double SA2_tuition = g.getSA2_tuition_grade();
                tuition_gradeArr.add(CA1_tuition);
                tuition_gradeArr.add(SA1_tuition);
                tuition_gradeArr.add(CA2_tuition);
                tuition_gradeArr.add(SA2_tuition);
                tuitionSeriesObj.put("data",tuition_gradeArr);
                
                // for School Data series
                ArrayList<Double> school_gradeArr = new ArrayList<>();
                JSONObject schoolSeriesObj = new JSONObject();
                schoolSeriesObj.put("type","column");
                schoolSeriesObj.put("name", "School");
                double CA1_school = g.getCA1_school_grade();
                double SA1_school = g.getSA1_school_grade();
                double CA2_school = g.getCA2_school_grade();
                double SA2_school = g.getSA2_school_grade();
                school_gradeArr.add(CA1_school);
                school_gradeArr.add(SA1_school);
                school_gradeArr.add(CA2_school);
                school_gradeArr.add(SA2_school);
                schoolSeriesObj.put("data",school_gradeArr);
                
                // For Series Array
                JSONArray series = new JSONArray();
                series.put(tuitionSeriesObj);
                series.put(schoolSeriesObj);
                
                // For each object
                gradeObjForEachSubject.put("name",studentName);
                gradeObjForEachSubject.put("basicInfo",levelName+"["+g.getSubjectName()+"]");
                gradeObjForEachSubject.put("series", series);
                
                gradeJSONArrForEachlvl.put(gradeObjForEachSubject);
            }
            
            System.out.println(gradeJSONArrForEachlvl);
            out.println(gradeJSONArrForEachlvl);
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
