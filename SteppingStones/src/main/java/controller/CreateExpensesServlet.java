/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Expense;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ExpenseDAO;

/**
 *
 * @author Riana
 */
@WebServlet(name = "CreateExpensesServlet", urlPatterns = {"/CreateExpensesServlet"})
public class CreateExpensesServlet extends HttpServlet {

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
       
        List<String> expensesList = new ArrayList<>();
        
        boolean stillExist = true;
        int counter = 0;
        while(stillExist){
            int expTypeInt = Integer.parseInt(request.getParameter("type[" + counter + "]"));
            String description = request.getParameter("description[" + counter + "]");
            double amount = Double.parseDouble(request.getParameter("amount[" + counter + "]"));
            String date = request.getParameter("paymentdate[" + counter + "]");
            if(request.getParameter("type[" + (counter + 1) + "]") == null){
                stillExist = false;
            }
            expensesList.add("("+expTypeInt+",'"+description+"',"+amount+",'"+date+ "')");
            counter++;
        }
        
        ExpenseDAO.insertExpense(expensesList);
        
        response.sendRedirect("Expenses.jsp");
        
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
