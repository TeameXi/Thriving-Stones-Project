/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Branch;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import entity.Class;
import entity.Lesson;
import entity.Users;
import javax.servlet.RequestDispatcher;
import model.BranchDAO;
import model.LessonDAO;

/**
 *
 * @author Riana
 */
@WebServlet(name = "RetrieveScheduleServlet", urlPatterns = {"/RetrieveScheduleServlet"})
public class RetrieveScheduleServlet extends HttpServlet {

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
        
        Users user = (Users)request.getSession(false).getAttribute("user");
        
        //retrieve class list
        ArrayList<Class> classList = new ArrayList<Class>();
        if(user.getBranchId() == 0){
            BranchDAO branchDAO = new BranchDAO();
            List<Branch> branchList = branchDAO.retrieveAllBranches();
            
            for(Branch b:branchList){
                classList.addAll(ClassDAO.listAllClasses(b.getBranchId()));
            }
        }else{
            classList = ClassDAO.listAllClasses(user.getBranchId());
        }
        
        //retrieve lesson list
        LessonDAO lessonDAO = new LessonDAO();
        List<Lesson> lessonList = new ArrayList<Lesson>();
        for(Class c:classList){
            List<Lesson> lessons = lessonDAO.retrieveAllLessonLists(c.getClassID());
            lessonList.addAll(lessons);
        }
        
        request.setAttribute("ClassList", classList);
        request.setAttribute("LessonList", lessonList);
        
        RequestDispatcher view=request.getRequestDispatcher("DisplaySchedules.jsp");
        view.forward(request,response);
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
