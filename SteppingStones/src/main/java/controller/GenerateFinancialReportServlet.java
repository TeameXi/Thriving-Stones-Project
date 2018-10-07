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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.FinancialReportDAO;
import model.PaymentDAO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Desmond
 */
@WebServlet(name = "GenerateFinancialReportServlet", urlPatterns = {"/GenerateFinancialReportServlet"})
public class GenerateFinancialReportServlet extends HttpServlet {

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
       // int month = Integer.parseInt(request.getParameter("month"));
        //int year = Integer.parseInt(request.getParameter("year"));
        Calendar mCalendar = Calendar.getInstance();
        //int monthInt = mCalendar.get(Calendar.MONTH) -1;
        String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + "_" + mCalendar.get(Calendar.YEAR);
        
        String temp = this.getServletContext().getRealPath("/temp");
        
        File file = new File(temp);
        if (!file.exists()) {
            file.mkdir();
        }
        
        String AccountExcel = temp + File.separator + "Account_" + month + ".xlsx";
        HashMap<String, ArrayList<Revenue>> revenue = PaymentDAO.retrieveAllRevenueData();
        HashMap<String, ArrayList<Deposit>> deposit = PaymentDAO.retrieveAllDepositData();
        HashMap<String, ArrayList<Expense>> expense = PaymentDAO.retrieveAllExpenseData();   
        ArrayList<BankDeposit> bankDeposit = PaymentDAO.retrieveAllBankDepositData();
        
        XSSFWorkbook workbook = FinancialReportDAO.FinancialReportGeneration1(AccountExcel,revenue, deposit, expense, bankDeposit, "010-91303-9", 0);
        
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Account_" + month + ".xlsx");
            
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new ServletException("Exception in DownLoad Excel Servlet", e);
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
