/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.BankDeposit;
import entity.Deposit;
import entity.Expense;
import entity.Revenue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.FinancialReportDAO;
import model.PaymentDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "FinancialReportServlet", urlPatterns = {"/FinancialReportServlet"})
public class FinancialReportServlet extends HttpServlet {

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
        String temp = request.getSession().getServletContext().getRealPath("/temp");
        System.out.println(request.getSession().getServletContext().getContextPath());
        File file = new File(temp);
        if (!file.exists()) {
            file.mkdir();
        }
        
        String AccountExcel = temp + File.separator + "Account.xlsx";
        HashMap<String, ArrayList<Revenue>> revenue = PaymentDAO.retrieveAllRevenueData();
        HashMap<String, ArrayList<Deposit>> deposit = PaymentDAO.retrieveAllDepositData();
        HashMap<String, ArrayList<Expense>> expense = PaymentDAO.retrieveAllExpenseData();      
        
        ArrayList<BankDeposit> bankDeposit = new ArrayList<>();
        BankDeposit d1 = new BankDeposit("Transfer", "1/9/2018", "Deyu", 300);
        bankDeposit.add(d1);
        BankDeposit d2 = new BankDeposit("Cheque", "13/9/2018", "LOL", 225);
        bankDeposit.add(d2);
        BankDeposit d3 = new BankDeposit("Transfer", "25/9/2018", "GG", 180);
        bankDeposit.add(d3);
        
        //System.out.println("revenue " + revenue + " deposit" + deposit + " expense " + expense);
        
        FinancialReportDAO.FinancialReportGeneration(AccountExcel,revenue, deposit, expense, bankDeposit, "010-91303-9", 0);
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
