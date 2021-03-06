/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Tutor;
import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.GeneratePassword;
import model.SendMail;
import model.TutorDAO;
import model.UsersDAO;

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
            //String images[] = request.getParameterValues("con_images[]");
            String birth_dates[] = request.getParameterValues("con_birthdates[]");
            String genders[] = request.getParameterValues("con_genders[]");
            String emails[] = request.getParameterValues("con_emails[]");
            String qualification[] = request.getParameterValues("con_qualification[]");
            int branch_id = 0; 
            if(request.getParameter("branch") != null && request.getParameter("branch") != ""){
                branch_id = Integer.parseInt(request.getParameter("branch"));
            }

  
            ArrayList<String> tutorLists = new ArrayList();
            ArrayList<String> tutorEmailLists = new ArrayList();
            HashMap<String, String> emailList = new HashMap<>();
            for(int i = 0; i < tutorNames.length; i++){
                if("".equals(tutorNames[i].trim()) || "NIL".equalsIgnoreCase(tutorNames[i].trim())) continue;
                
                if("".equals(emails[i].trim()) || "NIL".equalsIgnoreCase(emails[i].trim())) continue;
                
                if("".equals(phones[i].trim()) || "NIL".equalsIgnoreCase(phones[i].trim())) continue;
                
                if("NIL".equalsIgnoreCase(tutorNrics[i])){
                    tutorNrics[i] = "";
                }
                
                if("NIL".equalsIgnoreCase(addresses[i])){
                    addresses[i] = "";
                }
                
                if("NIL".equalsIgnoreCase(birth_dates[i])){
                    birth_dates[i] = "";
                }
                
                if("NIL".equalsIgnoreCase(genders[i])){
                    genders[i] = "";
                }
                
                
                if("NIL".equalsIgnoreCase(qualification[i])){
                    qualification[i] = "";
                }
                
                int phone = 0;
                if(!"".equals(phones[i])){
                    phone = Integer.parseInt(phones[i]);
                }
                
//                Tutor tempTutor = new Tutor(tutorNrics[i],tutorNames[i],phone,addresses[i],images[i],birth_dates[i],genders[i],emails[i],passwords[i],branch_id);

                tutorLists.add("('"+tutorNrics[i]+"','"+tutorNames[i].trim()+"',"+phone+",'"+addresses[i]+ "','"
                       +birth_dates[i]+"','"+genders[i]+"','"+emails[i]+"','"+ qualification[i] + "','"+branch_id+"')");

                tutorEmailLists.add(emails[i].trim());
                
            }
            
            ArrayList<Tutor>existingUsers = new ArrayList<>();
            if(tutorLists.size() > 0){
                TutorDAO tutorDao = new TutorDAO();
                ArrayList<Object> insertTutor = tutorDao.uploadTutor(tutorLists, tutorEmailLists);
                existingUsers = (ArrayList<Tutor>)insertTutor.get(0);
                ArrayList<Tutor> insertedTutor = (ArrayList<Tutor>) insertTutor.get(1);
                
                HttpSession session = request.getSession();
                session.setAttribute("existingUserLists",existingUsers);
                
                UsersDAO userDAO = new UsersDAO();
                for(Tutor tutor: insertedTutor){
                    String password = GeneratePassword.random(16);
                    String tutorName = tutor.getName();
                    String username = tutorName.replace(' ', '.');
                    int i = -1;
                    int temp = 0;
                    while(i<0){
                        if(temp == 0){
                            temp++;
                            username = tutorName.replace(' ', '.') + "." + (Calendar.getInstance().get(Calendar.YEAR));
                            if(userDAO.retrieveUserByUsername(username) < 1){
                                i = 0;
                            }
                        }else{
                            username = tutorName.replace(' ', '.') + temp + "." + (Calendar.getInstance().get(Calendar.YEAR));
                            temp++;
                            if(userDAO.retrieveUserByUsername(username) < 1){
                                i = 0;
                            }
                        }
                    }
                    Users tempUser = new Users(username, password, "tutor", tutor.getTutorId(), branch_id);
                    boolean userStatus = userDAO.addUser(tempUser);
                    if(userStatus){
                        String value = tutor.getEmail() + "&" + password;
                        emailList.put(username, value);
                    }
                }
            }
            
            Set<String> usernames = emailList.keySet();
            for(String username: usernames){
                String[] value = emailList.get(username).split("&");
                String email = value[0];
                String password = value[1];
                String subject = "Stepping Stones Tuition Center Tutor's Account Creation";
                if(email != null && !email.equals("")){
                    //for local
                    //SendMail.sendingEmail(email, subject, text);
                    //for deploy
                    SendMail.sendingEmailUsingSendGrid(email, subject, username, password);
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
