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
import java.time.LocalDateTime;
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
        String outstanding_amountstr = receipt.get(6);
        String studentidstr = receipt.get(7);
        
        String studentName = StudentDAO.retrieveStudentName(Integer.parseInt(studentidstr));
        
        String[] nos = nostr.split("#");
        String[] descriptions = descriptionstr.split("#");
        String[] payment_amounts = payment_amountstr.split("#");
        String[] outstanding_amount = outstanding_amountstr.split("#");
        
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=" + studentName + " " + date + ".pdf" );

        // Create pageformat for the document
        PageFormat pf = new PageFormat();
        Paper paper = new Paper();
        paper.setSize(72 * 8.5, 72 * 11.0);
        paper.setImageableArea(0, 0, 8.5 * 72, 11 * 72);
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
        g2d.drawString("Stepping Stones Learning Centre", 259, 90);

        g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
        g2d.drawString("Blk 258 Bukit Panjang Ring Road #01-52 Singapore 670258", 259, 110);
        g2d.drawString("Phone No.:6763 2660 ", 259, 130);
        g2d.drawString("Email: contact@steppingstoneslc.com.sg ", 259, 150);
        g2d.drawString("UEN No.: T16LL1821J", 259, 170);
        
        g2d.setFont(new Font ("Calibri", Font.BOLD, 16));
        g2d.drawString("Official Receipt", 259, 200);
        
        g2d.setFont(new Font ("Calibri", Font.BOLD, 11));
        g2d.drawString("Received From: ", 100, 220);
        
        g2d.drawString("Receipt No.: ", 380, 220);
        g2d.drawString("Receipt Date: ", 380, 235);
        g2d.drawString("Payment Mode: ", 380, 250);
        
        g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
        g2d.drawString(studentName, 100, 235);
        g2d.drawString(formattedReceiptNo, 450, 220);
        
        g2d.drawString(date, 450, 235);
        g2d.drawString(" " + paymentMode, 460, 250);
        
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.RED);
        g2d.drawLine(40, 260, 570, 260);
        g2d.drawLine(40, 320, 570, 320);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font ("Calibri", Font.BOLD, 11));
        g2d.drawString("No.", 60, 280);
        g2d.drawString("Description", 80, 280);
        g2d.drawString("Amt Paid", 390, 280);
        g2d.drawString("Outstanding Amt", 460, 280);
        
        int lengthCount = 300;
        for (int i = 0; i < nos.length; i++) {
            
            g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
            g2d.drawString(nos[i], 60, lengthCount);
            g2d.drawString(descriptions[i], 80, lengthCount);
            g2d.drawString(payment_amounts[i], 390, lengthCount);
            g2d.drawString(outstanding_amount[i], 460, lengthCount);
            lengthCount += 20;
        }
        
        
        g2d.setColor(Color.RED);
        g2d.drawLine(100, 650, 570, 650);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font ("Calibri", Font.BOLD, 11));
        g2d.drawString("Total Amount Paid", 300, 670);
        
        g2d.setFont(new Font ("Calibri", Font.PLAIN, 11));
        g2d.drawString("" + total_amount_paid, 400, 670);
        
        g2d.setColor(Color.RED);
        g2d.drawLine(100, 680, 570, 680);
        
        g2d.setFont(new Font ("Calibri", Font.ITALIC, 9));
        g2d.setColor(Color.BLACK);
        g2d.drawString("This is computer generated receipt. Therefore, no signature is required", 200, 710);
        
        // Add the page to the document
        pdfDoc.addPage(newPage);

        ServletOutputStream sOut = response.getOutputStream();
        // Save the document to the servlet output stream.  This goes directly to the browser
        pdfDoc.saveDocument(sOut);

        sOut.println("<HTML>");
        sOut.println("<head>");
        sOut.println("<script>") ;
        sOut.println("function closeWindow(){");
        sOut.println("window.close()");
        sOut.println("</script>") ;
        sOut.println("</head>");
        sOut.println("<body onLoad=\"closeWindow()\">");
        sOut.println("</body>");
        sOut.println("</HTML>");
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
