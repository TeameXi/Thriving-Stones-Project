/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Student;
import entity.Tutor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LessonDAO;
import model.LevelDAO;
import model.ParentChildRelDAO;
import model.ParentDAO;
import model.PaymentDAO;
import model.StudentClassDAO;
import model.StudentDAO;
import model.StudentGradeDAO;
import model.TutorHourlyRateDAO;
import model.UsersDAO;

/**
 *
 * @author Zang Yu
 */
@WebServlet(name = "PromoteStudentServlet", urlPatterns = {"/PromoteStudentServlet"})
public class PromoteStudentServlet extends HttpServlet {


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
        
        
        PrintWriter out = response.getWriter();
        
        String selStuID = request.getParameter("selStuID");
        String[] selStuList = selStuID.split(",");
        if(selStuList != null){
            for(String studentStr: selStuList){                
                int studentID = Integer.parseInt(studentStr);
                Student student = StudentDAO.retrieveStudentbyID(studentID);
                String level = student.getLevel();                    
                int levelID = LevelDAO.retrieveLevelID(level);
                if(levelID<11){
                    double totalDeposit = 0;                
                    totalDeposit = StudentClassDAO.retrieveStudentTotalDepositAmt(studentID,0);
                    if (totalDeposit>0){
                        boolean updateClassStudentRelStatus=updateClassStudentRelStatus =StudentClassDAO.updateStatus(studentID,0);
                        boolean updateReqAmt = StudentDAO.updateStudentFees(studentID,totalDeposit,student.getOutstandingAmt());
                    }

                        boolean update = StudentDAO.promoteStudentLevel(studentID, levelID+1);
                        if(update){                        
                            out.println(1);
                        }else{
                            out.println(0);
                        }
                }else{
                    out.println(2);
                }
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
