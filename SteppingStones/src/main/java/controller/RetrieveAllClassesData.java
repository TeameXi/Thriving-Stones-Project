/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Subject;
import entity.Tutor_HourlyRate_Rel;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.LevelDAO;
import model.SubjectDAO;
import model.TutorDAO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author MOH MOH SAN
 */
@WebServlet(name = "RetrieveAllClassesData", urlPatterns = {"/RetrieveAllClassesData"})
public class RetrieveAllClassesData extends HttpServlet {

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
        try {
            PrintWriter out = response.getWriter();
            JSONObject parentObj = new JSONObject();

            int branch_id = 0;
            if (!request.getParameter("branchId").equals("")) {
                branch_id = Integer.parseInt(request.getParameter("branchId"));
            }

            int level_id = 0;
            if (!request.getParameter("levelId").equals("")) {
                level_id = Integer.parseInt(request.getParameter("levelId"));
            }

            //=============== TUTOR RETRIEVING ================ //
            JSONArray tutorJSONLists = new JSONArray();
            ArrayList<Tutor_HourlyRate_Rel> tutorList = TutorDAO.tutorSubjectListsForSpecificBranch(branch_id);
           
            if (level_id != 0) {
                tutorList = TutorDAO.tutorSubjectListsForSpecificLevel(level_id, branch_id);
            }
            JSONObject tempTutorObj = new JSONObject();
            tempTutorObj.put("key", 0);
            tempTutorObj.put("label","----Select Tutor----");
            tutorJSONLists.put(tempTutorObj);
            for (Tutor_HourlyRate_Rel t : tutorList) {
                JSONObject obj = new JSONObject();
                obj.put("key", t.getTutor_id());
              
                int lvlId = t.getLevel_id();
                String lvlName = "Primary "+lvlId;
                if(lvlId > 6){
                    lvlName = "Secondary "+(lvlId-6);
                }
                obj.put("label", t.getTutor_name() + "( "+lvlName+" - "+t.getSubject_name()+" - $ " + t.getHourly_pay() + ")");
                obj.put("level", t.getLevel_id());
                obj.put("subject",t.getSubject_id());
                
                tutorJSONLists.put(obj);
            }
            parentObj.put("tutor", tutorJSONLists);
            //=============== END OF TUTOR RETRIEVING ================ //

            //=============== SUBJECT RETRIEVING ================ //
            JSONArray subjectJSONLists = new JSONArray();
            ArrayList<Subject> subjectList = SubjectDAO.retrieveSubjectsByBranch(branch_id);
            if (level_id != 0) { 
               subjectList = LevelDAO.retrieveAllSubjectsBelongToLevelAndBranch(level_id, branch_id);
            }    
            for (Subject s : subjectList) {
                JSONObject obj = new JSONObject();
                obj.put("key", s.getSubjectId());
                obj.put("label", s.getSubjectName());
                subjectJSONLists.put(obj);
            }
            parentObj.put("subject", subjectJSONLists);
            //=============== END OF SUBJECT RETRIEVING ============= //

            
            //=============== CLASSES RETRIEVING ============= //
            JSONArray classObjLists = new JSONArray();
           
            ArrayList<entity.Class> classLists = ClassDAO.listAllClassesForSpecificBranchWithCost(branch_id);
            if(level_id != 0){
                classLists = ClassDAO.listAllClassesForSpecificLevelWithCost(branch_id, level_id);
            }
            for (entity.Class c : classLists) {
                int classId = c.getClassID();
                int subjectId = c.getSubjectID();
                double fees = c.getMthlyFees();
                int tutorId = c.getTutorID();
                String[] timingArr = c.getClassTime().split("-");
                String startingTime = timingArr[0];
                String endingTime = timingArr[1];
                String day = c.getClassDay();
                String start_date = c.getStartDate() + " " + startingTime;
                String end_date = c.getEndDate() + " " + endingTime;
                
                String holiday_date = c.getHolidayDate();
                
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                Date start_time = format.parse(startingTime);
                Date end_time = format.parse(endingTime);
                long difference = (end_time.getTime() - start_time.getTime()) / 1000;

                JSONObject obj = new JSONObject();
                obj.put("id", classId);
                obj.put("start_date", start_date);
                obj.put("end_date", end_date);
                obj.put("text", subjectId + "");
                obj.put("subject", subjectId);
                obj.put("fees", fees);
                obj.put("tutor", tutorId);
                obj.put("day", day);
                obj.put("level", c.getLevel());
                obj.put("event_pid", "0");
                obj.put("event_length", difference);
                obj.put("holiday_date",holiday_date);
                obj.put("restricted", true);
                if (day.equals("Mon")) {
                    obj.put("rec_type", "week_1___1#11");
                } else if (day.equals("Tue")) {
                    obj.put("rec_type", "week_1___2#11");
                } else if (day.equals("Wed")) {
                    obj.put("rec_type", "week_1___3#11");
                } else if (day.equals("Thur")) {
                    obj.put("rec_type", "week_1___4#11");
                } else if (day.equals("Fri")) {
                    obj.put("rec_type", "week_1___5#11");
                } else if (day.equals("Sat")) {
                    obj.put("rec_type", "week_1___6#11");
                } else if (day.equals("Sun")) {
                    obj.put("rec_type", "week_1___7#11");
                }
                classObjLists.put(obj);
            }
            parentObj.put("class",classObjLists);
            //=============== END OF CLASSES RETRIEVING ============= //
            out.println(parentObj);

        } catch (IOException | NumberFormatException | ParseException | JSONException ex) {
            System.out.println(-1);
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
