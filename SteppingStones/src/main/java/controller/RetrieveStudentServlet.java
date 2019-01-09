/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Student;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.StudentDAO;
import org.json.JSONObject;
/**
 *
 * @author Zang Yu
 */
@WebServlet(name = "RetrieveStudentServlet", urlPatterns = {"/RetrieveStudentServlet"})
public class RetrieveStudentServlet extends HttpServlet {
    
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
            StudentDAO studentDao = new StudentDAO();
            Student student = studentDao.retrieveStudentbyID(studentID,branchID);

            if(student != null){
                int id = student.getStudentID();
                String nric = student.getStudentNRIC();
                String fullname = student.getName();
                String birth_date = student.getBOD();
                String gender = student.getGender();
                String level = student.getLevel();
                int branch_id = student.getBranchlID();
                String address = student.getAddress();
                int phone = student.getPhone();
                String email = student.getEmail();
                double reqAmt = student.getReqAmt();
                double outstandingAmt = student.getOutstandingAmt();
                String school = student.getSchool();
                String stream = student.getStream();
                   
                obj.put("id",id);
                obj.put("nric",nric);
                obj.put("fullname",fullname); 
                obj.put("birth_date",birth_date);
                obj.put("gender",gender);
                obj.put("level",level);
                obj.put("branch_id",branch_id);                
                obj.put("address",address);
                obj.put("phone",phone);
                obj.put("email",email);
                obj.put("reqAmt",reqAmt);
                obj.put("outstandingAmt",outstandingAmt);
                
                if(school != null && !school.equals("")){
                    obj.put("school", school);
                }
                
                if(stream != null && !stream.equals("")){
                    obj.put("stream", stream);
                }                            
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
