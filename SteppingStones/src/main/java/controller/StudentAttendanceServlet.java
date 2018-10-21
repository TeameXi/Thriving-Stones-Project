/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import entity.Class;
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
import model.TutorDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Riana
 */
@WebServlet(name = "StudentAttendanceServlet", urlPatterns = {"/StudentAttendanceServlet"})
public class StudentAttendanceServlet extends HttpServlet {

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

            LessonDAO lessonDAO = new LessonDAO();
            ClassDAO classDAO = new ClassDAO();
            AttendanceDAO a = new AttendanceDAO();

            switch (action) {
                case "retrieve": {
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    ArrayList<Class> classes = ClassDAO.listAllClasses(branchID);
                    JSONArray array = new JSONArray();
                    for (Class c : classes) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", c.getClassID());
                        System.out.println(c.getClassID());
                        obj.put("name", c.getClassDay() + " " + c.getStartTime() + "-" + c.getEndTime()
                                + "<br/>" + c.getLevel() + " " + c.getSubject());
                        System.out.println(c.getTutorID());
                        obj.put("tutor", new TutorDAO().retrieveSpecificTutorById(c.getTutorID()).getName());
                        array.put(obj);
                    }
                    JSONObject obj = new JSONObject().put("data", array);
                    out.println(obj.toString());
                    break;
                }
                case "retrieveStudents": {
                    int classID = Integer.parseInt(request.getParameter("classID"));
                    int branchID = Integer.parseInt(request.getParameter("branchID"));
                    ArrayList<Student> students = classDAO.retrieveStudentsByClass(classID);
                    JSONArray array = new JSONArray();
                    for (Student s : students) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", s.getStudentID());
                        obj.put("name", s.getName());

                        JSONArray lessons = new JSONArray();
                        ArrayList<Lesson> lessonList = lessonDAO.retrieveAllLessonListsBeforeCurr(classID);

                        for (Lesson l : lessonList) {
                            JSONObject lesson = new JSONObject();
                            lesson.put("id", l.getLessonid());
                            lesson.put("date", l.getLessonDate());
                            lesson.put("attended", a.retrieveStudentAttendances(s.getStudentID(), l.getLessonid()));
                            lessons.put(lesson);
                        }
                        obj.put("lessons", lessons);

                        DecimalFormat formatter = new DecimalFormat("#.##");
                        String attendance = formatter.format(a.retrieveNumberOfStudentAttendances(s.getStudentID(), classID) / lessonDAO.retrieveNumberOfLessons(classID) * 100);

                        obj.put("attendance", attendance + "%");
                        array.put(obj);
                    }
                    String json = array.toString();
                    System.out.println(json);
                    out.println(json);
                    break;
                }
                case "mark":
                    String lessons = request.getParameter("lessons");
                    String[] lesson_student = lessons.split(" ");
                    int classID = Integer.parseInt(request.getParameter("classID"));

                    int updated = 0;

                    JSONArray attendances = new JSONArray();

                    for (String s : lesson_student) {
                        int lessonID = Integer.parseInt(s.split("-")[0]);
                        int studentID = Integer.parseInt(s.split("-")[1]);
                        int attended = Integer.parseInt(s.split("-")[2]);

                        if (attended == 1) {
                            a.updateStudentAttendance(studentID, lessonID, classID, 0, true);
                            updated++;
                        } else {
                            a.updateStudentAttendance(studentID, lessonID, classID, 0, false);
                            updated++;
                        }

                        DecimalFormat formatter = new DecimalFormat("#.##");
                        String attendance = formatter.format(a.retrieveNumberOfStudentAttendances(studentID, classID) / lessonDAO.retrieveNumberOfLessons(classID) * 100);

                        for (int i = 0; i < attendances.length(); i++) {
                            JSONObject obj = attendances.getJSONObject(i);
                            
                            if(obj.has(studentID + "")){
                                attendances.remove(i);
                            }
                        }

                        attendances.put(new JSONObject().put(studentID + "", attendance + "%"));
                    }

                    JSONObject obj = new JSONObject();

                    if (updated == lesson_student.length) {
                        obj.put("data", true);
                    } else {
                        obj.put("data", false);
                    }

                    obj.put("attendance", attendances);
                    out.println(obj.toString());

                    break;
                default:
                    break;
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
