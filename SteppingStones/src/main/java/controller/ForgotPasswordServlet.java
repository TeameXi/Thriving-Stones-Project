/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.TutorDAO;
import entity.Tutor;
import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.SendMail;
import model.UsersDAO;

/**
 *
 * @author Riana
 */
@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/ForgotPasswordServlet"})
public class ForgotPasswordServlet extends HttpServlet {

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
       
        String email = request.getParameter("email");
        String role = request.getParameter("type");
        
        ArrayList<String> errorList = new ArrayList<>();
        
        if(email == null || email.equals("")){
            errorList.add("username is required");
        }
        
        if(role == null || role.equals("")){
            errorList.add("please select role");
        }
        
        if(errorList.isEmpty()){
            UsersDAO users = new UsersDAO();
            Users user = users.retrieveUserByUsernameRole(role, email);
            
            if(user == null){
                errorList.add("user does not exist");
                request.setAttribute("error", errorList);
            }else{
                
                try {
                    String subject = "Stepping Stones Tuition Center Account Password Reset";
                    //String encrypt = encrypt(user.getPassword()+"&"+user.getEmail()+"&"+role, "a");
                    String href = request.getHeader("origin")+request.getContextPath()+"/ChangePassword?";
                    String msg = "p="+user.getPassword()+"&u="+user.getEmail()+"&r="+role;
                    String text = "Dear " + user.getEmail() + ", "
                        + "\n\nWe have received a request to reset your Stepping Stones Tuition Center Account password."
                        + "\nSimply click the link below to reset your password."
                        + "\n"+href+msg;
                    SendMail.sendingEmail(user.getMailingAddress(), subject, text);
                    request.setAttribute("status", "Email sent. please check your email for instruction to reset your password.");
                } catch (Exception ex) {
                    Logger.getLogger(ForgotPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            request.setAttribute("error", errorList);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("ForgotPassword.jsp");
        dispatcher.forward(request, response);
    }
    public static String encrypt(String strClearText,String strKey) throws Exception{
	String strData="";
	
	try {
		SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
		Cipher cipher=Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
		byte[] encrypted=cipher.doFinal(strClearText.getBytes());
		strData=new String(encrypted);
		
	} catch (Exception e) {
		e.printStackTrace();
		throw new Exception(e);
	}
	return strData;
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
