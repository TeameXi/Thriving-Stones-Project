/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.PaymentReminderTask;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.PaymentDAO;

/**
 *
 * @author Xin
 */
@WebServlet(name = "SetPaymentDateServlet", urlPatterns = {"/SetPaymentDateServlet"})
public class SetPaymentDateServlet extends HttpServlet {

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
        String paymentDate = request.getParameter("paymentDate") + " 10:13:00";
        String classID = request.getParameter("classID");
        
        if(! classID.isEmpty()){
            try {
                int classId = Integer.parseInt(classID);
                PaymentDAO payment = new PaymentDAO();
                payment.insertPaymentDate(paymentDate,classId);
                
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = format.parse(paymentDate);
                long milis = date.getTime() - System.currentTimeMillis();
                ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
                ScheduledFuture schedule = service.schedule(new PaymentReminderTask(paymentDate, classId, false), milis, TimeUnit.MILLISECONDS);
                
                Date overdueDate = new Date(date.getTime() + 2 * (24*60*60*1000));
                String overdue = format.format(overdueDate);
                milis = overdueDate.getTime() - System.currentTimeMillis();
                ScheduledFuture overdueSched = service.schedule(new PaymentReminderTask(overdue, classId, true), milis, TimeUnit.MILLISECONDS);
            } catch (ParseException ex) {
                Logger.getLogger(SetPaymentDateServlet.class.getName()).log(Level.SEVERE, null, ex);
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
