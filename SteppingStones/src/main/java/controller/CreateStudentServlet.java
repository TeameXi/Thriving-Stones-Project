/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Users;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.GeneratePassword;
import model.ParentChildRelDAO;
import model.ParentDAO;
import model.SendMail;
import model.SendSMS;
import model.StudentDAO;
import model.UsersDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "CreateStudentServlet", urlPatterns = {"/CreateStudentServlet"})
public class CreateStudentServlet extends HttpServlet {

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

        String studentName = request.getParameter("studentName");
        String lvl = request.getParameter("lvl");
        
        int phone = 0;
        if(request.getParameter("phone") != null && !"".equals(request.getParameter("phone"))){
            phone = Integer.parseInt(request.getParameter("phone"));
        }
        int branchID = 0;
        if(request.getParameter("branch") != null && !"".equals(request.getParameter("branch"))){
            branchID = Integer.parseInt(request.getParameter("branch"));
        }
        int levelID = 0;
        if(lvl != null && !lvl.equals("")){
            levelID = Integer.parseInt(lvl);
        }
        double regFees = 0;
        if(request.getParameter("regFees") != null && !"".equals(request.getParameter("regFees"))){
            regFees = Double.parseDouble(request.getParameter("phone"));
        }
        String stuEmail = request.getParameter("studentEmail");
        String stuPassword = GeneratePassword.random(16);
        String parentName = request.getParameter("parentName");
        String parentPassword = GeneratePassword.random(16);
        int parentPhone = Integer.parseInt(request.getParameter("parentPhone"));
        String parentEmail = request.getParameter("parentEmail");     
        
        String href =  request.getHeader("origin")+request.getContextPath()+"/Login.jsp";
        int insertStudent = StudentDAO.insertStudent(studentName, phone,stuEmail, stuPassword, levelID, branchID);
        if(insertStudent>0){
            UsersDAO userDAO = new UsersDAO();
            String username = studentName.replace(' ', '.');
            int i = -1;
            int temp = 0;
            while(i<0){
                if(temp == 0){
                    temp++;
                    username = studentName.replace(' ', '.') + "." + (Calendar.getInstance().get(Calendar.YEAR));
                    if(userDAO.retrieveUserByUsername(username) < 1){
                        i = 0;
                    }
                }else{
                    username = studentName.replace(' ', '.') + temp + "." + (Calendar.getInstance().get(Calendar.YEAR));
                    temp++;
                    if(userDAO.retrieveUserByUsername(username) < 1){
                        i = 0;
                    }
                }
            }
            Users tempUser = new Users(username, stuPassword, "student", insertStudent, branchID);
            boolean userStatus = userDAO.addUser(tempUser);
            if(userStatus){
                String subject = "Stepping Stones Tuition Center Student's Account Creation";
                String text = "Thanks for choosing us. Your account has been created.\n\nBelow is the username and password to access your account: \nUsername: " + username 
                        + "\nPassword: " + stuPassword + "\n\nYou can Login via "+href; 
                if(stuEmail != null && !stuEmail.equals("")){
                    SendMail.sendingEmail(stuEmail, subject, text);
                }else if(phone != 0){
                    String phoneNum = "+65" + phone;
                    SendSMS.sendingSMS(phoneNum, text);
                }
            }
        }

        int insertParent = ParentDAO.insertParent(parentName, parentPhone, parentEmail, parentPassword, branchID);
        if(insertParent>0){
            UsersDAO userDAO = new UsersDAO();
            String username = parentName.replace(' ', '.');
            int i = -1;
            int temp = 0;
            while(i<0){
                if(temp == 0){
                    temp++;
                    username = parentName.replace(' ', '.') + "." + (Calendar.getInstance().get(Calendar.YEAR));
                    if(userDAO.retrieveUserByUsername(username) < 1){
                        i = 0;
                    }
                }else{
                    username = parentName.replace(' ', '.') + temp + "." + (Calendar.getInstance().get(Calendar.YEAR));
                    temp++;
                    if(userDAO.retrieveUserByUsername(username) < 1){
                        i = 0;
                    }
                }
            }

            Users tempUser = new Users(username, parentPassword, "parent", insertParent, branchID);
            boolean userStatus = userDAO.addUser(tempUser);
            if(userStatus){
                String subject = "Stepping Stones Tuition Center Parent's Account Creation";
                String text = "Thanks for choosing us. Your account has been created.\n\nBelow is the username and password to access your account: \nUsername: " + parentName 
                        + "\nPassword: " + parentPassword + "\n\nYou can Login via "+href; 
                if(parentEmail != null && !parentEmail.equals("")){
                    SendMail.sendingEmail(parentEmail, subject, text);
                }else if(parentPhone != 0){
                    String phoneNum = "+65" + parentPhone;
                    SendSMS.sendingSMS(phoneNum, text);
                }
            }
            
        }

        request.setAttribute("creation_status",""+(insertStudent>0 && insertParent>0));
        ParentChildRelDAO.insertParentChildRel(parentName, insertStudent, branchID);
        response.sendRedirect("RegisterForClasses.jsp?studentName="+studentName);     
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
