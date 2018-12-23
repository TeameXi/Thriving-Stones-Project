/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Student;
import entity.Class;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.LessonDAO;
import model.PaymentDAO;
import model.ReceiptDAO;
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
        double totalAmount = 0;
        if(totalAmountStr != null && !totalAmountStr.isEmpty()){
            totalAmount = Double.parseDouble(totalAmountStr);
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
       
        String paymentDate = "";
        if(paymentMode.equals("Bank Transfer") || paymentMode.equals("Cheque") || paymentMode.equals("Credit Card")){
            paymentDate = request.getParameter("payment_date");
            boolean insert = PaymentDAO.insertBankDeposit(paymentMode, paymentDate, studentName, totalAmount);
            System.out.println(insert);
        }
        List<Double> finalOutstandingAmount = new ArrayList<>();
        
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
                
                if(request.getParameter("update").equals("updatePayment")){
                    if (paymentAmount != 0) {
                        PaymentDAO.insertPaymentToRevenue(studentID, studentName, noOfLesson, "Deposit", lvlSubject, paymentAmount, paymentDate);
                        PaymentDAO.updateDepositOutstandingAmount(studentID, classID, calculatedOutstandingAmount);
                        finalOutstandingAmount.add(calculatedOutstandingAmount);
                        //StudentClassDAO.updateDepositPaymentDate(studentID, classID);
                    }

                    Student stu = StudentDAO.retrieveStudentbyID(studentID);
                    double totalOutstandingAmt = stu.getOutstandingAmt() - paymentAmount;
                    StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
//                System.out.println("After Deposit Payment" + totalOutstandingAmt + "  " + stu.getOutstandingAmt() + "  " + paymentAmount);
                }
                
                if(request.getParameter("update").equals("updateStudentFees")){
                
                    String depositAmtStr = request.getParameter("Deposit" + classID);
                    double depositAmt = outstandingAmount;
                    System.out.println("Deposit Str" + depositAmtStr + "outstanding" + outstandingAmount);
                    if (depositAmtStr != null && !depositAmtStr.isEmpty()) {
                        depositAmt = Double.parseDouble(depositAmtStr);  
                    }
                    double oldDepositAmt = PaymentDAO.getOldDepositAmt(studentID, classID);
                    double extraDepositBringForward = outstandingAmount - oldDepositAmt;
                    calculatedOutstandingAmount = depositAmt - paymentAmount + extraDepositBringForward;

                    int noOfLess = PaymentDAO.retrieveNoOfLessonPaymentReminder(studentID, classID);
                    double monthlyFees = depositAmt;
                    if(noOfLess >= 11){
                        monthlyFees = 3 * depositAmt;
                    }
                    if(depositAmt != outstandingAmount){
                        PaymentDAO.updateTuitionFees(studentID, classID, monthlyFees);
                    }

                    PaymentDAO.updateDepositAmount(studentID, classID, calculatedOutstandingAmount, depositAmt);
                    finalOutstandingAmount.add(calculatedOutstandingAmount);
                    
                    Student stu = StudentDAO.retrieveStudentbyID(studentID);
                    //double totalOutstandingAmt = stu.getOutstandingAmt() - paymentAmount - (outstandingAmount - depositAmt);
                    double totalOutstandingAmt = stu.getOutstandingAmt() - paymentAmount - (oldDepositAmt - depositAmt);
                    System.out.println("oldDeposit" + oldDepositAmt + "totalOut" + totalOutstandingAmt);
                    StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                    System.out.println("After Deposit Payment" + totalOutstandingAmt + "  " + stu.getOutstandingAmt() + "  " + paymentAmount);
                }

            } else if (type.equals("First Installment")) {
                
                if(request.getParameter("update").equals("updatePayment")){
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
                        finalOutstandingAmount.add(calculatedOutstandingAmount);
                         
                        double totalOutstandingAmt = stu.getOutstandingAmt() - paymentAmount;
                        StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                       
                        System.out.println("After FirstInstallment Pay" + totalOutstandingAmt + "  " + stu.getOutstandingAmt() + "  " + paymentAmount);
                    }

                    double firstInstallment = -1;
                    if (firstInstallmentStr != null && firstInstallmentStr.isEmpty()) {
                        PaymentDAO.updateFirstInstallmentAmount(studentID, classID, firstInstallment, calculatedOutstandingAmount);
                         finalOutstandingAmount.add(calculatedOutstandingAmount);
                    }

                    if (firstInstallmentStr != null && !firstInstallmentStr.isEmpty()) {
                        firstInstallment = Double.parseDouble(firstInstallmentStr);
                        calculatedOutstandingAmount = firstInstallment - paymentAmount;
                        PaymentDAO.updateFirstInstallmentAmount(studentID, classID, firstInstallment, calculatedOutstandingAmount);
                         finalOutstandingAmount.add(calculatedOutstandingAmount);
                         
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
                        PaymentDAO.insertPaymentToRevenue(studentID, studentName, noOfLesson, "First Installment", lvlSubject, paymentAmount, paymentDate);
                    }
                }

            } else if (type.equals("Reg Fees")) {

                PaymentDAO.updateRegFeesOutstandingAmount(studentID, calculatedOutstandingAmount);
                finalOutstandingAmount.add(calculatedOutstandingAmount);
                 
                if (paymentAmount != 0) {
                    PaymentDAO.insertPaymentToRevenue(studentID, studentName, noOfLesson, "Reg Fees", lvlSubject, paymentAmount, paymentDate);
                }

                Student stu = StudentDAO.retrieveStudentbyID(studentID);
                double totalOutstandingAmt = stu.getOutstandingAmt() - paymentAmount;
                StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                System.out.println("After Reg Fees Payment" + totalOutstandingAmt + "  " + stu.getOutstandingAmt() + "  " + paymentAmount);

            } else if (type.equals("Tuition Fees")) {
                if(request.getParameter("update").equals("updatePayment")){
                    double depositAmt = PaymentDAO.retrieveDepositAmt(studentID, classID);
                    if(depositAmt != chargeAmount){
                        chargeAmount = depositAmt;
                        if(outstandingAmount > chargeAmount){
                            outstandingAmount = depositAmt;
                        }
                    }
                    calculatedOutstandingAmount = outstandingAmount - paymentAmount;
                     finalOutstandingAmount.add(calculatedOutstandingAmount);
                    System.out.println( depositAmt + "Please " + outstandingAmount);

                    Student stu = StudentDAO.retrieveStudentbyID(studentID);
                    PaymentDAO.updateTuitionFeesOutstandingAmount(studentID, classID, dueDate, calculatedOutstandingAmount);
                    if (paymentAmount != 0) {
                        PaymentDAO.insertPaymentToRevenue(studentID, studentName, noOfLesson, "Tuition Fees", lvlSubject, paymentAmount, paymentDate);
                    }

                    if (chargeAmount == outstandingAmount && paymentAmount != 0) {
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
        }

        System.out.println("LOL" + request.getParameter("update"));
        if (request.getParameter("update").equals("updateStudentFees")) {
            response.sendRedirect("PaymentPage.jsp?studentID="+studentID);
            return;
        }
        
        boolean isZero = false;
        
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        String nos = "";
        String descriptions = "";
        String payment_amounts = "";
        String outstanding_amounts = "";
        DecimalFormat df = new DecimalFormat("0.00##");
        String totalAmountResult = "";
        if(totalAmount == 0){
            isZero = true;
        }else{
            totalAmountResult = df.format(totalAmount);
        }
        
        
        for (int i = 0; i < paymentType.length; i++) {
            if(isZero){
                break;
            }
            String type = paymentType[i];
            
            String subject = subjects[i];
            String lvlSubject = level + " " + subject;
            double paymentAmount = 0;
            if (!paymentAmounts[i].isEmpty()) {
                paymentAmount = Double.parseDouble(paymentAmounts[i]);
            }else{
                continue;
            }
            double outstandingAmountdb = 0;
            if (finalOutstandingAmount.get(i) != 0) {
                outstandingAmountdb = finalOutstandingAmount.get(i);
            }
            
            String result = df.format(paymentAmount);
            String outstandingAmountResult = df.format(outstandingAmountdb);
            
            if(i==0){
                nos = "" + (i+1);
                descriptions = type + " for " + lvlSubject;
                payment_amounts = "" + result;
                outstanding_amounts = "" + outstandingAmountResult;
            }else{
                nos = nos + "#" + (i+1);
                descriptions = descriptions + "#" + type + " for " + lvlSubject;
                payment_amounts = payment_amounts + "#" + result; 
                outstanding_amounts = outstanding_amounts + "#" + outstandingAmountResult;
            }
            
        }
        if(!isZero){
            ReceiptDAO receiptDAO = new ReceiptDAO();
            int receiptid = receiptDAO.addReceipt(date, paymentMode, nos, descriptions, payment_amounts, outstanding_amounts, "S$" + totalAmountResult, studentID);
            PrintWriter out = response.getWriter(); 

            out.println("<HTML>");
            out.println("<head>");
            out.println("<script>") ;
            out.println("function callMe(){");
            out.println("window.open('"+ request.getHeader("origin")+request.getContextPath()+"/GenerateReceiptServlet?i="+receiptid+"','_blank');");
            out.println("window.open('"+ request.getHeader("origin")+request.getContextPath()+"/PaymentSummaryPage.jsp');window.close()}");
            out.println("</script>") ;
            out.println("</head>");
            out.println("<body onLoad=\"callMe()\">");
            out.println("</body>");
            out.println("</HTML>");
        }else{
            response.sendRedirect("PaymentSummaryPage.jsp");
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
