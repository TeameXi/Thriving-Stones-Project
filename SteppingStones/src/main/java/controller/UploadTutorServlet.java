/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.SendMail;
import model.TutorDAO;

/**
 *
 * @author MOH MOH SAN
 */
@WebServlet(name = "UploadTutorServlet", urlPatterns = {"/UploadTutorServlet"})
public class UploadTutorServlet extends HttpServlet {

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
       
        try (PrintWriter out = response.getWriter()) {
            String tutorNrics[] = request.getParameterValues("con_nric[]");
            String tutorNames[] = request.getParameterValues("con_username[]");
            String phones[] = request.getParameterValues("con_phones[]");
            String addresses[] = request.getParameterValues("con_addresses[]");
            String images[] = request.getParameterValues("con_images[]");
            String birth_dates[] = request.getParameterValues("con_birthdates[]");
            String genders[] = request.getParameterValues("con_genders[]");
            String emails[] = request.getParameterValues("con_emails[]");
            String passwords[] = request.getParameterValues("con_pwd[]");
            int branch_id = 0; 
            if(request.getParameter("branch") != null && request.getParameter("branch") != ""){
                branch_id = Integer.parseInt(request.getParameter("branch"));
            }

  
            ArrayList<String> tutorLists = new ArrayList();
            ArrayList<String> tutorNameLists = new ArrayList();
            HashMap<String, String> emailList = new HashMap<>();
            for(int i = 0; i < tutorNames.length; i++){
                if("".equals(tutorNames[i].trim())) continue;
              
                int phone = 0;
                if(!"".equals(phones[i])){
                    phone = Integer.parseInt(phones[i]);
                }
                
//                Tutor tempTutor = new Tutor(tutorNrics[i],tutorNames[i],phone,addresses[i],images[i],birth_dates[i],genders[i],emails[i],passwords[i],branch_id);

                tutorLists.add("('"+tutorNrics[i]+"','"+tutorNames[i].trim()+"',"+phone+",'"+addresses[i]+"','"+images[i]+"','"
                       +birth_dates[i]+"','"+genders[i]+"','"+emails[i]+"',MD5('"+passwords[i]+"'),"+branch_id+")");

                tutorNameLists.add(tutorNames[i].trim());
                String value = emails[i] + "&" + passwords[i];
                emailList.put(tutorNames[i].trim(), value);
            }
            
            ArrayList<String>existingUsers = new ArrayList<>();
            if(tutorLists.size() > 0){
                TutorDAO tutorDao = new TutorDAO();
                existingUsers = tutorDao.uploadTutor(tutorLists, tutorNameLists);
                HttpSession session = request.getSession();
                session.setAttribute("existingUserLists",existingUsers);
            }
            for(String existingUser: existingUsers){
                emailList.remove(existingUser);
            }
            
            Set<String> usernames = emailList.keySet();
            for(String username: usernames){
                String[] value = emailList.get(username).split("&");
                String email = value[0];
                String password = value[1];
                String subject = "Stepping Stones Tuition Center Tutor's Account Creation";
                String text = "Your account has been created.\n\nBelow is the username and password to access your account: \nUsername: " + username
                        + "\nPassword: " + password + "\n\nYou can update your password via https://www.google.com/ or \n Login via https://www.google.com/";
                if(email != null && !email.equals("")){
                    SendMail.sendingEmail(email, subject, text);
                } 
            }
                    
            response.sendRedirect("DisplayTutors.jsp");
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
