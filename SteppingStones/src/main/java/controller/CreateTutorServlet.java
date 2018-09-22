/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Tutor;
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
import model.SendMail;
import model.TutorDAO;
import model.UsersDAO;

@WebServlet(name = "CreateTutorServlet", urlPatterns = {"/CreateTutorServlet"})
public class CreateTutorServlet extends HttpServlet {

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
        boolean status = true;
        String nric = request.getParameter("tutorNric");
        String name = request.getParameter("tutorName").trim().toLowerCase();
        int phone = 0;
        if (request.getParameter("phone") != null && request.getParameter("phone") != "") {
            phone = Integer.parseInt(request.getParameter("phone"));
        }
        String address = request.getParameter("address");
        String qualification = request.getParameter("qualification");
        String birth_date = request.getParameter("birthDate");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String password = GeneratePassword.random(16);
        
        int branch = 0;

        if (request.getParameter("branch") != null && request.getParameter("branch") != "") {
            branch = Integer.parseInt(request.getParameter("branch"));
        }

        TutorDAO tutordao = new TutorDAO();
        /*Tutor existingTutor = tutordao.retrieveSpecificTutor(name);
        if (existingTutor != null) {
            request.setAttribute("existingTutor", existingTutor.getName());
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreateTutor.jsp");
            dispatcher.forward(request, response);
        } else {*/
        Tutor tempTutor = new Tutor(nric, name, phone, address, qualification, birth_date, gender, email, branch);
        int tutorId = tutordao.addTutor(tempTutor);
        status = tutorId>0;
        if (status) {
            UsersDAO userDAO = new UsersDAO();
            String username = name.replace(' ', '.');
            int i = -1;
            int temp = 0;
            while(i<0){
                if(temp == 0){
                    temp++;
                    username = name.replace(' ', '.') + "." + (Calendar.getInstance().get(Calendar.YEAR));
                    if(userDAO.retrieveUserByUsername(username) < 1){
                        i = 0;
                    }
                }else{
                    username = name.replace(' ', '.') + temp + "." + (Calendar.getInstance().get(Calendar.YEAR));
                    temp++;
                    if(userDAO.retrieveUserByUsername(username) < 1){
                        i = 0;
                    }
                }
            }
            Users tempUser = new Users(username, password, "tutor", tutorId, branch);
            boolean userStatus = userDAO.addUser(tempUser);
            status = userStatus;
            if(userStatus){
                String href = request.getHeader("origin") + request.getContextPath() + "/Login.jsp";
                String subject = "Stepping Stones Tuition Center Tutor's Account Creation";
                String text = "Your account has been created.\n\nBelow is the username and password to access your account: \nUsername: " + username
                        + "\nPassword: " + password + "\n\nYou can Login via " + href;
                if (email != null && !email.equals("")) {
                    //for local
                    //SendMail.sendingEmail(email, subject, text);
                    //for deploy
                    SendMail.sendingEmailUsingSendGrid(email, subject, text);
                }
            }
            
        }
        request.setAttribute("creation_status", "" + status);
        RequestDispatcher dispatcher = request.getRequestDispatcher("DisplayTutors.jsp");
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
