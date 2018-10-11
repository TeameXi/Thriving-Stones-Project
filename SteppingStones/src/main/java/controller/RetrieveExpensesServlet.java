/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import entity.Expense;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ExpenseDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Desmond
 */
@WebServlet(name = "RetrieveExpensesServlet", urlPatterns = {"/RetrieveExpensesServlet"})
public class RetrieveExpensesServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String action = request.getParameter("action");

            if (action.equals("retrieve")) {
                 JSONArray array = new JSONArray();
                ArrayList<Expense> expenses = ExpenseDAO.listAllExpenses();

                for (Expense e : expenses) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", e.getPaymentID());
                    obj.put("type", (e.getTutorID() == 0? "Bank Expenses" : "Cashbox Expenses") );
                    obj.put("description", e.getDescription());
                    obj.put("amount", e.getAmount());
                    obj.put("paymentdate", e.getDate());
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
            }else if (action.equals("delete")) {
                int payment_id = Integer.parseInt(request.getParameter("expenseID"));
                 boolean success = ExpenseDAO.deleteExpense(payment_id);
                 JSONObject toReturn = new JSONObject().put("data", success);
                 String json = toReturn.toString();
                 out.println(json);
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
