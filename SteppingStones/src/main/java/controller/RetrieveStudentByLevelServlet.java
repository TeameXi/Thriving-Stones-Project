/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Student;
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
import model.LevelDAO;
import model.StudentDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "RetrieveStudentByLevelServlet", urlPatterns = {"/RetrieveStudentByLevelServlet"})
public class RetrieveStudentByLevelServlet extends HttpServlet {

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
        response.setContentType("text/html; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
          
            JSONArray array = new JSONArray();
            
            int classID = Integer.parseInt(request.getParameter("classID"));
            Class cls = ClassDAO.getClassByID(classID);
            int levelID = LevelDAO.retrieveLevelID(cls.getLevel());
            ArrayList<Student> studentList = StudentDAO.listStudentsByLevelNotEnrolledInSpecificClassYet(levelID, classID);
            
            if(studentList != null || !studentList.isEmpty()){
                for(Student s: studentList){
                    int studentID = s.getStudentID();
                    String studentName = s.getName();
                    
                    JSONObject obj = new JSONObject();
                    obj.put("student", studentID);
                    obj.put("name", studentName);
                    obj.put("type", cls.getType());
                    
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
