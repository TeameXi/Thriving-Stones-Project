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
            boolean updateOutstandingFees = false;
            boolean status = false;
            boolean paymentStauts = false;
            boolean insertOutFeesStatus = false;
            boolean firstInstallentStatus = false;
            
            for (String studentIDStr : studentValues) {
                int studentID = Integer.parseInt(studentIDStr);
                String outTuition = request.getParameter(studentIDStr + "tuitionFees");
                String paymentType = request.getParameter(studentIDStr + "paymentType");
                System.out.println("Payment Type" + paymentType);
                
                double outstandingDeposit = 0;

                double outstandingTuitionFees = 0;
                if (outTuition != null && !"".equals(outTuition)) {
                    outstandingTuitionFees = Double.parseDouble(outTuition);
                }
                Class cls = ClassDAO.getClassByID(classID);
                double monthlyFees = cls.getMthlyFees();
                String joinDate = LessonDAO.getNearestLessonDate(classID);
                
                HashMap<String, Integer> reminders = null;
                double fees = 0;
                double deposit = 0;
                int lessonNum = 0;
                if(paymentType == null){
                    paymentType = "month";
                }
                if(paymentType.equals("term")){
                    fees = monthlyFees * 3;
                    lessonNum = 11;
                    System.out.println("Per Term" + fees + " " + deposit);
                    reminders = PaymentDAO.getRemindersForPremiumStudent(classID, joinDate);
                    
                }else{
                    System.out.println("Per Month" + fees + " " + deposit);
                    fees = monthlyFees;
                    deposit = monthlyFees;
                    lessonNum = 4;
                    reminders = PaymentDAO.getReminders(classID, joinDate);
                }

                status = StudentClassDAO.saveStudentToRegisterClass(classID, studentID, deposit, outstandingDeposit, joinDate);
                insertOutFeesStatus = PaymentDAO.insertOutstandingTuitionFees(classID, studentID, joinDate, lessonNum, fees, outstandingTuitionFees);
                Student stu = StudentDAO.retrieveStudentbyID(studentID);
                double totalOutstandingAmt = stu.getOutstandingAmt() + outstandingTuitionFees;
                updateOutstandingFees = StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                firstInstallentStatus = StudentClassDAO.updateFirstInsatllment(classID, studentID);
                
                Set<String> keys = reminders.keySet();
                //System.out.println(reminders.keySet());
                paymentStauts = false;
                if(reminders != null && !reminders.isEmpty()){
                    for(String reminderDate: keys){
                        System.out.println("ReminderDates " + reminders.get(reminderDate));
                        int noOfLessons = reminders.get(reminderDate);
                        paymentStauts = PaymentDAO.insertPaymentReminderWithAmount(classID, studentID, reminderDate, noOfLessons, fees);
                    }
                }else{
                    paymentStauts = true;
                }
                if (!updateOutstandingFees && !status && !paymentStauts && !insertOutFeesStatus && !firstInstallentStatus) {
                    response.sendRedirect("BulkClassRegistration.jsp?errorMsg=Error Occurs while inserting!");
                    return;
                }
            }
            if (updateOutstandingFees && status && paymentStauts && insertOutFeesStatus && firstInstallentStatus) {
                //System.out.println("Enter successful");
                response.sendRedirect("BulkClassRegistration.jsp?status=Successfully Registered.");
                return;
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
