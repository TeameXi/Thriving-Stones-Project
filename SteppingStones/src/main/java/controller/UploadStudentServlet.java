/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Parent;
import entity.Student;
import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.GeneratePassword;
import model.LevelDAO;
import model.ParentChildRelDAO;
import model.ParentDAO;
import model.SendMail;
import model.SendSMS;
import model.StudentDAO;
import model.UsersDAO;

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
            String school[] = request.getParameterValues("con_school[]");
            String stream[] = request.getParameterValues("con_streams[]");
            String parentNames[] = request.getParameterValues("con_parentName[]");
            String nationality[] = request.getParameterValues("con_nationality[]");
            String company[] = request.getParameterValues("con_company[]");
            String designation[] = request.getParameterValues("con_designation[]");
            String mobiles[] = request.getParameterValues("con_mobile[]");
            String parentEmail[] = request.getParameterValues("con_parentEmail[]");
            String relationship[] = request.getParameterValues("con_relationship[]");
            
            String registrationFee[] = request.getParameterValues("con_registrationFee[]");
            String outstandingRegistrationFee[] = request.getParameterValues("con_outstandingRegistrationFee[]");
            String studentStatus[] = request.getParameterValues("con_studentStatus[]");
            
            int branch_id = 0;
            if (request.getParameter("branch") != null && request.getParameter("branch") != "") {
                branch_id = Integer.parseInt(request.getParameter("branch"));
            }
            
            String href =  request.getHeader("origin")+request.getContextPath()+"/Login.jsp";
            
            StudentDAO studentDAO = new StudentDAO();
            
            ArrayList<Student> existingUsers = new ArrayList<>();
            
            List<HashMap<String, String>> existingStudentList = StudentDAO.retrieveAllStudent();
            HashMap<String,String> phoneName = existingStudentList.get(0);
            HashMap<String,String> emailName = existingStudentList.get(1);
            
            ArrayList<String> studentLists = new ArrayList();
            ArrayList<String> studentNameLists = new ArrayList();
            ArrayList<String> studentEmailLists = new ArrayList();
            ArrayList<String> studentRelationshipLists = new ArrayList();
            for (int i = 0; i < studNames.length; i++) {
                boolean checkName = "".equals(studNames[i].trim()) || "NIL".equalsIgnoreCase(studNames[i].trim());
                boolean checkEmail = "".equals(studNames[i].trim()) || "NIL".equalsIgnoreCase(emails[i].trim());
                boolean checkPhone = "".equals(phones[i]) || "NIL".equalsIgnoreCase(phones[i].trim());
                if (checkName) {
                    continue;
                }
                if((checkName && checkEmail)){
                    if((checkName && checkPhone)){
                        continue;
                    }
                }
                /*if(!checkEmail){
                    continue;
                }*/

                if(!checkPhone){
                    String name = emailName.get(emails[i]);
                    if(name != null){
                        if(name.equals(studNames[i].trim())){
                            existingUsers.add(new Student(0,name,0,""));
                            continue;
                        }
                    }
                }
                
                int phone = 0;
                if (!checkPhone) {
                    phone = Integer.parseInt(phones[i]);
                    String name = phoneName.get(phones[i]);
                    if(name != null){
                        if(name.equals(studNames[i].trim())){
                            existingUsers.add(new Student(0,name,0,""));
                            continue;
                        }
                    }
                }
                
                if("NIL".equalsIgnoreCase(studNrics[i])){
                    studNrics[i] = "";
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
                
                if("NIL".equalsIgnoreCase(emails[i])){
                    emails[i] = "";
                }
                
                if("".equals(registrationFee[i]) || "NIL".equalsIgnoreCase(registrationFee[i])){
                    registrationFee[i] = "0";
                }
                
                if("".equals(outstandingRegistrationFee[i]) || "NIL".equalsIgnoreCase(outstandingRegistrationFee[i])){
                    outstandingRegistrationFee[i] = "0";
                }
                
                if("NIL".equalsIgnoreCase(school[i])){
                    school[i] = "0";
                }
                
                int level = LevelDAO.retrieveLevelID(changeLevelString(acad_level[i]));
                if( level == 0){
                    continue;
                }
                
                studentLists.add("('" + studNrics[i] + "','" + studNames[i].trim() + "'," + phone + ",'" + addresses[i] + "','" + birth_dates[i] + 
                        "','" + genders[i] + "','" + emails[i] + "','" + registrationFee[i] +  "','" + 
                        outstandingRegistrationFee[i] +  "','" + level + "'," + branch_id + ",'" + school[i] + "','" + stream[i] +"')");
                
                
                studentNameLists.add(studNames[i].trim());
                studentRelationshipLists.add(relationship[i]);
                if(phone == 0){
                    studentEmailLists.add(emails[i]);
                }else{
                    studentEmailLists.add(phones[i]);
                }
            }

            ArrayList<String> parentLists = new ArrayList();
            ArrayList<String> parentNameLists = new ArrayList();
            ArrayList<Integer> parentMobileList = new ArrayList();
            for (int i = 0; i < parentNames.length; i++) {
                if ("".equals(parentNames[i].trim()) || "NIL".equalsIgnoreCase(parentNames[i].trim())) {
                    continue;
                }
                int mobile = 0;
                if (!"".equals(mobiles[i]) || "NIL".equalsIgnoreCase(mobiles[i].trim())) {
                    mobile = Integer.parseInt(mobiles[i]);
                }else{
                    continue;
                }
                if("NIL".equalsIgnoreCase(nationality[i].trim())){
                    nationality[i] = "";
                }
                if("NIL".equalsIgnoreCase(company[i].trim())){
                    company[i] = "";
                }
                if("NIL".equalsIgnoreCase(designation[i].trim())){
                    designation[i] = "";
                }
                parentLists.add("('" + parentNames[i].trim() + "','" + nationality[i] + "','" + company[i] + "','" + designation[i] + "'," + mobile + 
                        ",'" + parentEmail[i] + "'," + branch_id + ")");

                parentNameLists.add(parentNames[i].trim());
                parentMobileList.add(mobile);
            }
            
            HashMap<List<String>, Integer> studParRel = new HashMap<>();
            
            
            for (int i = 0; i < studentNameLists.size() ; i++){
                if(!studentNameLists.get(i).equals("")){
                    List<String> studentDetails = new ArrayList<>();
                    studentDetails.add(studentEmailLists.get(i));
                    studentDetails.add(studentNameLists.get(i));
                    studentDetails.add(studentRelationshipLists.get(i));
                    studParRel.put(studentDetails,parentMobileList.get(i));
                }
            }
            
            //Map<Integer,Student> nameWithId = new HashMap<Integer,Student>();
            ArrayList<Student> insertedStudent = new ArrayList<>();
            if (studentLists.size() > 0) {
                //ArrayList<Object> studentReturnList = studentDAO.uploadStudent(studentLists);
                //existingUsers.addAll((ArrayList<Student>)studentReturnList.get(0));
                insertedStudent = studentDAO.uploadStudent(studentLists);
                UsersDAO userDAO = new UsersDAO();
                for(Student key : insertedStudent) {
                    String password = GeneratePassword.random(16);
                    String studentName = key.getName();
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
                    Users tempUser = new Users(username, password, "student", key.getStudentID(), branch_id);
                    boolean userStatus = userDAO.addUser(tempUser);
                    if(userStatus){
                        String subject = "Stepping Stones Tuition Center Student's Account Creation";
                        String text = "Thanks for choosing us. Your account has been created.\n\nBelow is the username and password to access your account: \nUsername: " + username 
                                + "\nPassword: " + password + "\n\nYou can Login via "+href; 
                        if(key.getEmail() != null && !key.getEmail().equals("")){
                            //SendMail.sendingEmail(key.getEmail(), subject, text);
                        }else if(key.getPhone() != 0){
                            //String phoneNum = "+65" + key.getPhone();
                            //SendSMS.sendingSMS(phoneNum, text);
                        }
                    }
                }
                
                ParentDAO parentDAO = new ParentDAO();
                ArrayList<Object> returnList = parentDAO.uploadParent(parentLists, parentMobileList);
                ArrayList<String> existingParents = (ArrayList<String>) returnList.get(0);
                ArrayList<Parent> insertedParents = (ArrayList<Parent>) returnList.get(1);
                
                for(Parent p : insertedParents){
                    String password = GeneratePassword.random(16);
                    String username = p.getName().replace(' ', '.');
                    int i = -1;
                    int temp = 0;
                    while(i<0){
                        if(temp == 0){
                            temp++;
                            username = p.getName().replace(' ', '.') + "." + (Calendar.getInstance().get(Calendar.YEAR));
                            if(userDAO.retrieveUserByUsername(username) < 1){
                                i = 0;
                            }
                        }else{
                            username = p.getName().replace(' ', '.') + temp + "." + (Calendar.getInstance().get(Calendar.YEAR));
                            temp++;
                            if(userDAO.retrieveUserByUsername(username) < 1){
                                i = 0;
                            }
                        }
                    }

                    Users tempUser = new Users(username, password, "parent", p.getParentId(), p.getBranch_id());
                    boolean userStatus = userDAO.addUser(tempUser);
                    if(userStatus){
                        String subject = "Stepping Stones Tuition Center Parent's Account Creation";
                        String text = "Thanks for choosing us. Your account has been created.\n\nBelow is the username and password to access your account: \nUsername: " + username 
                                + "\nPassword: " + password + "\n\nYou can Login via "+href; 
                        if(parentEmail != null && !parentEmail.equals("")){
                            //SendMail.sendingEmail(p.getEmail(), subject, text);
                        }else if(p.getPhone() != 0){
                            //String phoneNum = "+65" + p.getPhone();
                            //SendSMS.sendingSMS(phoneNum, text);
                        }
                    }
                }
                
                /*for (Student dupStudName: existingUsers){
                    if (studParRel.containsKey(dupStudName.getName())){
                        studParRel.remove(dupStudName.getName());
                    }
                }*/
                
                ParentChildRelDAO pcrDAO = new ParentChildRelDAO();
                for (List<String> key: studParRel.keySet()){
                    Integer value = studParRel.get(key);
                    int studentID = StudentDAO.retrieveStudentID(key);
                    if(studentID != 0){
                        pcrDAO.insertParentChildRelForUpload(value, studentID, branch_id, key.get(2));
                    }
                }
                HttpSession session = request.getSession();
                session.setAttribute("existingUserLists", existingUsers);
                session.setAttribute("existingParentLists", existingParents);
                session.setAttribute("insertedStudent", insertedStudent);
            }
            if(insertedStudent.size() > 0){
                //response.sendRedirect("ClassRegistrationForUploadStudent.jsp");
                response.sendRedirect("DisplayStudents.jsp");
            }else{
                response.sendRedirect("DisplayStudents.jsp");
            }
            
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

    private String changeLevelString(String level){
        if(level.charAt(0) == 'P' || level.charAt(0) == 'p'){
            return "Primary " + level.charAt(level.length() -1);
        }else if(level.charAt(0) == 'S' || level.charAt(0) == 's'){
            return "Secondary " + level.charAt(level.length()-1);
        }
        
        return "";
    }
}
