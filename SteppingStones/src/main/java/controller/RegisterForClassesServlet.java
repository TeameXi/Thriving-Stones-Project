/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.StudentDAO;
import entity.Class;
import entity.Student;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import model.LessonDAO;
import model.LevelDAO;
import model.PaymentDAO;
import model.StudentClassDAO;

/**
 *
 * @author DEYU
 */
@WebServlet(name = "RegisterForClassesServlet", urlPatterns = {"/RegisterForClassesServlet"})
public class RegisterForClassesServlet extends HttpServlet {

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
        int branchID = Integer.parseInt(request.getParameter("branch_id"));
        if(request.getParameter("search") != null){
            String student = (String) request.getParameter("student");
            String[] parts = student.split("-");
            String studentName = "";
            String studentEmail = "";
            int phone = 0;
            
            if(parts.length == 2){
                studentName = parts[0].trim();
                if(parts[1].contains("@")){
                    studentEmail = parts[1].trim();
                }else{
                    phone = Integer.parseInt(parts[1].trim());
                }
            }
//            System.out.println(phone + "& " + studentEmail);
            int studentID = 0;
            if(studentEmail.isEmpty()){
                studentID = StudentDAO.retrieveStudentIDWithPhone(studentName, phone);
            }else{
                studentID = StudentDAO.retrieveStudentIDWithEmail(studentName, studentEmail);
            }
//            System.out.println(studentID);
            int levelID = StudentDAO.retrieveStudentLevelbyID(studentID,branchID);
            if(levelID == 0){
                request.setAttribute("errorMsg", studentName + " Not Exists in Database, Please Create Student First.");           
            }else{
                
                ArrayList<Class> cls = ClassDAO.getClassesToEnrolled(levelID, studentID, branchID);
                ArrayList<Class> enrolledCls = ClassDAO.getStudentEnrolledClass(studentID);
                request.setAttribute("level", LevelDAO.retrieveLevel(levelID));    
                request.setAttribute("studentName", studentName);
                request.setAttribute("student_id", studentID);
                request.setAttribute("classes", cls);
                request.setAttribute("enrolledClasses", enrolledCls);
            }
            RequestDispatcher view = request.getRequestDispatcher("RegisterForClasses.jsp");
            view.forward(request, response);
        }
        
        if(request.getParameter("select") != null){
            String[] classValues = request.getParameterValues("classValue");
            int studentID = 0;
            String studentIDStr = request.getParameter("studentID");
            if(studentIDStr != null && !studentIDStr.isEmpty()){
                studentID = Integer.parseInt(studentIDStr);
            }
            //System.out.print(studentID);

            if(classValues != null){
                for(String classValue: classValues){
                    int classID = Integer.parseInt(classValue);
                    String joinDate = request.getParameter(classValue);
                    if(joinDate == null || joinDate.isEmpty()){
                        joinDate = LessonDAO.getNearestLessonDate(classID);
                    }
                    Class cls = ClassDAO.getClassByID(classID);
                    double monthlyFees = cls.getMthlyFees();
                    boolean status = StudentClassDAO.saveStudentToRegisterClass(classID, studentID, monthlyFees, monthlyFees, joinDate); 
                    HashMap<String, Integer> reminders = PaymentDAO.getReminders(classID, joinDate);
                    Set<String> keys = reminders.keySet();
                    boolean paymentStauts = false;
                    if(!reminders.isEmpty()){
                        for(String reminderDate: keys){
                            int noOfLessons = reminders.get(reminderDate);
                            paymentStauts = PaymentDAO.insertPaymentReminderWithAmount(classID, studentID, reminderDate, noOfLessons, monthlyFees);
                        }
                    }else{
                        paymentStauts = true;
                    }

                    boolean updateOutstandingFees = false;
                    if(status){
                        Student stu = StudentDAO.retrieveStudentbyID(studentID,branchID);
                        double totalOutstandingAmt = stu.getOutstandingAmt() + monthlyFees;
                        updateOutstandingFees = StudentDAO.updateStudentTotalOutstandingFees(studentID, totalOutstandingAmt);
                        System.out.println("After Deposit Add" + totalOutstandingAmt);
                    }
                    
                    if(!status || !paymentStauts || !updateOutstandingFees){
                        request.setAttribute("errorMsg", "Something Went Wrong with Registration.");
                        RequestDispatcher view = request.getRequestDispatcher("RegisterForClasses.jsp");
                        view.forward(request, response);   
                    }
                }
                response.sendRedirect("PaymentPage.jsp?studentID="+studentID);
                return;
            }
            request.setAttribute("errorMsg", "Please select at least 1 class to register");
            RequestDispatcher view = request.getRequestDispatcher("RegisterForClasses.jsp");
            view.forward(request, response);
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