/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Tutor;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TutorDAO;
import org.json.JSONObject;

/**
 *
 * @author MOH MOH SAN
 */
@WebServlet(name = "RetrieveTutorServlet", urlPatterns = {"/RetrieveTutorServlet"})
public class RetrieveTutorServlet extends HttpServlet {

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
            int tutorID = Integer.parseInt(request.getParameter("tutorID"));
            TutorDAO tutorDao = new TutorDAO();
            Tutor tu = tutorDao.retrieveSpecificTutorById(tutorID);

            if(tu != null){
                int id = tu.getTutorId();
                String nric = tu.getNric();
                String fullname = tu.getName();
                int phone = tu.getPhone();
                String address = tu.getAddress();
                String qualification = tu.getQualification();
                String birth_date = tu.getBirth_date();
                String gender = tu.getGender();
                String email = tu.getEmail();
                int branch_id = tu.getBranch_id();
                   
                obj.put("id",id);
                obj.put("nric",nric); 
                obj.put("fullname",fullname); 
                obj.put("phone",phone);
                obj.put("address",address);
                obj.put("qualification",qualification);
                obj.put("birth_date",birth_date);
                obj.put("gender",gender);
                obj.put("email",email);
                obj.put("branch_id",branch_id);
                                                
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
