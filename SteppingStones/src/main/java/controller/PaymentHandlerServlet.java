/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.qoppa.pdfWriter.PDFDocument;
import com.qoppa.pdfWriter.PDFGraphics;
import com.qoppa.pdfWriter.PDFPage;
import entity.Student;
import entity.Class;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
        if(paymentMode.equals("Bank Transfer") || paymentMode.equals("Cheque")){
            paymentDate = request.getParameter("payment_date");
            boolean insert = PaymentDAO.insertBankDeposit(paymentMode, paymentDate, studentName, totalAmount);
            System.out.println(insert);
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
                
                String depositAmtStr = request.getParameter("" + classID);
                double depositAmt = outstandingAmount;
                if (depositAmtStr != null && !depositAmtStr.isEmpty()) {
                    depositAmt = Double.parseDouble(depositAmtStr);  
                }
                calculatedOutstandingAmount = depositAmt - paymentAmount;
                
                int noOfLess = PaymentDAO.retrieveNoOfLessonPaymentReminder(studentID, classID);
                double monthlyFees = depositAmt;
                if(noOfLess >= 11){
                    monthlyFees = 3 * depositAmt;
                }
                if(depositAmt != outstandingAmount){
                    PaymentDAO.updateTuitionFees(studentID, classID, monthlyFees);
                }

                PaymentDAO.updateDepositOutstandingAmount(studentID, classID, calculatedOutstandingAmount, depositAmt);
                if (paymentAmount != 0) {
                    PaymentDAO.insertPaymentToRevenue(studentID, studentName, noOfLesson, "Deposit", lvlSubject, paymentAmount, paymentDate);
                    StudentClassDAO.updateDepositPaymentDate(studentID, classID);
                }

                Student stu = StudentDAO.retrieveStudentbyID(studentID);
                double totalOutstandingAmt = stu.getOutstandingAmt() - paymentAmount - (outstandingAmount - depositAmt);
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
                    PaymentDAO.insertPaymentToRevenue(studentID, studentName, noOfLesson, "First Installment", lvlSubject, paymentAmount, paymentDate);
                }

            } else if (type.equals("Reg Fees")) {

                PaymentDAO.updateRegFeesOutstandingAmount(studentID, calculatedOutstandingAmount);
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
        
        String from = (String) request.getSession().getAttribute("from");       
        //String from = "registration";  
        ReceiptDAO receiptDAO = new ReceiptDAO();
        int receiptNo = receiptDAO.retrieveReceiptNo();
        String formattedReceiptNo = "R" + String.format("%05d", receiptNo);
        String nos = "";
        String descriptions = "";
        String payment_amounts = "";
        
        //String from = "registration";
         // Get servlet output stream
        ServletOutputStream sOut = response.getOutputStream();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + formattedReceiptNo + ".pdf" );

        // Create pageformat for the document
        PageFormat pf = new PageFormat();
        Paper paper = new Paper();
        paper.setSize(72 * 8.5, 72 * 11.0);
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        // Create a document and a page in the document
        PDFDocument pdfDoc = new PDFDocument();
        PDFPage newPage = pdfDoc.createPage(pf);
        
        PDFGraphics g2d = (PDFGraphics) newPage.createGraphics();

        // set image compression to JPEG2000
        //g2d.setImageCompression(new ImageCompression(ImageCompression.COMPRESSION_JPEG2000, 0.8f));
        //System.out.println(getServletContext().getResource);
        // load image 
        BufferedImage logo = ImageIO.read(getServletContext().getResource("/styling/img/Stepping-stones-receipt-logo.png"));

        // draw image on the graphics object of the page 
        g2d.drawImage(logo, 100, 75, 150, 75, null);
        // Draw to the page
        //Graphics2D g2d = newPage.createGraphics();
        g2d.setFont(new Font ("Calibri", Font.BOLD, 14));
        g2d.drawString("Stepping Stones Learning Center", 260, 90);

        g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
        g2d.drawString("Blk 145 Teck Whye Ave #01-159 Singapore 680145", 260, 110);
        g2d.drawString("Phone No.:1231231 Email: contact@steppingstoneslc.com.sg ", 260, 130);
        g2d.drawString("UEN No.: T16LL1821J", 260, 150);
        
        g2d.setFont(new Font ("Calibri", Font.BOLD, 16));
        g2d.drawString("Official Receipt", 260, 180);
        
        g2d.setFont(new Font ("Calibri", Font.BOLD, 11));
        g2d.drawString("Received From: ", 100, 200);
        
        g2d.drawString("Receipt No.: ", 380, 200);
        g2d.drawString("Receipt Date: ", 380, 215);
        g2d.drawString("Payment Mode: ", 380, 230);
        
        g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
        g2d.drawString(studentName, 100, 215);
        g2d.drawString(formattedReceiptNo, 450, 200);
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        g2d.drawString(date, 450, 215);
        g2d.drawString(paymentMode, 460, 230);
        
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.RED);
        g2d.drawLine(100, 240, 650, 240);
        g2d.drawLine(100, 280, 650, 280);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font ("Calibri", Font.BOLD, 11));
        g2d.drawString("No.", 110, 260);
        g2d.drawString("Description", 160, 260);
        g2d.drawString("Amount Paid", 400, 260);
        
        int lengthCount = 300;
        for (int i = 0; i < paymentType.length; i++) {
            String type = paymentType[i];
            //int classID = Integer.parseInt(classIDs[i]);
            //Class cls = ClassDAO.getClassByID(classID);
            String subject = subjects[i];
            String lvlSubject = level + " " + subject;
            double paymentAmount = 0;
            if (!paymentAmounts[i].isEmpty()) {
                paymentAmount = Double.parseDouble(paymentAmounts[i]);
            }
            DecimalFormat df = new DecimalFormat("0.00##");
            String result = df.format(paymentAmount);
            //itemsssss
            g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
            g2d.drawString("" + (i+1), 110, lengthCount);
            g2d.drawString(type + " for " + lvlSubject , 160, lengthCount);
            g2d.drawString("" + result, 400, lengthCount);
            if(i==0){
                nos = "" + (i+1);
                descriptions = type + " for " + lvlSubject;
                payment_amounts = "" + result;
            }else{
                nos = "#" + (i+1);
                descriptions = "#" + type + " for " + lvlSubject;
                payment_amounts = "#" + result; 
            }
            lengthCount += 20;
        }
        
        
        g2d.setColor(Color.RED);
        g2d.drawLine(100, 650, 650, 650);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font ("Calibri", Font.BOLD, 11));
        g2d.drawString("Total Amount Paid", 300, 670);
        
        DecimalFormat df = new DecimalFormat("0.00##");
        String totalAmountResult = df.format(totalAmount);
        
        g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
        g2d.drawString("S$" + totalAmountResult, 400, 670);
        
        g2d.setColor(Color.RED);
        g2d.drawLine(100, 680, 650, 680);
        
        g2d.setFont(new Font ("Calibri", Font.ITALIC, 9));
        g2d.setColor(Color.BLACK);
        g2d.drawString("This is computer generated receipt. Therefore, no signature is required", 200, 710);
        
        receiptDAO.addReceipt(date, paymentMode, nos, descriptions, payment_amounts, "S$" + totalAmountResult, studentID);
        
        // Add the page to the document
        pdfDoc.addPage(newPage);

        // Save the document to the servlet output stream.  This goes directly to the browser
        pdfDoc.saveDocument(sOut);

        // Close the server output stream
        sOut.close();
        
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
