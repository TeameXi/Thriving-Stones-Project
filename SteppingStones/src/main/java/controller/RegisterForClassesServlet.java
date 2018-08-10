/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.StudentDAO;
import entity.Class;
import entity.Student;
import javax.servlet.RequestDispatcher;
import model.LevelDAO;
import model.StudentClassDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "RegisterForClassesServlet", urlPatterns = {"/RegisterForClassesServlet"})
public class RegisterForClassesServlet extends HttpServlet {

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
        int branchID = Integer.parseInt(request.getParameter("branch_id"));
        if(request.getParameter("search") != null){
            String studentName = (String) request.getParameter("studentName");
           
            int levelID = StudentDAO.retrieveStudentLevelbyName(studentName,branchID);
            int studentID = StudentDAO.retrieveStudentID(studentName);
            if(levelID == 0){
                request.setAttribute("errorMsg", studentName + " Not Exists in Database, Please Create Student First.");           
            }else{
                ArrayList<Class> cls = ClassDAO.getClassesToEnrolled(levelID, studentID, branchID);
                ArrayList<Class> enrolledCls = ClassDAO.getStudentEnrolledClass(studentID);
                request.setAttribute("level", LevelDAO.retrieveLevel(levelID));
                request.setAttribute("studentName", studentName);
                request.setAttribute("classes", cls);
                request.setAttribute("enrolledClasses", enrolledCls);
            }
            RequestDispatcher view = request.getRequestDispatcher("RegisterForClasses.jsp");
            view.forward(request, response);
        }
        if(request.getParameter("select") != null){
            String[] classValues = request.getParameterValues("classValue");
            String studentName = request.getParameter("studentName");
            int studentID = StudentDAO.retrieveStudentID(studentName);
            System.out.println(studentName);
            if(classValues != null){
                for(String classValue: classValues){
                    int classID = Integer.parseInt(classValue);
                    boolean status = StudentClassDAO.saveStudentToRegisterClass(classID, studentID);
                    System.out.println(status);
                    if(status){
                        Class cls = ClassDAO.getClassByID(classID);
                        Student stu = StudentDAO.retrieveStudentbyID(studentID,branchID);
                        double reqAmt = (cls.getMthlyFees() * 3) + stu.getReqAmt(); //reqAmt meaning 1 mth or for whole term how to calculate
                        double outstandingAmt = (cls.getMthlyFees() * 3) + stu.getOutstandingAmt();
                        boolean update = StudentDAO.updateStudentFees(studentID, reqAmt, outstandingAmt); 
                        if(update){
                            request.setAttribute("status", "Successfully Registered.");
                        }
                    }
                }
            }
            RequestDispatcher view = request.getRequestDispatcher("RegisterForClasses.jsp");
            view.forward(request, response);
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
