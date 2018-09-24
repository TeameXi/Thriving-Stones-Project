/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Level;
import entity.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LevelDAO;
import model.StudentDAO;

/**
 *
 * @author Zang Yu
 */
@WebServlet(name = "AutoPromoteServlet", urlPatterns = {"/AutoPromoteServlet"})
public class AutoPromoteServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        boolean status = false;
        if(request.getParameter("search")!=null){
            String branch = request.getParameter("branch");
            int branchID = 0;
            if(branch != null && !branch.equals("")){
                branchID = Integer.parseInt(branch);
            }
            out.println(branchID);
            String[] level = request.getParameterValues("level");
            boolean contains = Arrays.asList(level).contains("0");
            ArrayList<Student> allSelectedStudents = new ArrayList<Student>();
           
            if(level != null && !contains){
                for(String lvl: level){
                    int levelID = Integer.parseInt(lvl);                    
                    int totalNumber = StudentDAO.retrieveNumberOfStudentByLevel(levelID);
                    if(totalNumber > 0){
                       ArrayList<Student> students = StudentDAO.listAllStudentsByLimit(branchID, levelID, 0, totalNumber);
                       allSelectedStudents.addAll(students); 
                       status = true;
                    }
                }   
            }
            if(level != null && contains){
                ArrayList<Level> allLevels = LevelDAO.retrieveAllLevelLists();
                for(int i=0; i<allLevels.size(); i++ ){
                    Level lvl = allLevels.get(i);                
                    int totalNumber = StudentDAO.retrieveNumberOfStudentByLevel(lvl.getLevel_id());
                    if(totalNumber > 0){
                       ArrayList<Student> students = StudentDAO.listAllStudentsByLimit(branchID, lvl.getLevel_id(), 0, totalNumber);
                       allSelectedStudents.addAll(students); 
                       status = true;
                    }
                }   
            }
            if(status) {
                
            }else{
                request.setAttribute("errorMsg", "No student found!");
            }
            request.setAttribute("selectedStudents",allSelectedStudents );
            RequestDispatcher view = request.getRequestDispatcher("AutoPromote.jsp");
            view.forward(request,response);
        }
        if (request.getParameter("promote")!=null){
            String[] studentValue = request.getParameterValues("studentValue");
            if(studentValue != null){
                for(String stuValue: studentValue){
                    int stuID = Integer.parseInt(stuValue);
                    Student student = StudentDAO.retrieveStudentbyID(stuID);
                    
                    out.println("student id" +stuID);
                    out.println("name" +student.getName());
                    String level = student.getLevel();
                    
                    int levelID = LevelDAO.retrieveLevelID(level);                    
                    boolean update = StudentDAO.promoteStudentLevel(stuID, levelID+1);
                    if(update){
                            request.setAttribute("status", "Successfully Promoted.");
                   }
                }
            }
            
            RequestDispatcher view = request.getRequestDispatcher("AutoPromote.jsp");
            view.forward(request,response);
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
