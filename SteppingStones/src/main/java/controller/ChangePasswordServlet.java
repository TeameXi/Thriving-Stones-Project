/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
import model.UsersDAO;

/**
 *
 * @author Riana
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/ChangePasswordServlet"})
public class ChangePasswordServlet extends HttpServlet {

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
        ArrayList<String> errorList = new ArrayList<>();
        /*String encrypt = request.getParameter("e");
        if(encrypt == null || encrypt.equals("")){
            errorList.add("invalid link");
        }
        
        
        try {
            if(errorList.isEmpty()){
                String decrypt = decrypt(encrypt, "a");
                String[] paramList = decrypt.split("&");
                String password = paramList[0];
                String username = paramList[1];
                String role = paramList[2];
                
                UsersDAO userDAO = new UsersDAO();
                Users user = userDAO.retrieveUserByUsername(role, username, password);
                
                if(user != null){
                    RequestDispatcher dispatcher = request.getRequestDispatcher("ChangePassword.jsp");
                    dispatcher.forward(request, response);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        /*String password = request.getParameter("p");
        String username = request.getParameter("u");
        String role = request.getParameter("r");
        
        if(password == null || password.equals("") || username == null || username.equals("") || role == null || role.equals("")){
            errorList.add("link is invalid");
        }
        if(errorList.isEmpty()){
            UsersDAO userDAO = new UsersDAO();
            Users user = userDAO.retrieveUserByUsernameRole(role, username);
            if(user != null){
                boolean match = user.authenticateUser(user, password);
                if(match){
                    request.setAttribute("user", user);
                    request.setAttribute("role", role);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("ChangePassword.jsp");
                    dispatcher.forward(request, response);
                }else{
                    errorList.add("invalid link");
                }
            }else{
                errorList.add("invalid link");
            }
        }else{
            errorList.add("invalid link");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("PageNotFound.jsp");
        dispatcher.forward(request, response);
        
    }
    public static String decrypt(String strEncrypted,String strKey) throws Exception{
	String strData="";
	
	try {
		SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes(),"Blowfish");
		Cipher cipher=Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, skeyspec);
		byte[] decrypted=cipher.doFinal(strEncrypted.getBytes());
		strData=new String(decrypted);
		
	} catch (Exception e) {
		e.printStackTrace();
		throw new Exception(e);
	}
	return strData;*/
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
