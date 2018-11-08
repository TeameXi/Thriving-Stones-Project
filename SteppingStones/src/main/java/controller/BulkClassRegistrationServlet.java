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
                Student stu = StudentDAO.retrieveStudentbyID(studentID);
                String outTuition = request.getParameter(studentIDStr + "tuitionFees");
                String paymentType = request.getParameter(studentIDStr + "paymentType");
                if(paymentType == null){
                    paymentType = "month";
                }
                String totalDepositUnusedStr = request.getParameter(studentIDStr + "totalDepositUnused");
                String classFeesStr = request.getParameter(studentIDStr + "classFees");
                String depositTopupAmtStr = request.getParameter(studentIDStr + "depositTopupAmt");
                
                double outstandingTuitionFees = 0;
                if (outTuition != null && !"".equals(outTuition)) {
                    outstandingTuitionFees = Double.parseDouble(outTuition);
                }
                
                double totalDepositUnused = 0;
                if (totalDepositUnusedStr != null && !"".equals(totalDepositUnusedStr)) {
                    totalDepositUnused = Double.parseDouble(totalDepositUnusedStr);
                }
                
                double monthlyFees = 0;
                if (classFeesStr != null && !"".equals(classFeesStr)) {
                    monthlyFees = Double.parseDouble(classFeesStr);
                }
                
                double depositTopupAmt = 0;
                if (depositTopupAmtStr != null && !"".equals(depositTopupAmtStr)) {
                    depositTopupAmt = Double.parseDouble(depositTopupAmtStr);
                }
                
                System.out.println("Payment Type" + paymentType + " totalDepositUnused " + totalDepositUnused + " classFees " + monthlyFees + " depositTopupAmt " + depositTopupAmt);
                
                Class cls = ClassDAO.getClassByID(classID);
//                double monthlyFees = cls.getMthlyFees();
                String joinDate = LessonDAO.getNearestLessonDate(classID);
                double remainingtotalDepositUnused = 0;
                double outstandingDeposit = 0;
                if(totalDepositUnused > monthlyFees && paymentType.equals("month")){
                    remainingtotalDepositUnused = totalDepositUnused - monthlyFees;
                }else{
                    outstandingDeposit = monthlyFees - totalDepositUnused - depositTopupAmt;
                }
                    
                HashMap<String, Integer> reminders = null;
                double fees = 0;
                double deposit = 0;
                int lessonNum = 0;
                
                if(paymentType.equals("term")){
                    fees = monthlyFees * 3;
                    lessonNum = 11;
                    System.out.println("Per Term" + fees + " " + deposit);
                    reminders = PaymentDAO.getRemindersForPremiumStudent(classID, joinDate);
                    status = StudentClassDAO.saveStudentToRegisterClass(classID, studentID, 0, 0, joinDate);
                }else{
                    System.out.println("Per Month" + fees + " " + deposit);
                    fees = monthlyFees;
                    deposit = monthlyFees;
                    lessonNum = 4;
                    reminders = PaymentDAO.getReminders(classID, joinDate);
                    status = StudentClassDAO.saveStudentToRegisterClass(classID, studentID, deposit, outstandingDeposit, joinDate);
                    StudentDAO.updateTotalDepositUnused(studentID, remainingtotalDepositUnused);
                }
                
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
                               
                boolean dataMigration = ClassDAO.checkForBulkRegistrationType(classID);
                //System.out.println("dataMigration" + dataMigration);
                if(dataMigration){
                    insertOutFeesStatus = PaymentDAO.insertOutstandingTuitionFees(classID, studentID, joinDate, lessonNum, fees, outstandingTuitionFees);
                    
                }else{
                    insertOutFeesStatus = PaymentDAO.updateOutstandingChargeForNextYear(studentID, outstandingTuitionFees, classID);
                    String studentName = StudentDAO.retrieveStudentName(studentID);
                    String lvlSub = StudentDAO.retrieveStudentLevelbyIDD(studentID) + cls.getSubject();
                    PaymentDAO.insertPaymentToRevenue(studentID, studentName, lessonNum, "First Installment", lvlSub, monthlyFees - outstandingTuitionFees, "");
                }
                
                double totalOutstandingAmt = stu.getOutstandingAmt() + outstandingTuitionFees + outstandingDeposit;
                //System.out.println("total out" + outstandingTuitionFees + " L " + outstandingDeposit);
                updateOutstandingFees = StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                firstInstallentStatus = StudentClassDAO.updateFirstInsatllment(classID, studentID);
                
                
                if (!updateOutstandingFees && !status && !paymentStauts && !insertOutFeesStatus && !firstInstallentStatus) {
                    response.sendRedirect("BulkClassRegistration.jsp?errorMsg=Error Occurs while inserting!");
                    return;
                }
            }
            if (updateOutstandingFees && status && paymentStauts && insertOutFeesStatus && firstInstallentStatus) {
                //System.out.println("Enter successful");
                //response.sendRedirect("AdminMarkAttendance.jsp?status=Successfully Registered.");
                response.sendRedirect("BulkClassRegistration.jsp");
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
