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
import model.LevelDAO;
import model.ParentChildRelDAO;
import model.ParentDAO;
import model.StudentDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "StudentApplicationServlet", urlPatterns = {"/StudentApplicationServlet"})
public class StudentApplicationServlet extends HttpServlet {

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
        
        String studentNRIC = request.getParameter("studentNRIC");
        String studentName = request.getParameter("studentName");
        String BOD = request.getParameter("bday");
        String gender = request.getParameter("gender");
        String lvl = request.getParameter("lvl");
        int phone = Integer.parseInt(request.getParameter("phone"));
        String stuEmail = request.getParameter("studentEmail");
        String stuPassword = request.getParameter("studentPassword");
        String parentName = request.getParameter("parentName");
        String parentNationality = request.getParameter("parentNationality");
        String parentCompany = request.getParameter("parentCompany");
        String parentDesgination = request.getParameter("parentDesgination");
        int parentPhone = Integer.parseInt(request.getParameter("parentPhone"));
        String parentEmail = request.getParameter("parentEmail");
        String address = request.getParameter("address");

        int level_id = LevelDAO.retrieveLevelID(lvl);
        System.out.println("LOL" + lvl);
        //Admin admin = (Admin) request.getSession().getAttribute("admin");
        //int branch_id = admin.getBranchID();
        
        StudentDAO.insertStudent(studentNRIC, studentName, phone, address, BOD, gender, stuEmail, stuPassword, level_id, 1); // replace with branch_id
        ParentDAO.insertParent(parentName, parentNationality, parentCompany, parentDesgination, parentPhone, parentEmail, parentPhone, 1); //replace with bracnch_id
        ParentChildRelDAO.insertParentChildRel(parentName, studentName);
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
