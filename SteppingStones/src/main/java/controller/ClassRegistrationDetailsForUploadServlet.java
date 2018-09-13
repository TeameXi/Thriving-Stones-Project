/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.StudentClassDAO;
import model.StudentDAO;

/**
 *
 * @author Riana
 */
@WebServlet(name = "ClassRegistrationDetailsForUploadServlet", urlPatterns = {"/ClassRegistrationDetailsForUploadServlet"})
public class ClassRegistrationDetailsForUploadServlet extends HttpServlet {

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
        //get all the values
        int branchID = Integer.parseInt(request.getParameter("branch_id"));
        String stud_ids[] = request.getParameterValues("arr_id[]");
        String classid[] = request.getParameterValues("arr_classid[]");
        String deposit[] = request.getParameterValues("arr_deposit[]");
        String outstandingDeposit[] = request.getParameterValues("arr_outstandingdeposit[]");
        String monthly[] = request.getParameterValues("arr_monthly[]");
        String outstandingTuition[] = request.getParameterValues("arr_outstandingTuition[]");
        String join[] = request.getParameterValues("arr_joinDate[]");
        
        if(stud_ids.length>0){
            for (int i = 0; i < stud_ids.length; i++) {
                if ("".equals(stud_ids[i].trim())) {
                    continue;
                }
                
                int stud_id = 0;
                if (!"".equals(stud_ids[i])) {
                    stud_id = Integer.parseInt(stud_ids[i].trim());
                }
                
                int class_id = 0;
                if (!"".equals(classid[i])) {
                    class_id = Integer.parseInt(classid[i].trim());
                }
                
                double depositAmt = 0;
                if (!"".equals(deposit[i])) {
                    depositAmt = Double.parseDouble(deposit[i].trim());
                }
                
                double outstandingDepositAmt = 0;
                if (!"".equals(outstandingDeposit[i])) {
                    outstandingDepositAmt = Double.parseDouble(outstandingDeposit[i].trim());
                }
                
                double monthlyFee = 0;
                if (!"".equals(monthly[i])) {
                    monthlyFee = Double.parseDouble(monthly[i].trim());
                }  
                
                double outstandingTuitionFee = 0;
                if (!"".equals(outstandingTuition[i])) {
                    outstandingTuitionFee = Double.parseDouble(outstandingTuition[i].trim());
                }

                boolean status = StudentClassDAO.saveStudentsToRegisterClass(class_id, stud_id, depositAmt, outstandingDepositAmt, monthlyFee, 
                            outstandingTuitionFee, join[i]);
                if(status){
                    Student stu = StudentDAO.retrieveStudentbyID(stud_id,branchID);
                    double reqAmt = stu.getReqAmt(); //reqAmt meaning 1 mth or for whole term how to calculate
                    double outstandingAmt = stu.getOutstandingAmt() + outstandingDepositAmt + outstandingTuitionFee;
                    boolean update = StudentDAO.updateStudentFees(stud_id, reqAmt, outstandingAmt); 
                    if(update){
                        request.setAttribute("status", "Successfully Registered.");
                        
                    }
                }
                

            }
            
        }
        response.sendRedirect("DisplayStudents.jsp");
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
