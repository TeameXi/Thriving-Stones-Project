/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Parent;
import entity.Student;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ParentChildRelDAO;
import model.ParentDAO;
import model.StudentDAO;
import org.json.JSONObject;

/**
 *
 * @author Zang Yu
 */
@WebServlet(name = "RetrieveParentServlet", urlPatterns = {"/RetrieveParentServlet"})
public class RetrieveParentServlet extends HttpServlet {
    
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
        response.setContentType("text/html; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
          
            JSONObject obj = new JSONObject();
            int studentID = Integer.parseInt(request.getParameter("studentID"));
            int branchID = Integer.parseInt(request.getParameter("branch_id"));
            ParentChildRelDAO parentChildRelDAO = new ParentChildRelDAO();
            int parentID = parentChildRelDAO.getParentID(studentID);
            ParentDAO parentDao = new ParentDAO();
            Parent parent = parentDao.retrieveSpecificParentById(parentID);
            
            if(parent != null){
                int id = parent.getParentId();                
                String fullname = parent.getName();
                String nationality = parent.getNationality();
                String company = parent.getCompany();
                String designation = parent.getDesignation();
                int phone = parent.getPhone();
                String email = parent.getEmail();
                
                   
                obj.put("id",id);
                obj.put("fullname",fullname); 
                obj.put("nationality",nationality);
                obj.put("company",company);
                obj.put("designation",designation);
                obj.put("phone",phone);                
                obj.put("email",email);
                                               
            }
            
            out.println(obj);
       
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

