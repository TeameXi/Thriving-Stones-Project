/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Reward;
import entity.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.LevelDAO;
import model.RewardDAO;
import model.StudentDAO;

/**
 *
 * @author Desmond
 */
@WebServlet(name = "TutorRewardServlet", urlPatterns = {"/TutorRewardServlet"})
public class TutorRewardServlet extends HttpServlet {

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
        if(request.getParameter("branch_id") == null){
            response.sendRedirect("TutorReward.jsp");
            return;
        }
        int branchID = Integer.parseInt(request.getParameter("branch_id"));
       if(request.getParameter("search") != null){
            String student = (String) request.getParameter("student");
            String[] parts = student.split("-");
            String studentName = "";
            String studentEmail = "";
            int phone = 0;
            
            if(parts.length == 2){
                studentName = parts[0].trim();
                if(parts[1].contains("@")){
                    studentEmail = parts[1].trim();
                }else{
                    phone = Integer.parseInt(parts[1].trim());
                }
            }
//            System.out.println(phone + "& " + studentEmail);
            int studentID = 0;
            if(studentEmail.isEmpty()){
                studentID = StudentDAO.retrieveStudentIDWithPhone(studentName, phone);
            }else{
                studentID = StudentDAO.retrieveStudentIDWithEmail(studentName, studentEmail);
            }
//            System.out.println(studentID);
            int levelID = StudentDAO.retrieveStudentLevelbyID(studentID,branchID);
            if(levelID == 0){
                request.setAttribute("errorMsg", studentName + " Not Exists in Database, Please Create Student First.");           
            }else{
                List<Reward> rewardList = RewardDAO.listAllRewardsByStudent(studentID);
                request.setAttribute("rewardList", rewardList);
                request.setAttribute("level", LevelDAO.retrieveLevel(levelID));    
                request.setAttribute("studentName", studentName);
                request.setAttribute("student_id", studentID);
                
            }
            RequestDispatcher view = request.getRequestDispatcher("TutorReward.jsp");
            view.forward(request, response);
        }
        
       if(request.getParameter("amount") != null){
           int student_id = Integer.parseInt(request.getParameter("studentiiD"));
           int tutor_id = Integer.parseInt(request.getParameter("tutorid"));
           String description = request.getParameter("description");
           int amount = Integer.parseInt(request.getParameter("amount"));
           
           int status = RewardDAO.insertReward(student_id, tutor_id, description, amount);
           List<Reward> rewardList = RewardDAO.listAllRewardsByStudent(student_id);
           Student student = StudentDAO.retrieveStudentbyID(student_id);
            request.setAttribute("rewardList", rewardList);
            request.setAttribute("level", student.getLevel());    
            request.setAttribute("studentName", student.getName());
            request.setAttribute("student_id", student_id);
            
            if(status == 0){
                request.setAttribute("errorMsg", "Error occurred when adding rewards"); 
            }
             
            RequestDispatcher view = request.getRequestDispatcher("TutorReward.jsp");
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
