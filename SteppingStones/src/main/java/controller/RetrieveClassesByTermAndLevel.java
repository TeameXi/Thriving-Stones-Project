/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Holiday;
import entity.Subject;
import entity.Tutor;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.LevelDAO;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Date;
import model.HolidayDAO;
import model.TutorDAO;

/**
 *
 * @author MOH MOH SAN
 */
@WebServlet(name = "RetrieveClassesByTermAndLevel", urlPatterns = {"/RetrieveClassesByTermAndLevel"})
public class RetrieveClassesByTermAndLevel extends HttpServlet {

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
        try{
            PrintWriter out = response.getWriter();
            int term = 0;
            int branch_id = 0;
            int level_id = 0;
            String level_name = request.getParameter("level");

            if (!request.getParameter("term").equals("")) {
                term = Integer.parseInt(request.getParameter("term"));
            }

            if (!request.getParameter("branch_id").equals("")) {
                branch_id = Integer.parseInt(request.getParameter("branch_id"));
            }

            if (!level_name.equals("")) {
                level_id = LevelDAO.retrieveLevelID(level_name);
            }

            if (term == 0 || branch_id == 0 || level_id == 0) {
                out.println(-1);
            } else {
                if (request.getParameter("year") != null) {
                    int year = Integer.parseInt(request.getParameter("year"));
                    
                    JSONObject parentObj = new JSONObject();
                    
                    // For Holiday
                    JSONArray holidayObjLists = new JSONArray();
                    ArrayList<Holiday> holidayLists = HolidayDAO.getHolidayLists(year, term);
                    
                    for(Holiday h : holidayLists){
                        int holidayId = h.getHolidayId();
                        String holidayName = h.getHolidayName();
                        String holidayDate = h.getDate();
                        if(holidayDate.equals("7")){
                            holidayDate = "0";
                        }
                        
                        JSONObject obj = new JSONObject();
                        obj.put("key", holidayId);
                        obj.put("label", holidayName);
                        obj.put("day",holidayDate);
                        
                        holidayObjLists.put(obj);    
                    }
                    parentObj.put("holiday",holidayObjLists);
                    
                    // For Class
                    JSONArray classObjLists =new JSONArray();
                    ArrayList<entity.Class> classLists = ClassDAO.getClassesByTermAndLevelAndYear(level_id, term, branch_id,level_name,year);
                    
                    for(entity.Class c : classLists){
                        int classId = c.getClassID();
                        int subjectId = c.getSubjectID();
                        double fees = c.getMthlyFees();
                        int tutorId = c.getTutorID();
                        String[] timingArr = c.getClassTime().split("-");
                        String startingTime = timingArr[0];
                        String endingTime = timingArr[1];
                        String day = c.getClassDay();
                        String start_date = c.getStartDate()+" "+startingTime;
//                        String end_date = c.getEndDate()+" "+startingTime;
                        String end_date = c.getEndDate()+" "+endingTime;
                                               
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        
                        
                        Date start_time = format.parse(startingTime);
                        Date end_time = format.parse(endingTime);
                        long difference = (end_time.getTime() - start_time.getTime())/1000;
                        
                      
                        JSONObject obj = new JSONObject();
                        obj.put("id", classId);
                        obj.put("start_date", start_date);
                        obj.put("end_date", end_date);
                        obj.put("text", subjectId+"");
                        obj.put("subject", subjectId);
                        obj.put("fees", fees);
                        obj.put("tutor",tutorId);
                        obj.put("day", day);
                        obj.put("event_pid","0");
                        obj.put("event_length",difference);
                        obj.put("restricted",true);
                        if(day.equals("Mon")){
                            obj.put("rec_type","week_1___1#11");
                        }else if(day.equals("Tue")){
                            obj.put("rec_type","week_1___2#11");
                        }else if(day.equals("Wed")){
                            obj.put("rec_type","week_1___3#11");
                        }else if(day.equals("Thur")){
                            obj.put("rec_type","week_1___4#11");
                        }else if(day.equals("Fri")){
                            obj.put("rec_type","week_1___5#11");
                        }else if(day.equals("Sat")){
                            obj.put("rec_type","week_1___6#11");
                        }else if(day.equals("Sun")){
                            obj.put("rec_type","week_1___7#11");
                        }
                        classObjLists.put(obj);
                    }
                    parentObj.put("class",classObjLists);
                    
                    
                    // For Tutor
                    JSONArray tutorJSONLists =new JSONArray();
                    ArrayList<Tutor> tutorList = TutorDAO.retrieveTutorsShortInfoByBranch(branch_id);
                    for(Tutor t : tutorList){
                        JSONObject obj = new JSONObject();
                        obj.put("key", t.getTutorId());
                        obj.put("label",t.getName());
                        tutorJSONLists.put(obj);
                    }
                    parentObj.put("tutor", tutorJSONLists);
                    
                    
                    // For Subject
                    JSONArray subjectJSONLists =new JSONArray();
                    ArrayList<Subject> subjectLists = LevelDAO.retrieveAllSubjectsBelongToLevelAndBranch(level_id,branch_id);
                    for(Subject s : subjectLists){
                        JSONObject obj = new JSONObject();
                        obj.put("key", s.getSubjectId());
                        obj.put("label",s.getSubjectName());
                        subjectJSONLists.put(obj);
                    }
                    parentObj.put("subject",subjectJSONLists);
                    
                    // For General Info
                    JSONObject generalObj = new JSONObject();
                    generalObj.put("lvl_id",level_id);
                    generalObj.put("term",term);
                    generalObj.put("year",year);
                    generalObj.put("branch",branch_id);
                    parentObj.put("general",generalObj);
                          
                    out.println(parentObj);

                } else {
                    Map<String, ArrayList<entity.Class>> classMapLists = ClassDAO.groupClassesByTimingAndDay(level_id, term, branch_id, level_name);

                    JSONArray classObjLists = new JSONArray();

                    for (String key : classMapLists.keySet()) {
                        ArrayList<entity.Class> tempClass = classMapLists.get(key);
                        JSONObject obj = new JSONObject();
                        obj.put(key, tempClass);
                        classObjLists.put(obj);
                    }

                    if (classObjLists.length() > 0) {
                        out.println(classObjLists);
                    } else {
                        out.println(0);
                    }
                }

            }
        }catch(Exception e){
            System.out.println(e);
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
