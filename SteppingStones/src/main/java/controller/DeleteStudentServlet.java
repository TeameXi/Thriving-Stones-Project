/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LessonDAO;
import model.ParentChildRelDAO;
import model.ParentDAO;
import model.PaymentDAO;
import model.StudentClassDAO;
import model.StudentDAO;
import model.StudentGradeDAO;
import model.UsersDAO;


/**
 *
 * @author DEYU
 */
@WebServlet(name = "DeleteStudentServlet", urlPatterns = {"/DeleteStudentServlet"})
public class DeleteStudentServlet extends HttpServlet {

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
        int studentID = Integer.parseInt(request.getParameter("studentID"));
        int parentID = ParentChildRelDAO.getParentID(studentID);
        boolean deleteUser = UsersDAO.deleteUserByIdAndRole(studentID, "student");
        
        boolean deleteStudent = StudentDAO.deleteStudentbyID(studentID);
        boolean deleteParentChildRel = ParentChildRelDAO.deleteParentChildRel(studentID);
        boolean deleteParent;
        if(ParentChildRelDAO.getNumOfChild(parentID) == 1){
            deleteUser = UsersDAO.deleteUserByIdAndRole(parentID, "parent");
            deleteParent = ParentDAO.deleteParent(parentID);
        }else{
            deleteParent = true;
        }     
        boolean deleteTuitionGrade = StudentGradeDAO.deleteStudentTuitionGrade(studentID);
        System.out.println(deleteTuitionGrade);
        boolean deleteStudentClassRel = StudentClassDAO.deleteStudentClassRel(studentID);
        boolean deletePaymentReminder = PaymentDAO.deletePaymentReminderbyStudentID(studentID);
        boolean deleteRevenue = PaymentDAO.deleteRevenuebyStudentID(studentID);
        boolean deleteAttendance = LessonDAO.deleteAttendancebyID(studentID);
        
        if(deleteStudent && deleteParentChildRel && deleteParent && deleteTuitionGrade && deleteStudentClassRel && 
                deleteUser && deletePaymentReminder && deleteRevenue && deleteAttendance){
            out.println(1);
        }else{
            out.println(0);
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
