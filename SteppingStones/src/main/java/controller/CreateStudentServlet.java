/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Student;
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
        
        if(request.getParameter("branch") == null){
            response.sendRedirect("CreateStudent.jsp");
            return;
        }
        String nric = request.getParameter("nric");
        String gender = request.getParameter("gender");
        String birthDate = request.getParameter("bday");
        String address = request.getParameter("address");
        String studentName = request.getParameter("studentName");
        String lvl = request.getParameter("lvl");
        String stream = request.getParameter("stream");
        String school = request.getParameter("school");
        String relationship = request.getParameter("relationship");
        
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
            regFees = Double.parseDouble(request.getParameter("regFees"));
        }
        String stuEmail = request.getParameter("studentEmail");
        String stuPassword = GeneratePassword.random(16);
        String parentName = request.getParameter("parentName");
        String parentPassword = GeneratePassword.random(16);
        int parentPhone = Integer.parseInt(request.getParameter("parentPhone"));
        String parentEmail = request.getParameter("parentEmail"); 
        String nationality = request.getParameter("nationality");
        String company = request.getParameter("company");
        String designation = request.getParameter("designation");         
        
        String href =  request.getHeader("origin")+request.getContextPath()+"/Login.jsp";
        
        int studentID = 0;
        String stu = "";
        if(stuEmail != null && !stuEmail.isEmpty()){
            stu = studentName + "-" + stuEmail; 
            studentID = StudentDAO.retrieveStudentIDWithEmail(studentName, stuEmail);
        }else{
            stu = studentName + "-" + phone;
            studentID = StudentDAO.retrieveStudentIDWithPhone(studentName, phone);
        }
        if(stuEmail.equals("")){
            stuEmail = null;
        }
        //System.out.println("StudentID  " + studentID);
        
        int insertStudent = 0;
        if(studentID == 0){
            insertStudent = StudentDAO.insertStudent(studentName, phone,stuEmail, levelID, branchID, regFees, school, stream, nric, gender, birthDate, address);
        }
        //System.out.println("Insert Student " + insertStudent);

        if(insertStudent > 0){
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
            if(phone == 0 && stuEmail == null){
                stuPassword = "password";
            }
            Users tempUser = new Users(username, stuPassword, "student", insertStudent, branchID);
            boolean userStatus = userDAO.addUser(tempUser);
            
            Student student = StudentDAO.retrieveStudentbyID(insertStudent,branchID);
            double outstandingAmt = student.getOutstandingAmt() + regFees;
            boolean updateOutstandingFees = StudentDAO.updateStudentTotalOutstandingFees(insertStudent, outstandingAmt);
            System.out.println("After Reg Fees Add" + outstandingAmt);
            
//            if(userStatus && updateOutstandingFees){
//                String subject = "Stepping Stones Tuition Center Student's Account Creation";
//                String text = "Thanks for choosing us. Your account has been created.\n\nBelow is the username and password to access your account: \nUsername: " + username 
//                        + "\nPassword: " + stuPassword + "\n\nYou can Login via "+href; 
//                if(stuEmail != null && !stuEmail.equals("")){
//                    SendMail.sendingEmail(stuEmail, subject, text);
//                }else if(phone != 0){
//                    String phoneNum = "+65" + phone;
//                    SendSMS.sendingSMS(phoneNum, text);
//                }
//            }

            
        }
        
        int insertParent = 0;
        if(insertStudent > 0){
            insertParent = ParentDAO.insertParent(parentName, parentPhone, parentEmail, branchID, nationality, company, designation);
            //System.out.println("Insert Parent" + insertParent);
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
    //            if(userStatus){
    //                String subject = "Stepping Stones Tuition Center Parent's Account Creation";
    //                String text = "Thanks for choosing us. Your account has been created.\n\nBelow is the username and password to access your account: \nUsername: " + username 
    //                        + "\nPassword: " + parentPassword + "\n\nYou can Login via "+href; 
    //                if(parentEmail != null && !parentEmail.equals("")){
    //                    SendMail.sendingEmail(parentEmail, subject, text);
    //                }else if(parentPhone != 0){
    //                    String phoneNum = "+65" + parentPhone;
    //                    SendSMS.sendingSMS(phoneNum, text);
    //                }
    //            }

            }
        }
        
        //System.out.print("parent Child Rel" + parentPhone + " " + insertStudent );
        
        request.setAttribute("creation_status",""+(insertStudent>0 && insertParent>0));
        if(insertStudent == 0){
            request.setAttribute("existingStudent", studentName);
            RequestDispatcher view = request.getRequestDispatcher("CreateStudent.jsp");
            view.forward(request, response);
        }else{
            ParentChildRelDAO.insertParentChildRel(parentPhone, insertStudent, branchID, relationship);
            response.sendRedirect("RegisterForClasses.jsp?studentName="+stu);
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
