/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.PaymentDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "UpdateBankDepositServlet", urlPatterns = {"/UpdateBankDepositServlet"})
public class UpdateBankDepositServlet extends HttpServlet {

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
        //String bankAccountNum = request.getParameter("bankAccountNum");
        String[] types = request.getParameterValues("type[]");
        String[] paymentDates = request.getParameterValues("date[]");
        String[] froms = request.getParameterValues("from[]");
        String[] amounts = request.getParameterValues("amount[]");
        
        boolean insertStatus = false;
        for (int i = 0; i < types.length; i++) {
            String type = types[i];
            String paymentDate = paymentDates[i];
            String from = froms[i];
            double amount = 0;
            if(!amounts[i].isEmpty()){
                amount = Double.parseDouble(amounts[i]);
            }
            if(!types[i].isEmpty() && !paymentDates[i].isEmpty() && !froms[i].isEmpty() && !amounts[i].isEmpty()){
                insertStatus = PaymentDAO.insertBankDeposit(type, paymentDate, from, amount);
            }
            if (!insertStatus) {
                response.sendRedirect("BankDepositRevenue.jsp?errorMsg=Error Occurs while inserting!");
                return;
            }
        }
        if (insertStatus) {
            response.sendRedirect("BankDepositRevenue.jsp?status=Update successfully.");
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
