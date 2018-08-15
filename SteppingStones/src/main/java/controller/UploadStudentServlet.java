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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.LevelDAO;
import model.ParentChildRelDAO;
import model.ParentDAO;
import model.StudentDAO;

@WebServlet(name = "UploadStudentServlet", urlPatterns = {"/UploadStudentServlet"})
public class UploadStudentServlet extends HttpServlet {

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
            String studNrics[] = request.getParameterValues("con_nric[]");
            String studNames[] = request.getParameterValues("con_name[]");
            String phones[] = request.getParameterValues("con_phones[]");
            String addresses[] = request.getParameterValues("con_addresses[]");
            String birth_dates[] = request.getParameterValues("con_birthdates[]");
            String genders[] = request.getParameterValues("con_genders[]");
            String emails[] = request.getParameterValues("con_emails[]");
            String acad_level[] = request.getParameterValues("con_acadlevel[]");
            String passwords[] = request.getParameterValues("con_pwd[]");
            
            
            String parentNames[] = request.getParameterValues("con_parentName[]");
            String nationality[] = request.getParameterValues("con_nationality[]");
            String company[] = request.getParameterValues("con_company[]");
            String designation[] = request.getParameterValues("con_designation[]");
            String mobiles[] = request.getParameterValues("con_mobile[]");
            String parentEmail[] = request.getParameterValues("con_parentEmail[]");
            
            int branch_id = 0;
            if (request.getParameter("branch") != null && request.getParameter("branch") != "") {
                branch_id = Integer.parseInt(request.getParameter("branch"));
            }

            ArrayList<String> studentLists = new ArrayList();
            ArrayList<String> studentNameLists = new ArrayList();
            for (int i = 0; i < studNames.length; i++) {
                if ("".equals(studNames[i].trim())) {
                    continue;
                }

                int phone = 0;
                if (!"".equals(phones[i])) {
                    phone = Integer.parseInt(phones[i]);
                }

                studentLists.add("('" + studNrics[i] + "','" + studNames[i].trim() + "'," + phone + ",'" + addresses[i] + "','" + birth_dates[i] + 
                        "','" + genders[i] + "','" + emails[i] + "',MD5('" + passwords[i] + "'),'" + LevelDAO.retrieveLevelID(acad_level[i]) + "'," + branch_id + ")");

                studentNameLists.add(studNames[i].trim());
            }

            ArrayList<String> parentLists = new ArrayList();
            ArrayList<String> parentNameLists = new ArrayList();
            for (int i = 0; i < parentNames.length; i++) {
                if ("".equals(parentNames[i].trim())) {
                    continue;
                }

                int mobile = 0;
                if (!"".equals(mobiles[i])) {
                    mobile = Integer.parseInt(mobiles[i]);
                }

                parentLists.add("('" + parentNames[i].trim() + "','" + nationality[i] + "','" + company[i] + "','" + designation[i] + "'," + mobile + 
                        ",'" + parentEmail[i] + "',MD5(" + mobile + ")," + branch_id + ")");

                parentNameLists.add(parentNames[i].trim());
            }
            
            HashMap<String, String> studParRel = new HashMap<>();
            
            for (int i = 0; i < studentNameLists.size() ; i++){
                studParRel.put(studentNameLists.get(i),parentNameLists.get(i));
            }
            
            ArrayList<String> existingUsers = new ArrayList<>();
            ArrayList<String> existingParents = new ArrayList<>();
            if (studentLists.size() > 0) {
                StudentDAO studentDAO = new StudentDAO();
                existingUsers = studentDAO.uploadStudent(studentLists, studentNameLists);
                ParentDAO parentDAO = new ParentDAO();
                existingParents = parentDAO.uploadParent(parentLists, parentNameLists);
                
                for (String dupStudName: existingUsers){
                    if (studParRel.containsKey(dupStudName)){
                        studParRel.remove(dupStudName);
                    }
                }
                
                ParentChildRelDAO pcrDAO = new ParentChildRelDAO();
                for (String key: studParRel.keySet()){
                    String value = studParRel.get(key);
                    pcrDAO.insertParentChildRel(key, value, branch_id);
                }
                HttpSession session = request.getSession();
                session.setAttribute("existingUserLists", existingUsers);
                session.setAttribute("existingParentLists", existingParents);
            }

            response.sendRedirect("DisplayStudents.jsp");
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
