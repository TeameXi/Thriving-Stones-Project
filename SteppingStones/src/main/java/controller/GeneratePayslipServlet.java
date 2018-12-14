/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import com.qoppa.pdfWriter.PDFGraphics;
import com.qoppa.pdfWriter.PDFDocument;
import com.qoppa.pdfWriter.PDFPage;
import entity.Tutor;
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
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.LevelDAO;
import model.TutorDAO;

@WebServlet(name = "GeneratePayslipServlet", urlPatterns = {"/GeneratePayslipServlet"})
public class GeneratePayslipServlet extends HttpServlet {

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
        String type = request.getParameter("t");
        String amount = request.getParameter("a");
        double paymentAmount = 0;
        if (!amount.isEmpty()) {
            paymentAmount = Double.parseDouble(amount);
        }
        DecimalFormat df = new DecimalFormat("0.00##");
        String result = df.format(paymentAmount);
        
        String[] data = request.getParameter("i").split("_");
        int tutorID = Integer.parseInt(data[0]);
        int classID = Integer.parseInt(data[1]);
        int month = 0;
        int year = 0;
        if("m".equals(type)){
            month = Integer.parseInt(data[2]);
            year = Integer.parseInt(data[3]);
        }
        
        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutor = tutorDAO.retrieveSpecificTutorById(tutorID);
        Class cls = ClassDAO.getClassByID(classID);
        
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        ServletOutputStream sOut = response.getOutputStream();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + "Payslip_"+ tutor.getName() + "_" + date +".pdf" );
        
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
        g2d.drawString("Payslip", 280, 180);
        
        g2d.setFont(new Font ("Calibri", Font.BOLD, 11));
        g2d.drawString("Name: ", 100, 200);
        
        //g2d.drawString("Period: ", 400, 200);
        g2d.drawString("Payment Date: ", 400, 215);
        //g2d.drawString("Payment Mode: ", 400, 230);
        
        g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
        g2d.drawString(tutor.getName(), 100, 215);
        //g2d.drawString(cls., 470, 200);
        g2d.drawString(date, 470, 215);
        //g2d.drawString("CASH", 480, 230);
        
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.RED);
        g2d.drawLine(100, 240, 650, 240);
        g2d.drawLine(100, 280, 650, 280);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font ("Calibri", Font.BOLD, 11));
        g2d.drawString("No.", 110, 260);
        g2d.drawString("Description", 160, 260);
        g2d.drawString("Amount Paid", 400, 260);
        
        
        //itemsssss
        g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
        g2d.drawString("1", 110, 300);
        
        String strlesson = request.getParameter("c");
        int lesson = Integer.parseInt(strlesson);
        double duration = ClassDAO.getClassTime(classID);
            
            
        if("r".equals(type)){
            g2d.drawString("Salary for replacement for " + cls.getSubject() + " " + cls.getLevel(), 160, 300);
            g2d.drawString("" + duration + "hours x " + lesson + "lesson(s) x $" + TutorDAO.getHourlyPay(tutorID, LevelDAO.retrieveLevelID(cls.getLevel()), cls.getSubjectID()) , 160, 320);
        }else{
            g2d.drawString("Salary for " + cls.getSubject() + " " + cls.getLevel() + " for " + getMonthName(month) + ", " + year, 160, 300);
            g2d.drawString("" + duration + "hours x " + lesson + "lesson(s) x $" + TutorDAO.getHourlyPay(tutorID, LevelDAO.retrieveLevelID(cls.getLevel()), cls.getSubjectID()) , 160, 320);
        }
        
        g2d.drawString(result, 400, 300);
        
        g2d.setColor(Color.RED);
        g2d.drawLine(100, 650, 650, 650);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font ("Calibri", Font.BOLD, 11));
        g2d.drawString("Total Amount Paid", 300, 670);
        
        g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
        g2d.drawString(result, 400, 670);
        
        g2d.setColor(Color.RED);
        g2d.drawLine(100, 680, 650, 680);
        
        g2d.setFont(new Font ("Calibri", Font.ITALIC, 9));
        g2d.setColor(Color.BLACK);
        g2d.drawString("This is computer generated receipt. Therefore, no signature is required", 200, 710);
        
        // Add the page to the document
        pdfDoc.addPage(newPage);

        // Save the document to the servlet output stream.  This goes directly to the browser
        pdfDoc.saveDocument(sOut);

        // Close the server output stream
        sOut.close();
        

    }
    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    private String getMonthName(int month){
        if(month == 1){
            return "January";
        }
        if(month == 2){
            return "February";
        }
        if(month == 3){
            return "March";
        }
        if(month == 4){
            return "April";
        }
        if(month == 5){
            return "May";
        }
        if(month == 6){
            return "June";
        }
        if(month == 7){
            return "July";
        }
        if(month == 8){
            return "August";
        }
        if(month == 9){
            return "September";
        }
        if(month == 10){
            return "October";
        }
        if(month == 11){
            return "November";
        }
        return "December";
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
