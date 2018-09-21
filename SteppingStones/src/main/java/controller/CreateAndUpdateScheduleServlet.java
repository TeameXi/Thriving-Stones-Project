package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ClassDAO;
import model.HolidayDAO;
import model.LessonDAO;

/**
 *
 * @author MOH MOH SAN
 */
@WebServlet(urlPatterns = {"/CreateAndUpdateScheduleServlet"})
public class CreateAndUpdateScheduleServlet extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            String add = request.getParameter("add_new");
            
            // Add Schedule
            if (add.equals("true")) {
                String lvl = request.getParameter("lvl_id");

                String branch = request.getParameter("branch");
                if (lvl.equals("")  || branch.equals("")) {
                    out.println(-1);
                } else {
                    System.out.println("not null for lvl and branch");
                    int levelid = Integer.parseInt(lvl);
                    int branchid = Integer.parseInt(branch);
                    double fees = 0;
                    int has_reminder = 0;
                    int subjectid = 0;
                    int tutorId = 0;

                    String subject = request.getParameter("subject_id");
                    if (!subject.equals("")) {
                        subjectid = Integer.parseInt(subject);
                    }

                    if (!request.getParameter("has_reminder").equals("")) {
                        has_reminder = Integer.parseInt(request.getParameter("has_reminder"));
                    }

                    if (!request.getParameter("tutor_id").equals("")) {
                        tutorId = Integer.parseInt(request.getParameter("tutor_id"));
                    }


                    String timing = request.getParameter("timing");
                    String classDay = request.getParameter("class_day");
                    String startDate = request.getParameter("start_date");
                    String endDate = request.getParameter("end_date");

                    // Handling Holiday
//                    String holiday = request.getParameter("holiday");
//                    ArrayList<String> holidayDates = new ArrayList<>();
//                    if (!holiday.equals("")) {
//                        holidayDates = HolidayDAO.getHolidayDateLists(holiday);
//                        if (holidayDates.size() > 0) {
//                            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                            try {
//                                Date convertedEndDate = formatter.parse(endDate);
//                                Calendar c = Calendar.getInstance();
//                                c.setTime(convertedEndDate);
//                                for (String h : holidayDates) {
//                                    Date currentSelectedHoliday = formatter.parse(h);
//                                    if (convertedEndDate.after(currentSelectedHoliday)) {
//                                        c.add(Calendar.WEEK_OF_MONTH, 1);
//                                        endDate = formatter.format(c.getTime());
//                                    }
//
//                                }
//
//                                while (holidayDates.contains(endDate)) {
//                                    convertedEndDate = formatter.parse(endDate);
//                                    c.add(Calendar.WEEK_OF_MONTH, 1);
//                                    endDate = formatter.format(c.getTime());
//
//                                    String nextYearHoliday = HolidayDAO.getHolidayDate(endDate);
//                                    if (!nextYearHoliday.equals("")) {
//                                        convertedEndDate = formatter.parse(endDate);
//                                        c.add(Calendar.WEEK_OF_MONTH, 1);
//                                        endDate = formatter.format(c.getTime());
//                                    }
//                                }
//
//                            } catch (ParseException e) {
//                                out.println(-1);
//                            }
//                        }
//                    }
                       System.out.println("B4 Class");
                    int classid = ClassDAO.createClass(levelid, subjectid,fees, has_reminder, timing, classDay, startDate, endDate, branchid, tutorId);
                    System.out.println(classid);
                    if (classid == 0) {
                        out.println(-1);
                    } else {
                        boolean status = insertLesson(classid, startDate, endDate, timing, tutorId);
                        if (status) {
                            out.println(1);
                        } else {
                            out.println(-1);
                        }
                    }
                }
            } else if (add.equals("false")) {
                //Edit schedule
                String classVal = request.getParameter("classId");
                String date = request.getParameter("lessonDate").trim();
                String tutorVal = request.getParameter("tutorId");
                String time = request.getParameter("lessonTime").trim();
                String assignmentType = request.getParameter("tutor_assignmentType");
   
                int classId = 0;
                int tutorId = 0;
                
                if(!classVal.equals("")){
                    classId = Integer.parseInt(classVal);
                }
                
                if(!tutorVal.equals("")){
                    tutorId = Integer.parseInt(tutorVal);
                }
                
                if(classId == 0){
                    out.println(-1);
                }else{
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    try{
                        String timeArr[] = time.split("-");
                        String startDate = date+" "+timeArr[0];
                        String endDate = date+" "+timeArr[1];
                        Date convertedLessonStartDate = dateFormat.parse(startDate+".000");
                        Timestamp LessonStartTimestamp = new java.sql.Timestamp(convertedLessonStartDate.getTime());
                        
                        Date convertedLessonEndDate = dateFormat.parse(endDate+".000");
                        Timestamp LessonEndTimestamp = new java.sql.Timestamp(convertedLessonEndDate.getTime());
                        
                        System.out.println("fff_date"); 
                        boolean editStatus = false;
                        if(assignmentType.equals("0")){
                            editStatus = LessonDAO.updateTutorForOneLesson(classId,tutorId,LessonStartTimestamp,LessonEndTimestamp);
                            System.out.println(editStatus);
                        }
                        if(editStatus){
                            out.println(1);
                        }else{
                            out.print(2);
                        }
                    }catch(ParseException e){
                        System.out.println(e);
                        out.println(-1);
                    }
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

    private boolean insertLesson(int classid, String startDate, String endDate, String timing, int tutorid) {
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

        String[] timingArr = timing.split("-");
        Date parsedDate;
        try {
            List<Date> dates = getDatesBetween(dateFormat1.parse(startDate), dateFormat1.parse(endDate));
            for (int i = 0; i < dates.size(); i += 7) {
                String date = dateFormat1.format(dates.get(i)) + " " + timingArr[0] + ":00.000";
                parsedDate = dateFormat.parse(date);
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

                LessonDAO lessonDAO = new LessonDAO();
                lessonDAO.createLesson(classid, tutorid, timestamp);
                
            }
        } catch (ParseException ex) {
            return false;
        }

        return true;
    }

    public static List<Date> getDatesBetween(Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }

        datesInRange.add(endDate);

        return datesInRange;
    }

}
