/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Riana
 */
@WebServlet(name = "ClassRegistrationForUploadServlet", urlPatterns = {"/ClassRegistrationForUploadServlet"})
public class ClassRegistrationForUploadServlet extends HttpServlet {

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
        
        String stud_ids[] = request.getParameterValues("arr_id[]");
        String stud_names[] = request.getParameterValues("arr_name[]");
        String stud_level[] = request.getParameterValues("arr_level[]");
        String stud_class[] = request.getParameterValues("arr_class[]");
        
        Map<String, List<String>> idWithClasses = new HashMap<String, List<String>>();
        
        if(stud_ids.length > 0){
           for(int i = 0; i < stud_ids.length; i++){
               String[] tempClass = stud_class[i].split(",");
               List<String> tempList = new ArrayList<String>();
               tempList.addAll(Arrays.asList(tempClass));
               idWithClasses.put(stud_ids[i], tempList);
           } 
        }
        
        request.setAttribute("idWithClasses", idWithClasses);

        RequestDispatcher view = request.getRequestDispatcher("ClassRegistrationDetailsForUpload.jsp");
        view.forward(request, response);
        
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
