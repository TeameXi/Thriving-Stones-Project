/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TutorDAO;
import org.json.JSONObject;

/**
 *
 * @author Hui Xin
 */
@WebServlet(name = "UpdateTutorServlet", urlPatterns = {"/UpdateTutorServlet"})
public class UpdateTutorServlet extends HttpServlet {

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


        try (PrintWriter out = response.getWriter()) {
            String tutorID = request.getParameter("tutorID");
            if (tutorID != "") {

                int id = Integer.parseInt(tutorID);
                String nric = request.getParameter("nric");

                int phone = 0;

                if(!request.getParameter("phone").equals("")){
                    phone = Integer.parseInt(request.getParameter("phone"));
                }
                String address = request.getParameter("address");
                String qualification = request.getParameter("qualification");
                String dob = request.getParameter("dob");
                String gender = request.getParameter("gender").trim();
                String email = request.getParameter("email");

                TutorDAO tutorDao = new TutorDAO();
                boolean status = tutorDao.updateTutor(id,nric,phone,address,qualification,dob,gender,email);
                if(status){
                    out.println(1);
                }else{
                    out.println(0);
                }
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
