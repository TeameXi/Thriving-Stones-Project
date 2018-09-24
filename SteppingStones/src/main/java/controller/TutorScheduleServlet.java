/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Lesson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.LessonDAO;
import org.json.JSONArray;
import org.json.JSONObject;
import entity.Class;
import entity.Student;
import java.util.HashMap;
import model.AttendanceDAO;
import model.StudentClassDAO;
import model.TutorDAO;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Xin
 */
@WebServlet(name = "TutorScheduleServlet", urlPatterns = {"/TutorScheduleServlet"})
public class TutorScheduleServlet extends HttpServlet {

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
            
            if(action.equals("retrieve")){
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                JSONArray array = new JSONArray();
                ArrayList<Lesson> lessons = LessonDAO.retrieveLessonsByTutor(tutorID);
                
                for(Lesson l : lessons){
                    JSONObject obj = new JSONObject();
                    obj.put("id", l.getLessonid());
                    Class c = ClassDAO.getClassByID(l.getClassid());
                    obj.put("start_date", l.getStartDate());
                    obj.put("end_date", l.endDate());
                    obj.put("text", c.getLevel() + " " + c.getSubject());
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("retrieveClassDetails")){
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                
                Lesson lesson = LessonDAO.getLessonByID(lessonID);
                JSONObject obj = new JSONObject();
                obj.put("attendance", new AttendanceDAO().retrieveNumberOfStudentsAttended(lessonID));
                System.out.println(new AttendanceDAO().retrieveNumberOfStudentsAttended(lessonID));
                obj.put("id", lessonID);
                obj.put("startDate", lesson.getStartDate());
                obj.put("endDate", lesson.endDate());
                obj.put("tutor", new TutorDAO().retrieveSpecificTutorById(lesson.getTutorid()).getName());
                obj.put("classSize", new StudentClassDAO().retrieveNumberOfStudentByClass(lesson.getClassid()));
                obj.put("className", new ClassDAO().getClassByID(lesson.getClassid()).getClassDay() + " " + new ClassDAO().getClassByID(lesson.getClassid()).getStartTime() + "-" + new ClassDAO().getClassByID(lesson.getClassid()).getEndTime());
                
                HashMap<String, String> map = new LessonDAO().retrieveUpdatedLessonDate(lessonID);
                obj.put("changedStart", map.get("start"));
                obj.put("changedEnd", map.get("end"));
                
                System.out.println(new LessonDAO().retrieveUpdatedLessonDate(lessonID) + " HALPPPPPPP");
                String json = obj.toString();
                out.println(json);
            }else if(action.equals("retrieveStudents")){
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                
                Lesson lesson = LessonDAO.getLessonByID(lessonID);
                JSONArray array = new JSONArray();
                ArrayList<Student> students = StudentClassDAO.listAllStudentsByClass(lesson.getClassid());
                
                for(Student s : students){
                    JSONObject obj = new JSONObject();
                    obj.put("id", s.getStudentID());
                    obj.put("name", s.getName());
                    obj.put("phone", s.getPhone());
                    
                    if(new AttendanceDAO().retrieveStudentAttendances(s.getStudentID(), lessonID)){
                        obj.put("attended", "Present");
                    }
                    array.put(obj);
                }
                JSONObject toReturn = new JSONObject().put("data", array);
                String json = toReturn.toString();
                out.println(json);
            }else if(action.equals("mark")){
                int studentID = Integer.parseInt(request.getParameter("studentID"));
                int tutorID = Integer.parseInt(request.getParameter("tutorID"));
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                int classID = LessonDAO.getLessonByID(lessonID).getClassid();
                
                AttendanceDAO attendance = new AttendanceDAO();
                boolean status = attendance.updateStudentAttendance(studentID, lessonID, classID, tutorID, true);
                int numStudents = attendance.retrieveNumberOfStudentsAttended(lessonID);
                
                JSONObject toReturn = new JSONObject().put("status", status);
                toReturn.put("attendance", numStudents);
                String json = toReturn.toString();
                out.println(json);
            }else if (action.equals("updateLessonDate")){
                int lessonID = Integer.parseInt(request.getParameter("lessonID"));
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                
                DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime startFormat = pattern.parseDateTime(startDate);
                DateTime endFormat = pattern.parseDateTime(endDate);
                
                boolean status = new LessonDAO().updateLessonDateTutor(lessonID,pattern.print(startFormat), pattern.print(endFormat));
                
                JSONObject obj = new JSONObject().put("data", status);
                String json = obj.toString();
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
