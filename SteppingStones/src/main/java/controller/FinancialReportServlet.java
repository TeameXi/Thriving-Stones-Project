/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.BankDeposit;
import entity.Expense;
import entity.Revenue;
import entity.Student;
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
import model.StudentDAO;

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
        HashMap<String, ArrayList<Revenue>> revenue = new HashMap();
        Revenue r1 = new Revenue("Thet Thet Yee", 4, "18/12/2017", 180, 50, 100, "18/07/2017", "");
        Revenue r2 = new Revenue("Thet Thet Mon", 3, "18/11/2017", 170, 60, 120, "18/06/2017", "");
        Revenue r3 = new Revenue("Thet Thet Wai", 4, "18/10/2017", 110, 80, 90, "18/05/2017", "18/09/2017");
        Revenue r4 = new Revenue("Thet Thet Lwin", 4, "18/09/2017", 100, 90, 150, "16/07/2017", "");
        Revenue r5 = new Revenue("Moh Moh San", 4, "18/08/2017", 150, 50, 70, "11/07/2017", "");
        ArrayList<Revenue> r = new ArrayList<>();
        r.add(r1);
        r.add(r2);
        r.add(r3);
        r.add(r4);
        r.add(r5);
        ArrayList<Revenue> rr = new ArrayList<>();
        rr.add(r2);
        rr.add(r5);
        rr.add(r4);
        revenue.put("Primary 1 English", r);
        revenue.put("Primary 1 Maths", rr);
        
        HashMap<String, ArrayList<Expense>> expense = new HashMap();
        Expense e1 = new Expense("Books", 200, "07/09/2018");
        Expense e2 = new Expense("TextBook", 569.9, "07/09/2018");
        Expense e3 = new Expense("Pens", 39.7, "07/08/2018");
        Expense e4 = new Expense("Pencils", 234.3, "07/09/2018");
        Expense e5 = new Expense("Inks", 122.4, "17/05/2018");
        ArrayList<Expense> ee = new ArrayList<>();
        ee.add(e1);
        ee.add(e4);
        ee.add(e5);
        ee.add(e3);
        ArrayList<Expense> e = new ArrayList<>();
        e.add(e2);
        e.add(e5);
        e.add(e3);
        expense.put("Teachers' Salary", e);
        expense.put("Bank Expenses", ee);
        
        ArrayList<BankDeposit> bd = new ArrayList<>();
        BankDeposit d1 = new BankDeposit("Transfer", "1/9/2018", "Deyu", 300);
        bd.add(d1);
        BankDeposit d2 = new BankDeposit("Cheque", "13/9/2018", "LOL", 225);
        bd.add(d2);
        BankDeposit d3 = new BankDeposit("Transfer", "25/9/2018", "GG", 180);
        bd.add(d3);
        
        FinancialReportDAO.FinancialReportGeneration(AccountExcel,revenue, expense, bd, "010-91303-9", 0);
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
