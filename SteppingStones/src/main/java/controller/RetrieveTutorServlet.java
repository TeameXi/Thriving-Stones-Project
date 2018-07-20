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
import model.FirebaseConnection;
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
            String tutorID = request.getParameter("tutorID");
            FirebaseConnection.initFirebase();
            TutorDAO tutorDao = new TutorDAO();
            Tutor tu = tutorDao.retrieveSpecificTutor(tutorID);
            if(tu != null){
                String name = tu.getName();
                int age = tu.getAge();
                String gender = tu.getGender();
                String email = tu.getEmail();
                String phone = tu.getPhone();
                obj.put("name",name);
                obj.put("age",age);
                obj.put("gender",gender);
                obj.put("email", email);
                obj.put("phone", phone);
               
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
