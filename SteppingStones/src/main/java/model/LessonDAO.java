package model;

import connection.ConnectionManager;
import entity.Lesson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LessonDAO {

    public void updateLesson(String tutorID, String level, String subject) {
        String lesson = "";
        String sql = "select lesson_id from lesson where class_id in (select class_id from class where level_id = ? and subject_id = ?)";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, level);
            stmt.setString(2, subject);
            System.out.println(stmt);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lesson = rs.getString(1);
            }

            sql = "update lesson set tutor_id = ? where lesson_id = ?";
            System.out.println(sql + " CORRECT");
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, tutorID);
            stmt.setString(2, lesson);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean updateTutorForOneLesson(int classId,int tutorId,Timestamp lessonStartDate,Timestamp lessonEndDate) {
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "update lesson set tutor_id = ? where class_id = ? and start_date = ? and end_date=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorId);
            stmt.setInt(2, classId);
            stmt.setTimestamp(3, lessonStartDate);
            stmt.setTimestamp(4, lessonEndDate);
            
            System.out.println(sql);
            System.out.println("Class: "+classId);
            System.out.println("Tutor: "+tutorId);
            System.out.println(lessonStartDate);
             System.out.println(lessonEndDate);
           

            int num = stmt.executeUpdate();
            if(num > 0){
               return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean createLesson(int classid, int tutorid, String startDate, String endDate) {
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "INSERT into LESSON (class_id, start_date, end_date, tutor_id, tutor_attended)"
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classid);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
            stmt.setInt(4, tutorid);
            stmt.setInt(5, 0);
            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
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
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getTimestamp(5), rs.getTimestamp(6));
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
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getTimestamp(5), rs.getTimestamp(6));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return lessons;
    }

    public static ArrayList<Lesson> retrieveLessonsByTutor(int tutorid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, start_date, end_date from lesson where tutor_id = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getTimestamp(5), rs.getTimestamp(6));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return lessons;
    }

    public ArrayList<Lesson> retrieveAllLessonListsDateRange(Timestamp startDate, Timestamp endDate) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, lesson_date_time from lesson where lesson_date_time between ? and ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, startDate);
            stmt.setTimestamp(2, endDate);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getTimestamp(5));
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
                Timestamp startTime = rs.getTimestamp("start_date");
                Timestamp endTime = rs.getTimestamp("end_date");

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
                return rs.getBoolean(1);
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
                if (rs.getBoolean(1)) {
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

    public String retrieveTotalPercentageAttendance(int tutorID) {
        double attended = 0;
        double total = retrieveLessonsByTutor(tutorID).size();

        DecimalFormat df = new DecimalFormat("#.##");

        if (total > 0) {
            String sql = "select tutor_attended from lesson where tutor_id = ?";

            try (Connection conn = ConnectionManager.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, tutorID);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    if (rs.getBoolean(1)) {
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
            PreparedStatement stmt = conn.prepareStatement("select tutor_payment_status from lesson where lesson_id = ? and tutor_id = ? and tutor_attended = 1");
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

    public static ArrayList<Lesson> retrieveAllLessonListsBeforeCurr(int classid) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "select lesson_id, class_id, tutor_id, tutor_attended, start_date, end_date from lesson where class_id = ? and start_date < CURDATE()";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lesson lesson = new Lesson(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getTimestamp(5), rs.getTimestamp(6));
                lessons.add(lesson);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return lessons;
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
    
    public boolean updateLessonDate(int lessonID, int tutorID, String changedStart, String changedEnd){
        String sql = "update lesson set start_date = ?, end_date = ?, replacement_tutor_id = ? where lesson_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, changedStart);
            stmt.setString(2, changedEnd);
            stmt.setInt(3, tutorID);
            stmt.setInt(4, lessonID);
            
            int rowsUpdated = stmt.executeUpdate();
            System.out.println(rowsUpdated + " cool");
            if(rowsUpdated > 0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public String retrieveUpdatedLessonDate(int lessonID){
        String sql = "select edited_date from lesson where lesson_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonID);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
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
    
    public boolean retrieveOverlappingLessonsForTutor(int tutorID, String start, String end, int lessonID){
        String sql = "select * from lesson where tutor_id = ? and (start_date between ? and ? or ? between start_date and end_date) and lesson_id <> ?";
        System.out.println(sql);
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorID);
            stmt.setString(2, start);
            stmt.setString(3, end);
            stmt.setString(4, start);
            stmt.setInt(5, lessonID);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteLessons(int classID){
        String sql = "delete from lesson where class_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            
            int rows = stmt.executeUpdate();
            
            if(rows > 0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteLesson(int lessonID){
        String sql = "delete from lesson where lesson_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonID);
            
            int rows = stmt.executeUpdate();
            
            if(rows > 0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
