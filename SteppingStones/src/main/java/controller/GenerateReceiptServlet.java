/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.qoppa.pdfWriter.PDFDocument;
import com.qoppa.pdfWriter.PDFGraphics;
import com.qoppa.pdfWriter.PDFPage;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ReceiptDAO;
import model.StudentDAO;

/**
 *
 * @author Desmond
 */
@WebServlet(name = "GenerateReceiptServlet", urlPatterns = {"/GenerateReceiptServlet"})
public class GenerateReceiptServlet extends HttpServlet {

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
        String receiptidstr = request.getParameter("i");
        
        ReceiptDAO receiptDAO = new ReceiptDAO();
        
        int receiptNo = Integer.parseInt(receiptidstr);
        String formattedReceiptNo = "R" + String.format("%05d", receiptNo);
        
        List<String> receipt = receiptDAO.retrieveReceipt(receiptNo);
        String date = receipt.get(0);
        String paymentMode = receipt.get(1);
        String nostr = receipt.get(2);
        String descriptionstr = receipt.get(3);
        String payment_amountstr = receipt.get(4);
        String total_amount_paid = receipt.get(5);
        String studentidstr = receipt.get(6);
        
        String studentName = StudentDAO.retrieveStudentName(Integer.parseInt(studentidstr));
        
        String[] nos = nostr.split("#");
        String[] descriptions = descriptionstr.split("#");
        String[] payment_amounts = payment_amountstr.split("#");
        
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

        // load image 
        BufferedImage logo = ImageIO.read(getServletContext().getResource("/styling/img/Stepping-stones-receipt-logo.png"));

        // draw image on the graphics object of the page 
        g2d.drawImage(logo, 100, 75, 150, 75, null);
        // Draw to the page
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
        for (int i = 0; i < nos.length; i++) {
            
            g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
            g2d.drawString(nos[i], 110, lengthCount);
            g2d.drawString(descriptions[i], 160, lengthCount);
            g2d.drawString(payment_amounts[i], 400, lengthCount);
            
            lengthCount += 20;
        }
        
        
        g2d.setColor(Color.RED);
        g2d.drawLine(100, 650, 650, 650);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font ("Calibri", Font.BOLD, 11));
        g2d.drawString("Total Amount Paid", 300, 670);
        
        g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
        g2d.drawString("S$" + total_amount_paid, 400, 670);
        
        g2d.setColor(Color.RED);
        g2d.drawLine(100, 680, 650, 680);
        
        g2d.setFont(new Font ("Calibri", Font.ITALIC, 9));
        g2d.setColor(Color.BLACK);
        g2d.drawString("This is computer generated receipt. Therefore, no signature is required", 200, 710);
        
        // Add the page to the document
        pdfDoc.addPage(newPage);

        ServletOutputStream sOut = response.getOutputStream();
        // Save the document to the servlet output stream.  This goes directly to the browser
        pdfDoc.saveDocument(sOut);

        // Close the server output stream
        sOut.close();
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
