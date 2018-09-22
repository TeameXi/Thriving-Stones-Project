 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Admin;
import entity.Users;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AdminDAO;
import model.BranchDAO;
import model.GeneratePassword;
import model.SendMail;
import model.UsersDAO;

@WebServlet(name = "CreateAdminServlet", urlPatterns = {"/CreateAdminServlet"})
public class CreateAdminServlet extends HttpServlet {

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
  
        String admin_username = request.getParameter("admin_name");
        int branch_id = Integer.parseInt(request.getParameter("branch"));
        String adminEmail = request.getParameter("adminEmail");
        String password = GeneratePassword.random(16);
        
        BranchDAO branchDao = new BranchDAO();
        AdminDAO adminDao = new AdminDAO();
        Admin existingAdmin = adminDao.retrieveAdminByName(admin_username);
        /*if (existingAdmin != null) {
            request.setAttribute("existingAdmin", existingAdmin.getAdmin_username());
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreateAdmin.jsp");
            dispatcher.forward(request, response);
        } else {*/
            Admin tempAdmin = new Admin(admin_username, adminEmail, branch_id);
            int status = adminDao.addAdmin(tempAdmin);
            
            RequestDispatcher dispatcher;
            if(status>0){
                UsersDAO userDAO = new UsersDAO();
                String username = admin_username;
                int i = -1;
                int temp = 0;
                while(i<0){
                    if(temp == 0){
                        temp++;
                        username = admin_username + "." + (Calendar.getInstance().get(Calendar.YEAR));
                        if(userDAO.retrieveUserByUsername(username) < 1){
                            i = 0;
                        }
                    }else{
                        username = admin_username + temp + "." + (Calendar.getInstance().get(Calendar.YEAR));
                        temp++;
                        if(userDAO.retrieveUserByUsername(username) < 1){
                            i = 0;
                        }
                    }
                }

                Users tempUser = new Users(username, password, "admin", status, branch_id);
                boolean userStatus = userDAO.addUser(tempUser);
                if(userStatus){
                    String href = request.getHeader("origin")+request.getContextPath()+"/Login.jsp";
                    String subject = "Stepping Stones Tuition Center Branch Admin's Account Creation";
                    String text = "Your account has been created.(Admin Account for " + branchDao.retrieveBranchById(branch_id).getName() + ")\n\nBelow is the username and password to access your account: \nUsername: " + username
                            + "\nPassword: " + password + "\n\nYou can Login via "+href; 
                    if(adminEmail != null && !adminEmail.equals("")){
                        SendMail.sendingEmail(adminEmail, subject, text);
                    }
                    request.setAttribute("status", "Admin created successfully!");
                    dispatcher = request.getRequestDispatcher("DisplayAdmins.jsp");
                }else{
                    request.setAttribute("errorMsg", "Error creating admin!");
                    dispatcher = request.getRequestDispatcher("CreateAdmin.jsp");
                }
            }else{
                request.setAttribute("errorMsg", "Admin Exist Already");
                dispatcher = request.getRequestDispatcher("CreateAdmin.jsp");
            }
            dispatcher.forward(request, response);
        //}

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
