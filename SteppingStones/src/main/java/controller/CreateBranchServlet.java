/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Branch;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BranchDAO;

/**
 *
 * @author Shawn
 */
@WebServlet(name = "CreateBranchServlet", urlPatterns = {"/CreateBranchServlet"})
public class CreateBranchServlet extends HttpServlet {

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

        String branchName = request.getParameter("branchName");
        String branchAddress = request.getParameter("branchAddress");
        int phoneNo = 0;
        if (request.getParameter("phoneNumber") != null && request.getParameter("phoneNumber") != "") {
            phoneNo = Integer.parseInt(request.getParameter("phoneNumber"));
        }
        String startDate = request.getParameter("startingDate");

        BranchDAO branchdao = new BranchDAO();
        Branch existingBranch = branchdao.retrieveBranchByName(branchName);
        if (existingBranch != null) {
            request.setAttribute("existingBranch", existingBranch.getName());
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreateBranch.jsp");
            dispatcher.forward(request, response);
        } else {
            Branch tempBranch = new Branch(branchName, startDate, branchAddress, phoneNo);
            boolean status = branchdao.addBranch(tempBranch);
            if(status){
                request.setAttribute("status", "Branch created successfully!");
            }else{
                request.setAttribute("errorMsg", "Error creating subject!");
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreateBranch.jsp");
            dispatcher.forward(request, response);
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
