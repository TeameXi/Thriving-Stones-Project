/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import entity.Student;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.PaymentDAO;
import model.StudentDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(urlPatterns = {"/UpdateTuitionFeesServlet"})
public class UpdateTuitionFeesServlet extends HttpServlet {

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
            String studentIDStr = request.getParameter("studentID");
            String classIDStr = request.getParameter("classID");
            String feesStr = request.getParameter("fees");
            
            int studentID = 0;
            int classID = 0;
            double fees = 0;
            
            if(studentIDStr != null && classIDStr != null && feesStr != null){
                studentID = Integer.parseInt(studentIDStr);
                classID = Integer.parseInt(classIDStr);
                fees = Double.parseDouble(feesStr);
            }
         
            System.out.println(studentID+":"+classID+":"+fees);
            if(studentID ==0 || classID == 0){
                out.println(-1);
            }else{
                switch(action){
                    case "update":
                        Student stu = StudentDAO.retrieveStudentbyID(studentID);
                        boolean updateStatus = PaymentDAO.updateTuitionFees(studentID, classID, fees);
                        double oldDepositAmt = PaymentDAO.getOldDepositAmt(studentID, classID);
                        double outstandingDepositAmt = PaymentDAO.getOutstandingDepositAmt(studentID, classID);
                        double diff = oldDepositAmt - fees;
                        boolean updateDeposit = false;
                        boolean updateTotalOutstandingFees = false;
                        if(oldDepositAmt == 0 || outstandingDepositAmt - diff < 0){
                            updateDeposit = true;
                            updateTotalOutstandingFees = true;
                        }else{
                            updateDeposit = PaymentDAO.updateDepositAmount(studentID, classID, outstandingDepositAmt - diff, oldDepositAmt - diff);
                            updateTotalOutstandingFees = StudentDAO.updateStudentTotalOutstandingFees(studentID, stu.getOutstandingAmt() - diff);
                        }
                        if(updateStatus){
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
