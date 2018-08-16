/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Tutor;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.GeneratePassword;
import model.SendMail;
import model.TutorDAO;

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
        
        String nric = request.getParameter("tutorNric");
        String name = request.getParameter("tutorName").trim().toLowerCase();
        int phone = 0;
        if(request.getParameter("phone") != null && request.getParameter("phone") != ""){
            phone =  Integer.parseInt(request.getParameter("phone"));
        }
        String address = request.getParameter("address");
        String image_url = request.getParameter("tutorImage");
        String birth_date = request.getParameter("birthDate");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email"); 
        String password = GeneratePassword.random(16);
        System.out.println(password);
        int branch = 0;

        if(request.getParameter("branch") != null && request.getParameter("branch") != ""){
            branch = Integer.parseInt(request.getParameter("branch"));
        }

        TutorDAO tutordao = new TutorDAO();
        Tutor existingTutor = tutordao.retrieveSpecificTutor(name);
        if(existingTutor != null){
            request.setAttribute("existingTutor", existingTutor.getName());
            RequestDispatcher dispatcher = request.getRequestDispatcher("CreateTutor.jsp");
            dispatcher.forward(request, response);
        }else{
            Tutor tempTutor = new Tutor(nric,name,phone,address,image_url,birth_date,gender,email,password,branch);
            boolean status = tutordao.addTutor(tempTutor);
            if(status){
                String href = request.getHeader("origin")+request.getContextPath()+"/Login.jsp";
                String subject = "Stepping Stones Tuition Center Tutor's Account Creation";
                String text = "Your account has been created.\n\nBelow is the username and password to access your account: \nUsername: " + name
                        + "\nPassword: " + password + "\n\nYou can Login via "+href; 
                if(email != null && !email.equals("")){
                    SendMail.sendingEmail(email, subject, text);
                }    
            }
            request.setAttribute("creation_status",""+status);
            RequestDispatcher dispatcher = request.getRequestDispatcher("DisplayTutors.jsp");
            dispatcher.forward(request, response);
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
