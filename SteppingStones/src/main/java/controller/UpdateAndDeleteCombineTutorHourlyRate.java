/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TutorHourlyRateDAO;

/**
 *
 * @author MOH MOH SAN
 */
@WebServlet(name = "UpdateAndDeleteCombineTutorHourlyRate", urlPatterns = {"/UpdateAndDeleteCombineTutorHourlyRate"})
public class UpdateAndDeleteCombineTutorHourlyRate extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String action = request.getParameter("action");
            String ids = request.getParameter("tutorID");
           
            
            int tutorId = 0;
            String levelId = "";
            int subjectId=0;
            int branchId = 0;
            double payRate = 0;
            if(!ids.equals("")){
                String[]idsArr = ids.split("_");
                if(idsArr.length == 4){
                    tutorId = Integer.parseInt(idsArr[0]);
                    levelId = idsArr[1];
                    subjectId = Integer.parseInt(idsArr[2]);
                    branchId = Integer.parseInt(idsArr[3]);
                }
            }
            
         
            //System.out.println(tutorId+":"+levelId+":"+subjectId);
            if(tutorId ==0 || levelId.isEmpty() || subjectId == 0 || branchId == 0){
                out.println(-1);
            }else{
                switch(action){
                    case "edit":
                        String pay = request.getParameter("payRate");
                        if(!pay.equals("")){
                            payRate = Double.parseDouble(pay);
                        }
                        boolean updateStatus = TutorHourlyRateDAO.updateTutorPayRateForCombine(tutorId, levelId, subjectId, branchId,payRate);
                        if(updateStatus){
                            out.println(1);
                        }else{
                            out.println(-1);
                        }
                        break;
                    case "delete":
                        System.out.println(tutorId+" "+levelId+" "+subjectId+" "+branchId);
                        boolean deleteStatus = TutorHourlyRateDAO.deleteTutorPayRateForCombine(tutorId, levelId, subjectId, branchId);
                        if(deleteStatus){
                            out.println(1);
                        }else{
                            out.println(-1);
                        }
                        break;
                    default:
                        out.println(-1);
                        break;
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
