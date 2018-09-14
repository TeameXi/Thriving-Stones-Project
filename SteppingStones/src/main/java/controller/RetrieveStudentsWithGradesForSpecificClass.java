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
@WebServlet(name = "RetrieveStudentsWithGradesForSpecificClass", urlPatterns = {"/RetrieveStudentsWithGradesForSpecificClass"})
public class RetrieveStudentsWithGradesForSpecificClass extends HttpServlet {

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
            String class_id = request.getParameter("class_id");
            if(class_id != null && !class_id.equals("")){

                int classID = Integer.parseInt(class_id);
                ArrayList<Grade> gradeList = StudentGradeDAO.listGradesFromSpecificClass(classID);
                
                JSONArray studentsObjListsForClass = new JSONArray();
                
                for(Grade g:gradeList){
                    JSONObject studentObj = new JSONObject();
                    studentObj.put("studentId", g.getStudentId());
                    studentObj.put("studentName",g.getStudentName());
                    studentObj.put("classId", g.getClass_id());
                    studentObj.put("CA1_0", g.getCA1_tuition_top()+"/"+g.getCA1_tuition_base());
                    studentObj.put("SA1_0",g.getSA1_tuition_top()+"/"+g.getSA1_tuition_base());
                    studentObj.put("CA2_0",g.getCA2_tuition_top()+"/"+g.getCA2_tuition_base());
                    studentObj.put("SA2_0",g.getSA2_tuition_top()+"/"+g.getSA2_tuition_base());

                    studentObj.put("CA1_1",g.getCA1_school_top()+"/"+g.getCA1_school_base());
                    studentObj.put("SA1_1",g.getSA1_school_top()+"/"+g.getSA1_school_base());
                    studentObj.put("CA2_1",g.getCA2_school_top()+"/"+g.getCA2_school_base());
                    studentObj.put("SA2_1",g.getSA2_school_top()+"/"+g.getSA2_school_base());
                    
                    studentsObjListsForClass.put(studentObj);
                }
                out.print(studentsObjListsForClass);
                
            }else{
                out.println("-1");
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
