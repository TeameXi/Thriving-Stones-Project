/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Admin;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AdminDAO;
import model.BranchDAO;
import org.json.JSONObject;

/**
 *
 * @author MOH MOH SAN
 */
@WebServlet(name = "BranchAdminServlet", urlPatterns = {"/BranchAdminServlet"})
public class BranchAdminServlet extends HttpServlet {

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
           String action = request.getParameter("action");
            int adminId = 0;
            if(!request.getParameter("admin_id").equals("")){
               adminId = Integer.parseInt(request.getParameter("admin_id"));
            }
            
            if(adminId == 0){
                out.println(-1);
            }
                
            if(action.equals("retrieve")){
                AdminDAO adminDao = new AdminDAO();
                Admin admin = adminDao.retrieveAdminById(adminId);
                JSONObject obj = new JSONObject();
                obj.put("name",admin.getAdmin_username());
                obj.put("branchId", admin.getBranch_id());
                obj.put("email",admin.getEmail());
                out.println(obj);
           }else if(action.equals("delete")){
                boolean deleteStatus = AdminDAO.deleteAdmin(adminId);
                if(deleteStatus){
                    out.println(1);
                }else{
                    out.println(-1);
                }
           }else{
                String name = request.getParameter("name");
                String email = request.getParameter("email");
             
                int branchID = 0;
                if(!request.getParameter("branch").equals("")){
                    branchID = Integer.parseInt(request.getParameter("branch"));
                }
                
                boolean updateStatus = AdminDAO.updateAdmin(name,email,branchID,adminId);
 
                if(updateStatus){
                    JSONObject r = new JSONObject();
                    BranchDAO branchDAO = new BranchDAO();
                    String branchName = branchDAO.retrieveBranchById(branchID).getName();
                    r.put("branch", branchName);
                    out.println(r);
                }else{
                    out.println(-1);
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
