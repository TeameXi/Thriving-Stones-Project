package model;

import connection.ConnectionManager;
import entity.Lesson;
import entity.TutorPay;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LessonDAO {

    public void updateLesson(int tutorID, int lessonID, String startDate, String endDate) {
        String sql = "";
        PreparedStatement stmt = null;

        try (Connection conn = ConnectionManager.getConnection()) {

            if (tutorID != 0) {
                if (startDate != null && endDate != null) {
                    sql = "update lesson set tutor_id = ?, changed_start_date = ?. changed_end_date = ? where lesson_id = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, tutorID);
                    stmt.setString(2, startDate);
                    stmt.setString(3, endDate);
                    stmt.setInt(4, lessonID);
                } else {
                    sql = "update lesson set tutor_id = ? where lesson_id = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, tutorID);
                    stmt.setInt(2, lessonID);
                }
            } else {
                if (startDate != null && endDate != null) {
                    sql = "update lesson set changed_start_date = ?. changed_end_date = ? where lesson_id = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, startDate);
                    stmt.setString(2, endDate);
                    stmt.setInt(3, lessonID);
                }
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean updateTutorForOneLesson(int classId, int tutorId, Timestamp lessonStartDate, Timestamp lessonEndDate) {
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "update lesson set tutor_id = ? where class_id = ? and start_date = ? and end_date=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorId);
            stmt.setInt(2, classId);
            stmt.setTimestamp(3, lessonStartDate);
            stmt.setTimestamp(4, lessonEndDate);

            System.out.println(sql);
            System.out.println("Class: " + classId);
            System.out.println("Tutor: " + tutorId);
            System.out.println(lessonStartDate);
            System.out.println(lessonEndDate);

            int num = stmt.executeUpdate();
            if (num > 0) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean createLesson(int classid, int tutorid, String startDate, String endDate, int reminder_status, int reminder_term, String type) {
        String sql = "";
        if (type.equals("P")) {
            sql = "INSERT into lesson (class_id, start_date, end_date, tutor_id, tutor_attended, tutor_payment_status, reminder_term, reminder_status)"
                    + "VALUES (?,?,?,?,?,?,?,?)";

            try (Connection conn = ConnectionManager.getConnection();) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, classid);
                stmt.setString(2, startDate);
                stmt.setString(3, endDate);
                stmt.setInt(4, tutorid);
                stmt.setInt(5, 0);
                stmt.setInt(6, 0);
                stmt.setInt(7, reminder_term);
                stmt.setInt(8, reminder_status);
                System.out.println(stmt);
                int rows = stmt.executeUpdate();
                System.out.println(rows);
                if (rows > 0) {
                    return true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            sql = "INSERT into lesson (class_id, start_date, end_date, tutor_id, tutor_attended, tutor_payment_status, reminder_status)"
                    + "VALUES (?,?,?,?,?,?,?)";

            try (Connection conn = ConnectionManager.getConnection();) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, classid);
                stmt.setString(2, startDate);
                stmt.setString(3, endDate);
                stmt.setInt(4, tutorid);
                stmt.setInt(5, 0);
                stmt.setInt(6, 0);
                stmt.setInt(7, reminder_status);
                System.out.println(stmt);
                int rows = stmt.executeUpdate();
                System.out.println(rows);
                if (rows > 0) {
                    return true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return false;
    }

    public static ArrayList<Lesson> retrieveAllLessonLists(int classid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, start_date, end_date from lesson where class_id = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return lessons;
    }
    
    public static ArrayList<Lesson> retrieveAllReplacementLessonLists(int classid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, start_date, end_date from lesson where class_id = ? and replacement_tutor_id != 0 and date(start_date) > curdate()";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return lessons;
    }

    public static ArrayList<Lesson> retrieveAllLessonListsByTutor(int classid, int tutorid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, start_date, end_date from lesson where class_id = ? and tutor_id = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classid);
            stmt.setInt(2, tutorid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return lessons;
    }

    public static ArrayList<Lesson> retrieveLessonsByTutor(int tutorid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, start_date, end_date from lesson where tutor_id = ? or replacement_tutor_id = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorid);
            stmt.setInt(2, tutorid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return lessons;
    }

    public static Lesson getLessonByID(int lessonID) {
        Lesson les = null;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from lesson where lesson_id = ?");
            stmt.setInt(1, lessonID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int lessonid = rs.getInt("lesson_id");
                int classid = rs.getInt("class_id");
                int tutorid = rs.getInt("tutor_id");
                int tutorAttended = rs.getInt("tutor_attended");
                String startTime = rs.getString("start_date");
                String endTime = rs.getString("end_date");

                les = new Lesson(lessonid, classid, tutorid, tutorAttended, startTime, endTime);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return les;
    }

    public static boolean updateTutorAttendance(int lessonid, int attended) {
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "update lesson set tutor_attended = ? where lesson_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, attended);
            stmt.setInt(2, lessonid);

            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean retrieveAttendanceForLesson(int lessonID) {
        String sql = "select tutor_attended from lesson where lesson_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return (1 == rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean retrieveAttendanceForLessonAbsent(int lessonID) {
        String sql = "select tutor_attended from lesson where lesson_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return ((-1) == (rs.getInt(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static String getNearestLessonDate(int classID) {
        String joinDate = "";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select date(start_date) as lesson_date from lesson where class_id = ? and "
                    + "date(start_date) >= curdate() order by start_date limit 1;");
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                joinDate += rs.getString("lesson_date");
            }
        } catch (SQLException e) {
            System.out.print("Error in getNearestLessonDate method" + e.getMessage());
        }
        return joinDate;
    }
    public double retrieveNumberOfReplacementLessons(int classID, int tutorID) {
        double total = 0;

        String sql = "select distinct lesson_id from lesson where class_id = ? and replacement_tutor_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setInt(2, tutorID);
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                total += 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    public String retrieveNumberTutorAttendancePerClass(int classID, int tutorID) {
        double attended = 0;
        double total = retrieveNumberOfLessons(classID);

        String sql = "select tutor_attended from lesson where tutor_id = ? and class_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorID);
            stmt.setInt(2, classID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if ((1 == rs.getInt(1))) {
                    attended += 1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        DecimalFormat df = new DecimalFormat("#.##");
        return df.format((attended / total) * 100);
    }
    public String retrieveNumberTutorAttendancePerReplacementClass(int classID, int tutorID) {
        double attended = 0;
        double total = retrieveNumberOfReplacementLessons(classID, tutorID);

        String sql = "select tutor_attended from lesson where replacement_tutor_id = ? and class_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorID);
            stmt.setInt(2, classID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if ((1 == rs.getInt(1))) {
                    attended += 1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        DecimalFormat df = new DecimalFormat("#.##");
        return df.format((attended / total) * 100);
    }

    public double retrieveNumberOfLessons(int classID) {
        double total = 0;

        String sql = "select distinct lesson_id from lesson where class_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                total += 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    public static ArrayList<Lesson> retrieveLessonsByTutorForAttendance(int tutorid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, start_date, end_date from lesson where (tutor_id = ? or replacement_tutor_id = ?) and date(start_date) <= CURDATE()";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorid);
            stmt.setInt(2, tutorid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return lessons;
    }
    public String retrieveTotalPercentageAttendance(int tutorID) {
        double attended = 0;
        double total = retrieveLessonsByTutorForAttendance(tutorID).size();

        DecimalFormat df = new DecimalFormat("#.##");

        if (total > 0) {
            String sql = "select tutor_attended from lesson where tutor_id = ?";

            try (Connection conn = ConnectionManager.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, tutorID);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    if ((1 == rs.getInt(1))) {
                        attended += 1;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(df.format((attended / total) * 100));
            return df.format((attended / total) * 100);
        } else {
            return df.format(0);
        }
    }

    public int retrievePaymentStatus(int lessonID, int tutorID) {
        int status = 2;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select tutor_payment_status from lesson where lesson_id = ? and tutor_id = ?");
            stmt.setInt(1, lessonID);
            stmt.setInt(2, tutorID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                status = rs.getInt(1);
            }
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public static int retrieveNoOfLessonForFirstInstallment(int classID, String joinDate) {
        int noOfLessons = 0;
        String sql = "select count(lesson_id) as noOfLessons from lesson where class_id = ? and date(start_date) >= ? and "
                + "date(start_date) <= (select date(start_date) from lesson where class_id = ? and date(start_date) > ? and reminder_status != 0 order by start_date limit 1);";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setString(2, joinDate);
            stmt.setInt(3, classID);
            stmt.setString(4, joinDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                noOfLessons = rs.getInt("noOfLessons");
            }
        } catch (Exception e) {
            System.out.println("Error in retrieveNoOfLessonForFirstInstallment method" + e.getMessage());
        }

        return noOfLessons;
    }

    public static int retrieveNoOfLessonPremium(int classID, String joinDate) {
        int noOfLessons = 0;
        String sql = "select count(lesson_id) as noOfLessons from lesson where class_id = ? and date(start_date) >= ? and "
                + "date(start_date) <= (select date(start_date) from lesson where class_id = ? and date(start_date) > ? and reminder_term != 0 order by start_date limit 1);";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setString(2, joinDate);
            stmt.setInt(3, classID);
            stmt.setString(4, joinDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                noOfLessons = rs.getInt("noOfLessons");
            }
        } catch (Exception e) {
            System.out.println("Error in retrieveNoOfLessonForFirstInstallment method" + e.getMessage());
        }

        return noOfLessons;
    }
    
//    public boolean updateLessonDate(int lessonID, int tutorID, String changedStart, String changedEnd) {
//        String sql = "select tutor_id from lesson where lesson_id = ?";
//        
//        try (Connection conn = ConnectionManager.getConnection()) {
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, lessonID);
//            
//            ResultSet rs = stmt.executeQuery();
//            int originalTutorId = 0;
//            if(rs.next()){
//                originalTutorId = rs.getInt(1);
//            }
//            
//            sql = "update lesson set start_date = ?, end_date = ?, tutor_id = ?, replacement_tutor_id = ? where lesson_id = ?";
//            stmt = conn.prepareStatement(sql);
//            
//            stmt.setString(1, changedStart);
//            stmt.setString(2, changedEnd);
//            stmt.setInt(3, tutorID);
//            stmt.setInt(4, originalTutorId);
//            stmt.setInt(5, lessonID);
//            int rowsUpdated = stmt.executeUpdate();
//            if (rowsUpdated > 0) {
//                return true;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }

    public boolean updateLessonDate(int lessonID, int tutorID, String changedStart, String changedEnd) {
        String sql = "update lesson set start_date = ?, end_date = ?, replacement_tutor_id = ? where lesson_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, changedStart);
            stmt.setString(2, changedEnd);
            stmt.setInt(3, tutorID);
            stmt.setInt(4, lessonID);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateLessonDateTutor(int lessonID, String changedStart, String changedEnd) {
        String sql = "update lesson set changed_start_date = ?, changed_end_date = ? where lesson_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, changedStart);
            stmt.setString(2, changedEnd);
            stmt.setInt(3, lessonID);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public HashMap<String, String> retrieveUpdatedLessonDate(int lessonID) {
        String sql = "select changed_start_date, changed_end_date from lesson where lesson_id = ?";
        HashMap<String, String> editedDates = new HashMap<>();

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String start = rs.getString(1);
                String end = rs.getString(2);
                editedDates.put("start", start.substring(2, start.length() - 2));
                editedDates.put("end", end.substring(0, end.length() - 2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return editedDates;
    }

    public static boolean deleteAttendancebyID(int studentID) {
        boolean deletedStatus = false;
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "delete from student_attendance where student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentID);
            stmt.executeUpdate();
            conn.commit();
            deletedStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return deletedStatus;
    }
    
    public boolean retrieveOverlapLessons(int tutorID, String start, String end, int classID, int dayOfWeek){
        String sql = "select * from lesson, class where lesson.class_id = class.class_id and lesson.tutor_id = ? and class_day = ? and (start_time between ? and ? or ? between start_time and end_time) and lesson.class_id <> ?";
        
        String day = "";
        
        switch(dayOfWeek){
            case 1:
                day = "Mon";
                break;
            case 2:
                day = "Tue";
                break;
            case 3:
                day = "Wed";
                break;
            case 4:
                day = "Thur";
                break;
            case 5:
                day = "Fri";
                break;
            case 6:
                day = "Sat";
                break;
            case 7:
                day = "Sun";
                break;
        }
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, tutorID);
            stmt.setString(2, day);
            stmt.setString(3, start);
            stmt.setString(4, end);
            stmt.setString(5, start);
            stmt.setInt(6, classID);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    public boolean retrieveOverlappingLessonsForTutor(int tutorID, String start, String end, int classID) {
        String sql = "select * from lesson where (tutor_id = ? or replacement_tutor_id = ?) and (start_date between ? and ? or ? between start_date and end_date) and class_id <> ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorID);
            stmt.setInt(2, tutorID);
            stmt.setString(3, start);
            stmt.setString(4, end);
            stmt.setString(5, start);
            stmt.setInt(6, classID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void deleteLessonsAfterCurr(int classID){
        String sql = "delete from lesson where class_id = ? and start_date > now()";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean deleteLessons(int classID) {
        String sql = "delete from lesson where class_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteLesson(int lessonID) {
        String sql = "delete from lesson where lesson_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonID);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static LinkedList<Lesson> retrieveAllLessonListsBeforeCurr(int classid) {
        LinkedList<Lesson> lessons = new LinkedList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, start_date, end_date from lesson where class_id = ? and replacement_tutor_id = 0 and date(start_date) <= CURDATE() order by start_date asc";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return lessons;
    }
    public static LinkedList<Lesson> retrieveAllReplacementLessonListsBeforeCurr(int classid, int tutorid) {
        LinkedList<Lesson> lessons = new LinkedList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, start_date, end_date from lesson where class_id = ? and replacement_tutor_id = ? and date(start_date) <= CURDATE() order by start_date asc";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classid);
            stmt.setInt(2, tutorid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return lessons;
    }
    public static ArrayList<entity.Class> listAllReplacementClassesByTutorID(int tutorID, int branchID) {
        ArrayList<entity.Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and DATE_ADD(end_date, INTERVAL 3 MONTH) >= curdate() and class_id in (select distinct(class_id) from lesson where replacement_tutor_id = ?)");
            stmt.setInt(1, branchID);
            stmt.setInt(2, tutorID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                int levelID = rs.getInt("level_id");
                int term = rs.getInt("term");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                String type = rs.getString("class_type");
                entity.Class cls = new entity.Class(classID,levelID,level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }
    public ArrayList<Lesson> retrieveAllLessonListsAfterCurr(int classid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, start_date, end_date from lesson where class_id = ? and start_date > CURDATE()";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return lessons;
    }

    public int retrieveReplacementTutor(int lessonID) {
        String sql = "select replacement_tutor_id from lesson where lesson_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, lessonID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static Timestamp retrieveFirstClass(int classID) {

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareCall("select lesson_id, start_date from lesson where class_id = ? order by lesson_id");
            stmt.setInt(1, classID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return rs.getTimestamp(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static ArrayList<String> retrieveReplacementDates(int lessonID) {
        ArrayList<String> replacementDates = new ArrayList<>();
        String sql = "select changed_start_date, changed_end_date from lesson where lesson_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString(1) != null && rs.getString(2) != null) {
                    replacementDates.add(rs.getString(1));
                    replacementDates.add(rs.getString(2));
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return replacementDates;
    }
    
    public static ArrayList<String> retrieveReplacementDetails(int lessonID) {
        ArrayList<String> replacementDetails = new ArrayList<>();
        String sql = "select date(start_date), time(start_date), time(end_date), replacement_tutor_id from lesson where lesson_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString(1) != null && rs.getString(2) != null) {
                    replacementDetails.add(rs.getString(1));
                    replacementDetails.add(rs.getString(2));
                    replacementDetails.add(rs.getString(3));
                    replacementDetails.add(rs.getString(4));
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return replacementDetails;
    }

    public ArrayList<Lesson> retrieveLessonsAfterDateAndBeforeCurr(String date, int classID) {
        ArrayList<Lesson> lessons = new ArrayList<>();

        String sql = "select * from lesson where class_id = ? and start_date > ? and start_date > curdate()";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setString(2, date);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int lessonID = rs.getInt("lesson_id");
                int tutorID = rs.getInt("tutor_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int tutorAttended = rs.getInt("tutor_attended");
                lessons.add(new Lesson(lessonID, classID, tutorID, tutorAttended, startDate, endDate));
            }
            if (lessons.size() > 0) {
                return lessons;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void updatePaymentStatus(int lessonID, String type, int lessonNum) {
        String sql = "";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = null;

            if (type.equals("P")) {
                sql = "update lesson set reminder_term = ? where lesson_id = ?";
                stmt = conn.prepareStatement(sql);
            } else {
                sql = "update lesson set reminder_status = ? where lesson_id = ?";
                stmt = conn.prepareStatement(sql);
            }

            stmt.setInt(1, lessonNum);
            stmt.setInt(2, lessonID);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Lesson> retrieveReplacementLessons(int branchID, int tutorID) {
        ArrayList<Lesson> lessons = new ArrayList<>();

        String sql = "select * from lesson where class_id in (select class_id from class where branch_id = ?) and replacement_tutor_id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, branchID);
            stmt.setInt(2, tutorID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int lessonID = rs.getInt("lesson_id");
                int classID = rs.getInt("class_id");
                int tutorAttended = rs.getInt("tutor_attended");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                lessons.add(new Lesson(lessonID, classID, tutorID, tutorAttended, startDate, endDate));
            }

            if (lessons.size() > 0) {
                return lessons;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ArrayList<Lesson> retrieveAllLessonListsAfterCurrTS(int classid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select lesson_id, class_id, reminder_status, start_date, end_date from lesson where class_id = ? and start_date > CURDATE() and reminder_status <> 0 and reminded <> 1 order by start_date asc limit 1");
            stmt.setInt(1, classid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4), rs.getTimestamp(5));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lessons;
    }

    public static ArrayList<Lesson> retrieveLessonsForPaymentStatus(int classid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select lesson_id, class_id, reminder_status, start_date, end_date from lesson where class_id = ? and start_date < CURDATE() and reminder_status <> 0");
            stmt.setInt(1, classid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4), rs.getTimestamp(5));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lessons;
    }

    public static int getPaymentStatus(int classid, int studentid, String date) {
        int paymentDue = -1;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select outstanding_charge from payment_reminder where class_id = ? and student_id = ? and payment_due_date = ?");
            stmt.setInt(1, classid);
            stmt.setInt(2, studentid);
            stmt.setString(3, date);
            System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                paymentDue = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentDue;
    }

    // For Tutor Payment lesson listing
//    public static ArrayList<TutorPay> totalLessonTutorAttendAndPaidForClass(int tutorID, int classID, double payRate, double duration) {
//        ArrayList<TutorPay> payListByMonthlyLessons = new ArrayList<>();
//
//        String sql = "SELECT count(lesson_id),MONTHNAME(start_date),MONTH(start_date),YEAR(end_date) FROM lesson WHERE"
//                + " tutor_id = ? AND class_id = ? AND tutor_attended = 1 "
//                + "AND tutor_payment_status = 1 AND replacement_tutor_id = 0 GROUP BY YEAR(end_date),MONTH(start_date)";
//
//        try (Connection conn = ConnectionManager.getConnection()) {
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, tutorID);
//            stmt.setInt(2, classID);
//
//            ResultSet rs = stmt.executeQuery();
//
//            int inititalLesson = 1;
//            int endLesson = 0;
//            while (rs.next()) {
//                int totalLessons = rs.getInt(1);
//                endLesson = inititalLesson + totalLessons - 1;
//                String lessonName = "L" + inititalLesson + " - " + "L" + endLesson + "(" + rs.getString(2) + ")";
//                inititalLesson = endLesson + 1;
//                double amount = payRate * duration * totalLessons;
//                TutorPay tutorpayList = new TutorPay(tutorID, classID, lessonName, amount, "paid", rs.getInt(3), rs.getInt(4), totalLessons);
//                payListByMonthlyLessons.add(tutorpayList);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return payListByMonthlyLessons;
//    }

    public static int totalLessonTutorAttendedForClassMonthlyCount(int tutorID, int classID){
        String sql = "SELECT month(start_date)  FROM lesson WHERE class_id = ? AND tutor_id = ? AND tutor_attended = 1 AND tutor_payment_status = 0 GROUP BY month(start_date)";
        int rowCount = 0;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            stmt.setInt(2, tutorID);
            
            ResultSet rs = stmt.executeQuery();
           
            rs.last();
            rowCount = rs.getRow();
            rs.beforeFirst();
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rowCount;
    }
    
    public static ArrayList<TutorPay> totalLessonTutorAttendForClass(int tutorID, int classID, double payRate, double duration,int payType) {
        ArrayList<TutorPay> payListByMonthlyLessons = new ArrayList<>();

        String sql = "SELECT month(start_date) FROM lesson WHERE tutor_id = ? AND class_id = ? AND tutor_attended=0 limit 1";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorID);
            stmt.setInt(2, classID);

            ResultSet rs1 = stmt.executeQuery();
            int unpaidMonth = 0;
            if (rs1.next()) {
                unpaidMonth = rs1.getInt(1);
            }

            // PAID
            sql = "SELECT count(lesson_id),MONTHNAME(start_date),MONTH(start_date),YEAR(end_date) FROM lesson WHERE"
                    + " tutor_id = ? AND class_id = ? AND tutor_attended = 1 "
                    + "AND tutor_payment_status = 1 AND replacement_tutor_id = 0 GROUP BY YEAR(end_date),MONTH(start_date)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorID);
            stmt.setInt(2, classID);
            ResultSet rs2 = stmt.executeQuery();
            int inititalLesson = 1;
            int endLesson = 0;
            while (rs2.next()) {
                int totalLessons = rs2.getInt(1);
                endLesson = inititalLesson + totalLessons - 1;
                String lessonName = "L" + inititalLesson + " - " + "L" + endLesson;
                inititalLesson = endLesson + 1;
                double amount = payRate;
                if(payType == 0){
                    amount = payRate * duration * totalLessons;
                }
                
                TutorPay tutorpayList = new TutorPay(tutorID, classID, lessonName, amount, "paid", rs2.getInt(3), rs2.getInt(4), totalLessons);
                payListByMonthlyLessons.add(tutorpayList);
            }

            // UNPAID
            sql = "SELECT count(lesson_id),MONTHNAME(start_date),MONTH(start_date),YEAR(end_date) FROM lesson WHERE"
                    + " tutor_id = ? AND class_id = ? AND tutor_attended = 1 "
                    + "AND tutor_payment_status = 0 AND replacement_tutor_id = 0 GROUP BY YEAR(end_date),MONTH(start_date)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorID);
            stmt.setInt(2, classID);

            ResultSet rs3 = stmt.executeQuery();
            while (rs3.next()) {
                int totalLessons = rs3.getInt(1);
                endLesson = inititalLesson + totalLessons - 1;
                String lessonName = "L" + inititalLesson + " - " + "L" + endLesson;
                inititalLesson = endLesson + 1;
                double amount = payRate;
                if(payType == 0){
                    amount = payRate * duration * totalLessons;
                }
                TutorPay tutorpayList = new TutorPay(tutorID, classID, lessonName, amount, "should pay", rs3.getInt(3), rs3.getInt(4), totalLessons);
                payListByMonthlyLessons.add(tutorpayList);
            }

            // Check whether the last month is already end of month or not
            if (payListByMonthlyLessons.size() > 0) {
                TutorPay lastPay = payListByMonthlyLessons.get(payListByMonthlyLessons.size() - 1);
                int month = lastPay.getMonth();
                if (month <= unpaidMonth) {
                    lastPay.setPaidStatus("shouldn't pay");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payListByMonthlyLessons;
    }

    public ArrayList<Integer> checkForExistingLessons(int tutorID) {
        ArrayList<Integer> classes = new ArrayList<>();

        String sql = "select class_id, count(distinct lesson_id) from lesson where tutor_id = ? and date(start_date) <= curdate() group by class_id";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int classID = rs.getInt(1);
                int numLessons = rs.getInt(2);

                if (numLessons > 0) {
                    classes.add(classID);
                }
            }

            if (classes.size() > 0) {
                return classes;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void setReminded(int classid, int lesssonid) {
        int paymentDue = -1;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("update lesson set reminded = 1 where class_id = ? and lesson_id = ? and payment_due_date = ?");
            stmt.setInt(1, classid);
            stmt.setInt(2, lesssonid);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Lesson retrieveSingleLessonAfterCurr(int classid) {
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, start_date, end_date from lesson where class_id = ? and start_date > CURDATE() limit 1";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
                return lesson;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
    
    public static ArrayList<Lesson> checkForLatePayment(int classid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select lesson_id, class_id, reminder_status, start_date, end_date from lesson where class_id = ? and start_date < CURDATE() and reminder_status <> 0 and reminded_late <> 1 order by start_date desc limit 1");
            stmt.setInt(1, classid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4), rs.getTimestamp(5));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lessons;
    }
     
    public static void setRemindedLate(int classid, int lesssonid) {
       int paymentDue = -1;
       try (Connection conn = ConnectionManager.getConnection()) {
           PreparedStatement stmt = conn.prepareStatement("update lesson set reminded_late = 1 where class_id = ? and lesson_id = ? and payment_due_date = ?");
           stmt.setInt(1, classid);
           stmt.setInt(2, lesssonid);
           stmt.execute();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}
