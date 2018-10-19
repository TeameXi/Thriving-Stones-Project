/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Student;
import entity.Class;
import java.io.IOException;
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
@WebServlet(name = "PaymentHandlerServlet", urlPatterns = {"/PaymentHandlerServlet"})
public class PaymentHandlerServlet extends HttpServlet {

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
        
        String paymentMode = request.getParameter("payment_mode");
        String totalAmountStr = request.getParameter("totalAmount");
        int totalAmount = 0;
        if(totalAmountStr != null && !totalAmountStr.isEmpty()){
            totalAmount = Integer.parseInt(totalAmountStr);
        }
        String studentIDStr = request.getParameter("student_id");
        int studentID = 0;
        if (studentIDStr != null && !studentIDStr.isEmpty()) {
            studentID = Integer.parseInt(studentIDStr);
        }
        String level = request.getParameter("level");
        String studentName = StudentDAO.retrieveStudentName(studentID);
        String[] classIDs = request.getParameterValues("classId[]");
        String[] paymentType = request.getParameterValues("paymentType[]");
        String[] paymentAmounts = request.getParameterValues("paymentAmount[]");
        String[] paymentDueDates = request.getParameterValues("paymentDueDate[]");
        String[] outstandingAmounts = request.getParameterValues("outstandingAmount[]");
        String[] noOfLessons = request.getParameterValues("noOfLessons[]");
        String[] subjects = request.getParameterValues("subject[]");
        String[] chargeAmounts = request.getParameterValues("chargeAmount[]");
        
        System.out.println(paymentMode);
        if(paymentMode.equals("Bank Transfer") || paymentMode.equals("Cheque")){
            String paymentDate = request.getParameter("payment_date");
            boolean insert = PaymentDAO.insertBankDeposit(paymentMode, paymentDate, studentName, totalAmount);
            System.out.println("insert");
        }
        
        for (int i = 0; i < paymentType.length; i++) {
            String type = paymentType[i];
            int classID = Integer.parseInt(classIDs[i]);
            Class cls = ClassDAO.getClassByID(classID);
            String dueDate = paymentDueDates[i];
            double paymentAmount = 0;
            if (!paymentAmounts[i].isEmpty()) {
                paymentAmount = Double.parseDouble(paymentAmounts[i]);
            }
            double chargeAmount = Double.parseDouble(chargeAmounts[i]);
            double outstandingAmount = Double.parseDouble(outstandingAmounts[i]);
            double calculatedOutstandingAmount = outstandingAmount - paymentAmount;
            int noOfLesson = Integer.parseInt(noOfLessons[i]);
            String subject = subjects[i];
            String lvlSubject = level + " " + subject;

            if (type.equals("Deposit")) {

                PaymentDAO.updateDepositOutstandingAmount(studentID, classID, calculatedOutstandingAmount);
                if (paymentAmount != 0) {
                    PaymentDAO.insertPaymentToRevenue(studentID, studentName, noOfLesson, "Deposit", lvlSubject, paymentAmount);
                    StudentClassDAO.updateDepositPaymentDate(studentID, classID);
                }

                Student stu = StudentDAO.retrieveStudentbyID(studentID);
                double totalOutstandingAmt = stu.getOutstandingAmt() - paymentAmount;
                StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                System.out.println("After Deposit Payment" + totalOutstandingAmt + "  " + stu.getOutstandingAmt() + "  " + paymentAmount);

            } else if (type.equals("First Installment")) {

                String joinDate = StudentClassDAO.retrieveJoinDateOfStudentByClass(studentID, classID);
                System.out.print("JoinDate " + joinDate);
                if(cls.getType().equals("P")){
                    noOfLesson = LessonDAO.retrieveNoOfLessonPremium(classID, joinDate);
                    //System.out.println(noOfLesson);
                }else{
                    noOfLesson = LessonDAO.retrieveNoOfLessonForFirstInstallment(classID, joinDate);
                }
                Student stu = StudentDAO.retrieveStudentbyID(studentID);
                String firstInstallmentStr = request.getParameter("" + classID);

                System.out.println("Installment Fees" + firstInstallmentStr);
                if (firstInstallmentStr == null) {
                    PaymentDAO.updateFirstInstallmentOutstandingAmount(studentID, classID, calculatedOutstandingAmount);
                    double totalOutstandingAmt = stu.getOutstandingAmt() - paymentAmount;
                    StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                    System.out.println("After FirstInstallment Pay" + totalOutstandingAmt + "  " + stu.getOutstandingAmt() + "  " + paymentAmount);
                }

                double firstInstallment = -1;
                if (firstInstallmentStr != null && firstInstallmentStr.isEmpty()) {
                    PaymentDAO.updateFirstInstallmentAmount(studentID, classID, firstInstallment, calculatedOutstandingAmount);
                }

                if (firstInstallmentStr != null && !firstInstallmentStr.isEmpty()) {
                    firstInstallment = Double.parseDouble(firstInstallmentStr);
                    calculatedOutstandingAmount = firstInstallment - paymentAmount;
                    PaymentDAO.updateFirstInstallmentAmount(studentID, classID, firstInstallment, calculatedOutstandingAmount);
                    if (chargeAmount == outstandingAmount) {
                        double totalOutstandingAmt = stu.getOutstandingAmt() + calculatedOutstandingAmount;
                        StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                        System.out.println("After FirstInstallment Out" + totalOutstandingAmt + "  " + stu.getOutstandingAmt() + "  " + paymentAmount);
                    } else {
                        double totalOutstandingAmt = stu.getOutstandingAmt() - paymentAmount;
                        StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                        System.out.println("After FirstInstallment Pay" + totalOutstandingAmt + "  " + stu.getOutstandingAmt() + "  " + paymentAmount);
                    }
                }

                if (paymentAmount != 0) {
                    PaymentDAO.insertPaymentToRevenue(studentID, studentName, noOfLesson, "First Installment", lvlSubject, paymentAmount);
                }

            } else if (type.equals("Reg Fees")) {

                PaymentDAO.updateRegFeesOutstandingAmount(studentID, calculatedOutstandingAmount);
                if (paymentAmount != 0) {
                    PaymentDAO.insertPaymentToRevenue(studentID, studentName, noOfLesson, "Reg Fees", lvlSubject, paymentAmount);
                }

                Student stu = StudentDAO.retrieveStudentbyID(studentID);
                double totalOutstandingAmt = stu.getOutstandingAmt() - paymentAmount;
                StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                System.out.println("After Reg Fees Payment" + totalOutstandingAmt + "  " + stu.getOutstandingAmt() + "  " + paymentAmount);

            } else if (type.equals("Tuition Fees")) {

                Student stu = StudentDAO.retrieveStudentbyID(studentID);
                PaymentDAO.updateTuitionFeesOutstandingAmount(studentID, classID, dueDate, calculatedOutstandingAmount);
                if (paymentAmount != 0) {
                    PaymentDAO.insertPaymentToRevenue(studentID, studentName, noOfLesson, "Tuition Fees", lvlSubject, paymentAmount);
                }

                if (chargeAmount == outstandingAmount) {
                    double totalOutstandingAmt = stu.getOutstandingAmt() + calculatedOutstandingAmount;
                    StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                    System.out.println("After Tuition Fees Out" + totalOutstandingAmt + "  " + stu.getOutstandingAmt() + "  " + paymentAmount);
                } else {
                    double totalOutstandingAmt = stu.getOutstandingAmt() - paymentAmount;
                    StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                    System.out.println("After Tuition Fees Pay" + totalOutstandingAmt + "  " + stu.getOutstandingAmt() + "  " + paymentAmount);
                }

            }
        }
        
        String from = (String) request.getSession().getAttribute("from");
        //String from = "registration";
        if (from.equals("registration")) {
            response.sendRedirect("RegisterForClasses.jsp?status=Payment successful.");
            return;
        } else if (from.equals("payment")) {
            response.sendRedirect("PaymentStudent.jsp?status=Payment successful.");
            return;
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
