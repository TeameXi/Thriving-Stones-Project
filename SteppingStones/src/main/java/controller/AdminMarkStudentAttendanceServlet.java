/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import entity.Lesson;
import entity.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AttendanceDAO;
import model.ClassDAO;
import model.LessonDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Riana
 */
@WebServlet(name = "AdminMarkStudentAttendanceServlet", urlPatterns = {"/AdminMarkStudentAttendanceServlet"})
public class AdminMarkStudentAttendanceServlet extends HttpServlet {

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
            Gson gson = new Gson();
            String action = request.getParameter("action");

            if (action.equals("retrieve")) {
                //int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                int branchID = Integer.parseInt(request.getParameter("branchID"));
                
                JSONArray array = new JSONArray();
                ArrayList<entity.Class> classes = ClassDAO.listAllClasses(branchID);

                for (entity.Class c : classes) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", c.getClassID());
                    obj.put("class", c.getClassDay() + " " + c.getClassTime());
                    obj.put("level", c.getLevel());
                    obj.put("subject", c.getSubject());
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("retrieveStudents")){
                int classID = Integer.parseInt(request.getParameter("classID"));
                JSONArray array = new JSONArray();
                ClassDAO classes = new ClassDAO();
                ArrayList<Student> students = classes.retrieveStudentsByClass(classID);
                
                AttendanceDAO attendance = new AttendanceDAO();
                
                for(Student s: students){
                    JSONObject obj = new JSONObject();
                    obj.put("id", s.getStudentID());
                    obj.put("name", s.getName());
                    obj.put("phone", s.getPhone());
                    
                    DecimalFormat df = new DecimalFormat("#.##");
                    double attendancePercentage = (attendance.retrieveNumberOfStudentAttendances(s.getStudentID(), classID) / LessonDAO.retrieveAllLessonLists(classID).size()) * 100;
                    obj.put("attendance", df.format(attendancePercentage) + "%");
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("retrieveLessons")){
                int classID = Integer.parseInt(request.getParameter("classID"));
                int studentID = Integer.parseInt(request.getParameter("studentID"));
                
                ArrayList<Lesson> lessons = LessonDAO.retrieveAllLessonLists(classID);
                AttendanceDAO attendance = new AttendanceDAO();
                
                JSONArray array = new JSONArray();
                
                for(int i = 0; i < lessons.size(); i++){
                    Lesson l = lessons.get(i);
                    JSONObject obj = new JSONObject();
                    String date = l.getStartDate();
                    obj.put("id", l.getLessonid());
                    obj.put("date", date.substring(0, date.indexOf(" ")));
                    
                    if(attendance.retrieveStudentAttendances(studentID, l.getLessonid())){
                        obj.put("attended", "Present");
                    }
                    
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("mark")){
                int classID = Integer.parseInt(request.getParameter("classID"));
                int studentID = Integer.parseInt(request.getParameter("studentID"));
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                
                AttendanceDAO attendance = new AttendanceDAO();
                boolean status = attendance.updateStudentAttendance(studentID, lessonID, classID, 0, true);
                String percentage = attendance.retrieveNumberOfStudentAttendances(studentID, classID) + "%";
                
                JSONObject toReturn = new JSONObject().put("data", status);
                toReturn.put("attendance", percentage);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("retrieveModal")){
                int classID = Integer.parseInt(request.getParameter("classID"));
                
                ArrayList<Lesson> lessons = LessonDAO.retrieveAllLessonLists(classID);
                AttendanceDAO attendance = new AttendanceDAO();
                
                JSONArray array = new JSONArray();
                
                for(int i = 0; i < lessons.size(); i++){
                    Lesson l = lessons.get(i);
                    JSONObject obj = new JSONObject();
                    String date = l.getStartDate();
                    obj.put("id", l.getLessonid());
                    obj.put("date", date.substring(0, date.indexOf(" ")));
                    obj.put("attendance", attendance.retrieveNumberOfStudentsAttended(l.getLessonid()));
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("retrieveLessonDetails")){
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                System.out.println("hereeeee");
                Lesson l = LessonDAO.getLessonByID(lessonID);
                ArrayList<Student> students = new ClassDAO().retrieveStudentsByClass(l.getClassid());
                JSONArray array = new JSONArray();
                AttendanceDAO attendance = new AttendanceDAO();
                
                for(Student s: students){
                    JSONObject obj = new JSONObject();
                    obj.put("id", s.getStudentID());
                    obj.put("name", s.getName());
                    obj.put("phone", s.getPhone());
                    
                    if(attendance.retrieveStudentAttendances(s.getStudentID(), l.getLessonid())){
                        obj.put("attended", "Present");
                    }
                    
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("markLessonModal")){
                int classID = Integer.parseInt(request.getParameter("classID"));
                int studentID = Integer.parseInt(request.getParameter("studentID"));
                //int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                
                AttendanceDAO attendance = new AttendanceDAO();
                boolean status = attendance.updateStudentAttendance(studentID, lessonID, classID, 0, true);
                int attendanceNum = attendance.retrieveNumberOfStudentsAttended(lessonID);
                
                JSONObject toReturn = new JSONObject().put("data", status);
                toReturn.put("attendance", attendanceNum);
                String json = toReturn.toString();
                out.println(json);
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
