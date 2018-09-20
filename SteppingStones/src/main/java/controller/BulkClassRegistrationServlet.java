/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Class;
import entity.Student;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.LessonDAO;
import model.PaymentDAO;
import model.StudentClassDAO;
import model.StudentDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "BulkClassRegistrationServlet", urlPatterns = {"/BulkClassRegistrationServlet"})
public class BulkClassRegistrationServlet extends HttpServlet {

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

        String[] studentValues = request.getParameterValues("studentID");
        String classIDStr = request.getParameter("classID");
        int classID = Integer.parseInt(classIDStr);

        if (studentValues != null) {
            for (String studentIDStr : studentValues) {
                int studentID = Integer.parseInt(studentIDStr);
                String outDep = request.getParameter(studentIDStr + "deposit");
                String outTuition = request.getParameter(studentIDStr + "tuitionFees");

                double outstandingDeposit = 0;
                if (outDep != null && !"".equals(outDep)) {
                    outstandingDeposit = Double.parseDouble(outDep);
                }

                double outstandingTuitionFees = 0;
                if (outTuition != null && !"".equals(outTuition)) {
                    outstandingTuitionFees = Double.parseDouble(outTuition);
                }
                Class cls = ClassDAO.getClassByID(classID);
                double monthlyFees = cls.getMthlyFees();
                String joinDate = LessonDAO.getNearestLessonDate(classID);
                boolean status = StudentClassDAO.saveStudentToRegisterClass(classID, studentID, monthlyFees, outstandingDeposit, joinDate);
                PaymentDAO.insertOutstandingTuitionFees(classID, studentID, joinDate, 3, monthlyFees, outstandingTuitionFees);
                
                Student stu = StudentDAO.retrieveStudentbyID(studentID);
                double totalOutstandingAmt = stu.getOutstandingAmt() + outstandingDeposit + outstandingTuitionFees;
                boolean updateOutstandingFees = StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                
                HashMap<String, Integer> reminders = PaymentDAO.getReminders(classID, joinDate);
                Set<String> keys = reminders.keySet();
                boolean paymentStauts = false;
                if(reminders != null && !reminders.isEmpty()){
                    for(String reminderDate: keys){
                        int noOfLessons = reminders.get(reminderDate);
                        paymentStauts = PaymentDAO.insertPaymentReminderWithAmount(classID, studentID, reminderDate, noOfLessons, monthlyFees);
                    }
                }else{
                    paymentStauts = true;
                }
                if (updateOutstandingFees && status && paymentStauts) {
                    System.out.println("Enter successful");
                    response.sendRedirect("BulkClassRegistration.jsp?status=Successfully Registered.");
                    return;
                }
                //System.out.println("Student 1" + " classID " + classID + " StuID " + studentID + " dep " + outstandingDeposit + " tut " + outstandingTuitionFees);
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
