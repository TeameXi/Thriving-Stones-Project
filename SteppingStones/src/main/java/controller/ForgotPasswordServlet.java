/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Admin;
import entity.Parent;
import entity.Student;
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
import model.AdminDAO;
import model.ParentDAO;
import model.SendMail;
import model.SendSMS;
import model.StudentDAO;
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
       
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        ArrayList<String> errorList = new ArrayList<>();
        
        if(username == null || username.equals("")){
            errorList.add("Username is required");
        }
        
        if((email == null || email.equals("")) && (phone == null || phone.equals("")) ){
            errorList.add("Please enter email or phone number");
        }
        
        if(errorList.isEmpty()){
            UsersDAO users = new UsersDAO();
            Users user = users.retrieveUserByUsernames(username);
            
            if(user == null){
                errorList.add("User does not exist");
                request.setAttribute("error", errorList);
            }else{
                String password = user.getPassword();
                String userEmail = "";
                int userPhone = 0;
                
                if(user.getRole().equals("admin")){
                    AdminDAO adminDao = new AdminDAO();
                    Admin admin = adminDao.retrieveAdminById(user.getRespectiveID());
                    userEmail = admin.getEmail();
                    
                }else if(user.getRole().equals("tutor")){
                    TutorDAO tutorDAO = new TutorDAO();
                    Tutor tutor = tutorDAO.retrieveSpecificTutorById(user.getRespectiveID());
                    userEmail = tutor.getEmail();
                    userPhone = tutor.getPhone();
                }else if(user.getRole().equals("student")){
                    Student student = StudentDAO.retrieveStudentbyID(user.getRespectiveID());
                    userEmail = student.getEmail();
                    userPhone = student.getPhone();
                }else{
                    ParentDAO parentDAO = new ParentDAO();
                    Parent parent = parentDAO.retrieveSpecificParentById(user.getRespectiveID());
                    userEmail = parent.getEmail();
                    userPhone = parent.getPhone();
                }
                String href = request.getHeader("origin")+request.getContextPath()+"/ChangePassword?";
                String msg = "p="+password+"&u="+username;
                if(email != null && !email.equals("") && email.equals(userEmail)){
                    try {
                        String subject = "Stepping Stones Tuition Center Account Password Reset";
                        //String encrypt = encrypt(user.getPassword()+"&"+user.getEmail()+"&"+role, "a");
                        
                        String text = "Dear " + username + ", "
                            + "\n\nWe have received a request to reset your Stepping Stones Tuition Center Account password."
                            + "\nSimply click the link below to reset your password."
                            + "\n"+href+msg;
                        //for local
                        //SendMail.sendingEmail(userEmail, subject, text);
                        //for deploy
                        SendMail.sendingEmailUsingSendGrid(userEmail, subject, text);
                        request.setAttribute("status", "Email sent. please check your email for instruction to reset your password.");
                    } catch (Exception ex) {
                        Logger.getLogger(ForgotPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(phone!= null && !phone.equals("") && Integer.parseInt(phone) == userPhone){
                    
                    String text = "We have received a request to reset your Stepping Stones Tuition Center Account password."
                            + "\nSimply click the link below to reset your password."
                            + "\n"+href+msg;
                    String phoneNum = "+65" + phone;
                    SendSMS.sendingSMS(phoneNum, text);
                }else{
                    errorList.add("User not found");
                    request.setAttribute("error", errorList);
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
