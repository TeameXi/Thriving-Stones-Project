/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Tutor;
import entity.Class;
import entity.Lesson;
import entity.TutorPay;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.ExpenseDAO;
import model.LessonDAO;
import model.TutorDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Shawn
 */
@WebServlet(name = "TutorPaymentServlet", urlPatterns = {"/TutorPaymentServlet"})
public class TutorPaymentServlet extends HttpServlet {

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
            String action = request.getParameter("action");

            if (action.equals("retrieveClasses")) {
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                int branchID = Integer.parseInt(request.getParameter("branchID"));
                
                // JSON OUTPUT
                JSONObject resultObj = new JSONObject();
                
                // FOR NORMAL CLASS PAY MONTHLY
                ArrayList<Class> classes = ClassDAO.listAllClassesBelongToTutors(tutorID, branchID);
                System.out.println(classes.size());
                JSONArray tutorPayListJSON = new JSONArray();
                for (Class c : classes) {
                    double tutorRate = c.getTutorRate();
                    int classId = c.getClassID();
                    String className = c.getClassName();
                    String levelName = c.getLevel();
                    String subjectName = c.getSubject();
                    double duration =  ClassDAO.getClassTime(classId);

                    ArrayList<TutorPay> tutorPayListByMonths = LessonDAO.totalLessonTutorAttendForClass(tutorID, classId, tutorRate, duration,c.getPayType());
                    System.out.println(tutorPayListByMonths);
                    
                    // FOR MONTHLY PAY
                    JSONObject classObj = new JSONObject();
                    classObj.put("id", classId);
                    classObj.put("className",className);
                    classObj.put("levelName",levelName);
                    classObj.put("subjectName",subjectName);
                    
                    JSONArray lessonMontlyList = new JSONArray();
                    for(TutorPay payForMonthlyLesson:tutorPayListByMonths){
                        JSONObject lessonObj = new JSONObject();
                        String lessonName = payForMonthlyLesson.getLessonName();
                        double lessonMonthlySalary = payForMonthlyLesson.getMonthlySalary();
                        int month = payForMonthlyLesson.getMonth();
                        int year = payForMonthlyLesson.getYear();
                        lessonObj.put("lessonName", lessonName);
                        lessonObj.put("monthlySalary",lessonMonthlySalary);
                        lessonObj.put("month",month);
                        lessonObj.put("year",year);
                        lessonObj.put("btnStatus",payForMonthlyLesson.getPaidStatus());
                        lessonObj.put("totalLesson", payForMonthlyLesson.getTotalLesson());
                        lessonMontlyList.put(lessonObj);
                    }
                    classObj.put("lessonMontlySalary",lessonMontlyList);
                    tutorPayListJSON.put(classObj);
                }
                resultObj.put("monthlyPay", tutorPayListJSON);
                
                //REPLACEMENT 
                JSONArray replacementClassListJSON = new JSONArray();
                ArrayList<TutorPay> replacementPayListByClass = ClassDAO.totalReplacementClasses(tutorID, branchID);
                for(TutorPay replacementPayForEachClass: replacementPayListByClass){
                    JSONObject replacementClassObj = new JSONObject();
                    int replacementClassId = replacementPayForEachClass.getClassId();
                    int replacementTutorId = replacementPayForEachClass.getTutorId();
                    String replacementClassName = replacementPayForEachClass.getClassName();
                    int totalReplacementLesson = replacementPayForEachClass.getTotalLesson();
                    double replacementPayPerClass = replacementPayForEachClass.getMonthlySalary();
                    String subject = replacementPayForEachClass.getSubjectName();
                    String levels = replacementPayForEachClass.getLevelIds();

                    replacementClassObj.put("replacementClassId", replacementClassId);
                    replacementClassObj.put("replacementTutorId",replacementTutorId);
                    replacementClassObj.put("replacementClassName", replacementClassName);
                    replacementClassObj.put("totalReplacementLesson",totalReplacementLesson);
                    replacementClassObj.put("replacementPayPerClass",replacementPayPerClass);
                    replacementClassObj.put("subject",subject);
                    replacementClassObj.put("levels",levels);
                    replacementClassListJSON.put(replacementClassObj);
                }
                
                resultObj.put("replacementPay", replacementClassListJSON);
                out.println(resultObj);

            }else if (action.equals("pay")) {
                //action
                String [] ids = request.getParameter("ids").split("_");
                int tutorID = Integer.parseInt(ids[0]);
                int classID = Integer.parseInt(ids[1]);
                int month = Integer.parseInt(ids[2]);
                int year = Integer.parseInt(ids[3]);
                
            
                // For Expense
                String tutorName = request.getParameter("tutorName");
                String levelName = request.getParameter("levelName");
                String subjectName = request.getParameter("subjectName");
                double amount = 0;
                String amountStr = request.getParameter("monthlyAmount");
                if(!amountStr.equals("")){
                    amount = Double.parseDouble(amountStr);
                }

                boolean status = TutorDAO.updateTutorPayment(tutorID,classID,month,year);
                if (status){
                    status = ExpenseDAO.insertExpense(tutorID,tutorName,subjectName,levelName,amount);
                }

                if(status){
                    out.println(1);
                }else{
                    out.println(0);
                }

            }else if(action.equals("replacementPay")){
                String [] ids = request.getParameter("ids").split("_");
                int tutorID = Integer.parseInt(ids[0]);
                int classID = Integer.parseInt(ids[1]);
                
                // For Expense
                String tutorName = request.getParameter("tutorName");
                String levelName = request.getParameter("levelName");
                String subjectName = request.getParameter("subjectName");
                double amount = 0;
                String amountStr = request.getParameter("replacementAmount");
                if(!amountStr.equals("")){
                    amount = Double.parseDouble(amountStr);
                }
                
                boolean status = TutorDAO.updateTutorPaymentForReplacement(tutorID,classID);
                if (status){
                    status = ExpenseDAO.insertExpense(tutorID,tutorName,subjectName,levelName,amount);
                }
                
                if(status){
                    out.println(1);
                }else{
                    out.println(0);
                }
                
                
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

}
