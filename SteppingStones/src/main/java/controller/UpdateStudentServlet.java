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
import model.StudentDAO;

/**
 *
 * @author MOH MOH SAN
 */
@WebServlet(name = "UpdateStudentServlet", urlPatterns = {"/UpdateStudentServlet"})
public class UpdateStudentServlet extends HttpServlet {

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
            Map<String, Object> updates = new HashMap<>();
                    
            String studentID = request.getParameter("studentID");
            String name = request.getParameter("name");
            String age = (String) request.getParameter("age");
            String gender = request.getParameter("gender");
            String lvl = request.getParameter("lvl");
            String address = request.getParameter("address");
            String phone = (String) request.getParameter("phone");
            Double r_amount = Double.parseDouble(request.getParameter("r_amount"));
            Double o_amount = Double.parseDouble(request.getParameter("o_amount"));
            

            if(studentID != null && !studentID.equals("")){
                if(name != null && !name.equals("")){
                    updates.put("name", name);
                }
                
                if(age != null && !age.equals("")){
                    updates.put("age", age);
                }
                
                
                if(lvl != null && !lvl.equals("")){
                    updates.put("level", gender);
                }        
                
                if(address != null && !address.equals("")){
                    updates.put("address", address);
                }
                
                if(phone != null && !phone.equals("")){
                    if(phone.length() == 8){
                        updates.put("phone", phone);
                    }
                }
                
                if(gender != null && !gender.equals("")){
                    String genderUpdate = gender.toUpperCase();
                    if(genderUpdate.equals("F") || genderUpdate.equals("M")){
                        updates.put("gender", gender);
                    }
                }
                
                if(r_amount != null){
                    updates.put("reqAmt",r_amount);
                }
                
                if(o_amount != null){
                    updates.put("outstandingAmt",o_amount);
                }
                
                if(!updates.isEmpty()){
                    StudentDAO student = new StudentDAO();
                    boolean status = student.updateStudent(studentID, updates);
                    if(status){
                        out.println(1);
                    }else{
                        out.println(0);
                    }
                   
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
