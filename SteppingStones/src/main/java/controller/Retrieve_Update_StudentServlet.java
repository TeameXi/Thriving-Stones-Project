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
 * @author DEYU
 */
@WebServlet(name = "Retrieve_Update_StudentServlet", urlPatterns = {"/Retrieve_Update_StudentServlet"})
public class Retrieve_Update_StudentServlet extends HttpServlet {

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
//        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        
        int studentID = Integer.parseInt(request.getParameter("studentID"));
        
        Student currStu = StudentDAO.retrieveStudentbyID(studentID);
        
        JSONObject obj = new JSONObject();
        try{
            String name = currStu.getName();
            String lvl = currStu.getLevel();
            String address = currStu.getAddress();
            int phone = currStu.getPhone();
            double required_amount = currStu.getReqAmt();
            double outstanding_amount = currStu.getOutstandingAmt();
            obj.put("name",name);
            obj.put("lvl", lvl);
            obj.put("address", address);
            obj.put("phone", phone);
            obj.put("r_amount", required_amount);
            obj.put("o_amount",outstanding_amount);
    
            out.println(obj);
        }finally{
            out.flush();
            out.close();
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
