package model;

import entity.Class;
import connection.ConnectionManager;
import entity.Student;
import entity.TutorPay;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassDAO {

    public static HashMap<String, ArrayList<Class>> getClassesToEnrolled(int level_id, int student_id, int branch_id) {
        String level = LevelDAO.retrieveLevel(level_id);
        HashMap<String, ArrayList<Class>> classList = new HashMap<>();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and level_id = ? and combined = 0 and end_date > curdate() and "
                    + "class_id not in (select class_id from class_student_rel where student_id = ? and status = 0) order by subject_id");
            stmt.setInt(1, branch_id);
            stmt.setInt(2, level_id);
            stmt.setInt(3, student_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                int term = rs.getInt("term");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String type = rs.getString("class_type");
                Class cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);
                ArrayList<Class> classArray = new ArrayList<>();
                if (classList.containsKey(type)) {  
                    classArray = classList.get(type);  
                    classArray.add(cls);
                } else {
                    classArray.add(cls); 
                }
                classList.put(type, classArray);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }

    public static Class getClassByID(int classID) {
        Class cls = null;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select level_id, subject_id, term, start_time, end_time, class_day, fees, start_date, end_date, class_type, has_reminder_for_fees, additional_lesson_id, tutor_id from class where class_id = ?");
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int levelID = rs.getInt(1);
                int subjectID = rs.getInt(2);
                System.out.println("SUBJECT " + subjectID);
                int term = rs.getInt(3);
                String startTime = rs.getString(4);
                String endTime = rs.getString(5);
                String classDay = rs.getString(6);
                String startDate = rs.getString(8);
                String endDate = rs.getString(9);
                int mthlyFees = rs.getInt(7);
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                String type = rs.getString(10);
                String combined = rs.getString("additional_lesson_id");
                int tutorID = rs.getInt("tutor_id");
                cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);
                cls.setHasReminderForFees(rs.getInt(11));
                cls.setSubjectID(subjectID);
                cls.setTutorID(tutorID);
                System.out.println(cls.getSubject());
                
                if(combined != null){
                    String[] combinedLevels = combined.split(",");
                    combined = "";
                    combined += LevelDAO.retrieveLevel(Integer.parseInt(combinedLevels[0]));
                    
                    for(int i = 1; i < combinedLevels.length; i++){
                        int levelAdd = Integer.parseInt(combinedLevels[i]);
                        combined += ", " +LevelDAO.retrieveLevel(levelAdd);
                    }
                    cls.setCombined(combined);
                }
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return cls;
    }

    public static ArrayList<Class> getStudentEnrolledClass(int student_id) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class c, class_student_rel cs where c.class_id = cs.class_id and "
                    + "end_date > curdate() and student_id = ? and status = 0");
            stmt.setInt(1, student_id);
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
                String combinedLevel = rs.getString("additional_lesson_id");
                Class cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type, combinedLevel);
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }
    
    public static ArrayList<Class> getAllCombinedClass(int branchID, int studentID, int levelID) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and combined = 1 "
                    + "and class_id not in (select class_id from class_student_rel where student_id = ?) order by subject_id;");
            stmt.setInt(1, branchID);
            stmt.setInt(2, studentID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                int term = rs.getInt("term");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String level = rs.getString("additional_lesson_id").trim();
                level = level.replace("\u0000","");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String type = rs.getString("class_type");
                String levelStr = String.valueOf(levelID);
                List<String> levelIDs = Arrays.asList(level.split(","));
                //System.out.println("Combined" + level);
                for(String level_id: levelIDs){
                    if(level_id.equals(levelStr)){
                        Class cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);
                        classList.add(cls);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }
    
    public static ArrayList<Class> getCombinedClassesByLevel(int branchID, int levelID) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and combined = 1 "
                    + "and (year(start_date) = year(curdate()) or start_date > curdate());");
            stmt.setInt(1, branchID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                int term = rs.getInt("term");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String level = rs.getString("additional_lesson_id").trim();
                level = level.replace("\u0000","");
                String subject = SubjectDAO.retrieveSubject(subjectID) + " Combined ";
                String type = rs.getString("class_type");
                String levelStr = String.valueOf(levelID);
                List<String> levelIDs = Arrays.asList(level.split(","));
                // System.out.println("Combined" + level);
                for(String level_id: levelIDs){
                    if(level_id.equals(levelStr)){
                        Class cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);
                        classList.add(cls);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }
    
    public static void getClassesByLevel(int levelID, int branchID, ArrayList<Class> combinedCls) {
        try (Connection conn = ConnectionManager.getConnection()) {
            String select_class_sql = "select * from class where branch_id = ? and level_id = ? and combined = 0 "
                    + "and (year(start_date) = year(curdate()) or start_date > curdate());";
            PreparedStatement stmt = conn.prepareStatement(select_class_sql);
            stmt.setInt(1, branchID);
            stmt.setInt(2, levelID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
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
                Class cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);              
                combinedCls.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }
    
    public static double retrieveStudentFees(int studentID, int classID) {
        double studentFees = 0;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select amount_charged from payment_reminder where student_id = ? and class_id = ? "
                    + "and amount_charged = outstanding_charge order by payment_due_date limit 1;");
            stmt.setInt(1, studentID);
            stmt.setInt(2, classID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                studentFees = rs.getDouble("amount_charged");
            }
        } catch (SQLException e) {
            System.out.print("Error in retrieveStudentFees method?" + e.getMessage());
        }
        return studentFees;
    }

    public static ArrayList<Class> listAllClasses(int branchID) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and year(end_date) >= year(curdate())");
            stmt.setInt(1, branchID);
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
                int tutor = rs.getInt("tutor_id");
                Class cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);
                cls.setTutorID(tutor);
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }
    
    
    /* LIST THE CURRENT CLASSES FOR THAT BRANCH WITH THE COST */
    public static ArrayList<Class> listAllClassesForSpecificBranchWithCost(int branchID){
        ArrayList<Class> classList = new ArrayList();
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and end_date > curdate()");
            stmt.setInt(1, branchID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                int levelID = rs.getInt("level_id");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                double mthlyFees = rs.getDouble("fees");
                String level = LevelDAO.retrieveLevel(levelID);
                String holidayDate = rs.getString("holiday_date");
                int tutorID =rs.getInt("tutor_id");
                Class cls = new Class(classID, level, subjectID, startTime, endTime, classDay, mthlyFees, startDate, endDate, holidayDate,tutorID);
                classList.add(cls);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }       
        return classList;
    }
    
    /* LIST THE CURRENT CLASSES FOR THAT BRANCH WITH THE COST */
    public static ArrayList<Class> listAllClassesForSpecificLevelWithCost(int branchID,int levelID){
        ArrayList<Class> classList = new ArrayList();
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and level_id = ? and end_date > curdate()");
            stmt.setInt(1, branchID);
            stmt.setInt(2, levelID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                int level_id = rs.getInt("level_id");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                double mthlyFees = rs.getDouble("fees");
                String level = LevelDAO.retrieveLevel(level_id);
                String holidayDate = rs.getString("holiday_date");
                int tutorID =rs.getInt("tutor_id");
                Class cls = new Class(classID, level, subjectID, startTime, endTime, classDay, mthlyFees, startDate, endDate, holidayDate,tutorID);
                classList.add(cls);
            }
        }catch(SQLException e){
            System.out.print(e.getMessage());
        }       
        return classList;
    }


    public static ArrayList<Class> listAllClassesByTutorID(int tutorID, int branchID) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and tutor_id = ? and DATE_ADD(end_date, INTERVAL 6 MONTH) >= curdate()");
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
                Class cls = new Class(classID,levelID,level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }

    public static ArrayList<Class> retrieveAllClassesOfTutor(int tutorID, int branchID) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and tutor_id = ? and end_date > curdate()");
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
                Class cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }

    public static ArrayList<Class> listAllClassesBelongToTutorByDay(int tutorID, int branchID, String day) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            String sql = "select * from class where branch_id = ? and tutor_id = ? and class_day = ? and end_date > curdate()";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, branchID);
            stmt.setInt(2, tutorID);
            stmt.setString(3, day);
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
                Class cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }

    public int insertClass(int level, int subject, int term, int hasReminderForFees, int branch, String startTime, String endTime, String classDay, double mthlyFees, String startDate, String endDate) {
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "INSERT into class (level_id, subject_id, term, fees, has_reminder_for_fees, start_time, end_time, class_day, start_date, end_date, branch_id)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, level);
            stmt.setInt(2, subject);
            stmt.setInt(3, term);
            stmt.setDouble(4, mthlyFees);
            stmt.setInt(5, hasReminderForFees);
            stmt.setString(6, startTime);
            stmt.setString(7, endTime);
            stmt.setString(8, classDay);
            stmt.setString(9, startDate);
            stmt.setString(10, endDate);
            stmt.setInt(11, branch);
            stmt.executeUpdate();
            conn.commit();
            ResultSet rs = stmt.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            return generatedKey;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public static int createClass(String classType,int level, int subject,double mthlyFees, int hasReminderForFees, String startTime, String endTime, String classDay, String startDate, String endDate, int branch, int tutorId, String holidays, String additionalLevels, boolean combined) {
        try (Connection conn = ConnectionManager.getConnection();) {
            String sql = "INSERT into class (level_id, subject_id,fees,has_reminder_for_fees,start_time, end_time, class_day, start_date, end_date,branch_id,tutor_id, class_type, holiday_date, additional_lesson_id, combined)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            //System.out.println(sql);
            stmt.setInt(1, level);
            stmt.setInt(2, subject);
            stmt.setDouble(3, mthlyFees);
            stmt.setInt(4, hasReminderForFees);
            stmt.setString(5, startTime);
            stmt.setString(6, endTime);
            stmt.setString(7, classDay);
            stmt.setString(8, startDate);
            stmt.setString(9, endDate);
            stmt.setInt(10, branch);
            stmt.setInt(11, tutorId);
            stmt.setString(12, classType);
            stmt.setString(13, holidays);
            stmt.setString(14, additionalLevels);
            stmt.setBoolean(15, combined);
            
            int rows = stmt.executeUpdate();
            
            if(rows > 0){
                sql = "select last_insert_id()";
                stmt = conn.prepareStatement(sql);
                
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public boolean updateClass(int tutorID, String startTime, String endTime, String startDate, String endDate, int classID) {
        String sql = "update class set start_time = ?, end_time = ?, start_date = ?, end_date = ?, tutor_id = ? where class_id = ?";
        //System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, startTime);
            stmt.setString(2, endTime);
            stmt.setString(3, startDate);
            stmt.setString(4, endDate);
            stmt.setInt(5, tutorID);
            stmt.setInt(6, classID);
            //System.out.println(stmt);

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    public static ArrayList<Class> getClassesByLevel(int levelID, int branchID) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            String select_class_sql = "select * from class where branch_id = ? and (level_id = ? or find_in_set(?, additional_lesson_id));";
            PreparedStatement stmt = conn.prepareStatement(select_class_sql);
            stmt.setInt(1, branchID);
            stmt.setInt(2, levelID);
            stmt.setInt(3, levelID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
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
                Class cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);
                
                String combined = rs.getString("additional_lesson_id");
                
                if(combined != null){
                    String[] combinedLevels = combined.split(",");
                    combined = "";
                    
                    for(String s: combinedLevels){
                        int levelAdd = Integer.parseInt(s);
                        combined += LevelDAO.retrieveLevel(levelAdd) + " ";
                    }
                    
                    combined = combined.trim();
                    combined = combined.replace(" ", ",");
                    cls.setCombined(combined);
                }
                
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }

    public static ArrayList<Class> getClassesByTermAndLevel(int level_id, int term, int branch_id, String level) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            String select_class_sql = "select * from class where branch_id = ? and level_id = ? and term = ? and end_date > curdate() order by subject_id";
            PreparedStatement stmt = conn.prepareStatement(select_class_sql);
            stmt.setInt(1, branch_id);
            stmt.setInt(2, level_id);
            stmt.setInt(3, term);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String type = rs.getString("class_type");
                Class cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }

    public static ArrayList<Class> getClassesByTermAndLevelAndYear(int level_id, int term, int branch_id, String level, int year) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            String select_class_sql = "select * from class where branch_id = ? and level_id = ? and term = ? and year = ? and end_date > curdate() order by subject_id";
            PreparedStatement stmt = conn.prepareStatement(select_class_sql);
            stmt.setInt(1, branch_id);
            stmt.setInt(2, level_id);
            stmt.setInt(3, term);
            stmt.setInt(4, year);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                int tutorID = rs.getInt("tutor_id");
                String type = rs.getString("class_type");
                Class cls = new Class(classID, subjectID, startTime, endTime, classDay, mthlyFees, startDate, endDate, tutorID, type);
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }

    public static Map<String, ArrayList<Class>> groupClassesByTimingAndDay(int level_id, int term, int branch_id, String level) {
        Map<String, ArrayList<Class>> classMap = new HashMap();
        try (Connection conn = ConnectionManager.getConnection()) {
            String select_class_sql = "select * from class where branch_id = ? and level_id = ? and term = ? and end_date > curdate() order by timing desc";
            PreparedStatement stmt = conn.prepareStatement(select_class_sql);
            stmt.setInt(1, branch_id);
            stmt.setInt(2, level_id);
            stmt.setInt(3, term);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String type = rs.getString("class_type");
                Class cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);

                String key = startTime + "-" + endTime + "-" + classDay;
                ArrayList<Class> tempClassLists = classMap.get(key);
                if (tempClassLists == null) {
                    tempClassLists = new ArrayList<>();
                    classMap.put(key, tempClassLists);
                }
                tempClassLists.add(cls);

            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classMap;
    }

    public ArrayList<Student> retrieveStudentsByClass(int classID) {
        ArrayList<Student> result = new ArrayList<>();
        String sql = "select student_id, student_name, phone, level_id from student where student_id in (select student_id from class_student_rel where class_id = ? and status = 0)";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String level = LevelDAO.retrieveLevel(rs.getInt(4));
                //System.out.println(rs.getString(3));
                result.add(new Student(rs.getInt(1), rs.getString(2), rs.getInt(3), level));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static double getClassTime(int classID) {
        double duration = 0;
        try (Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select start_date, end_date from lesson where class_id = ? limit 0,1")) {
            stmt.setInt(1, classID);

            String sTime = "";
            String eTime = "";
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sTime = rs.getString(1);
                eTime = rs.getString(2);
            }

            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
                Date start = formatter.parse(sTime);
                Date end = formatter.parse(eTime);
                duration = (double) (end.getTime() - start.getTime()) /1000 /60 /60;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return duration;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return duration;
    }
    
    public static String retrieveClassLevelSubject(int classID) {
        String lvlSubject = "";
        try (Connection conn = ConnectionManager.getConnection()) {
            String select_class_sql = "select level_id, subject_id from class where class_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(select_class_sql);
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int levelID = rs.getInt("level_id");
                int subjectID = rs.getInt("subject_id");
                String level = LevelDAO.retrieveLevel(levelID);
                String subject = SubjectDAO.retrieveSubject(subjectID);
                lvlSubject = level + " " + subject;
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return lvlSubject;
    }
    
    public boolean retrieveOverlappingClassesForTutor(int tutorID, String start, String end, int classID, int day){
        String sql = "select * from class where tutor_id = ? and dayofweek(start_date) = ? and (start_time between ? and ? or ? between start_time and end_time) and class_id <> ?";
        //System.out.println(sql);
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorID);
            stmt.setInt(2, day);
            stmt.setString(3, start);
            stmt.setString(4, end);
            stmt.setString(5, start);
            stmt.setInt(6, classID);
            //System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteClass(int classID){
        String sql = "delete from class where class_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            
            int rows = stmt.executeUpdate();
            
            if(rows > 0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteClassStudentRel(int classID){
        String sql = "delete from class_student_rel where class_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            
            int rows = stmt.executeUpdate();
            
            if(rows > 0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
        public static HashMap retrieveClassTypes(int branch_id){
        HashMap<Integer, String> classTypes = new HashMap<>();
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("select class_id, class_type from class where branch_id = ?");
            stmt.setInt(1, branch_id);
 
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                classTypes.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return classTypes;
    }
        
    public ArrayList<String> retrieveReplacementDates(int lessonID){
        ArrayList<String> replacementDates = new ArrayList<>();
        String sql = "select changed_start_date, changed_end_date from lesson where lesson_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, lessonID);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                replacementDates.add(rs.getString(1));
                replacementDates.add(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return replacementDates;
    }
    
    
    //For Tutor Payment
    public static ArrayList<Class> listAllClassesBelongToTutors(int tutorID, int branchID) {
        ArrayList<Class> classList = new ArrayList();
        String sql = "select r.hourly_pay,c.class_day,c.class_id,c.level_id,c.class_type,c.subject_id,c.end_time,c.start_time,"
                + "c.additional_lesson_id,c.combined,r.pay_type FROM class as c,tutor_hourly_rate as r"
                + " WHERE c.tutor_id = ? AND c.branch_id=? AND r.subject_id=c.subject_id AND"
                + " r.tutor_id = c.tutor_id AND r.level_id=c.level_id AND r.additonal_level_id=c.additional_lesson_id AND"
                + " r.combined_class=c.combined AND"
                + " DATE_ADD(end_date, INTERVAL 3 MONTH) > curdate() ORDER BY combined";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorID);
            stmt.setInt(2, branchID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double pay = rs.getDouble("hourly_pay");
                String day = rs.getString("class_day");
                int classId = rs.getInt("class_id");
                String class_type = rs.getString("class_type");
                int subject_id = rs.getInt("subject_id");
                String end_time = rs.getString("end_time");
                String start_time = rs.getString("start_time");
                String additionalLvl = "";
                int combine = rs.getInt("combined");
                int pay_type = rs.getInt("pay_type");
                if(combine == 1){
                    String [] lvlIds = rs.getString("additional_lesson_id").split(",");
                    for(String lvlId:lvlIds){
                        int lvlIdInt = Integer.parseInt(lvlId);
                        if(lvlIdInt < 7){
                            additionalLvl += "P "+lvlId+",";
                        }else{
                            additionalLvl += "S "+(lvlIdInt-6)+",";
                        }
                    }
                }else{
                    int level_id = rs.getInt("level_id");
                    if(level_id < 7){
                        additionalLvl += "P "+level_id+",";
                    }else{
                        additionalLvl += "S "+(level_id-6)+",";
                    }
                }
                
                additionalLvl = additionalLvl.substring(0, additionalLvl.length() - 1);
                String subjectName = SubjectDAO.retrieveSubject(subject_id);
                String className = start_time+"-"+end_time+"("+day+")"+"<br/>"+additionalLvl+"["+subjectName+"]";
                Class c = new Class(classId,className,pay,additionalLvl,subjectName,pay_type);
                classList.add(c);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }
    
    public static ArrayList<TutorPay>totalReplacementClasses(int tutorID,int branchID){
        ArrayList<TutorPay> tutorReplacementPayList = new ArrayList<>();
        String sql = "SELECT class_id, count(*) as total_lessons FROM lesson WHERE replacement_tutor_id = ? "
                + "AND tutor_payment_status = 0 AND tutor_attended = 1  GROUP BY class_id";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorID);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Integer>classIds = new ArrayList<>();
            HashMap<Integer,Integer> totalLessonMaps = new HashMap<>();
            while (rs.next()) {
                int classId = rs.getInt(1);
                int totalLessons = rs.getInt(2);
                classIds.add(classId);
                totalLessonMaps.put(classId, totalLessons);
            }
            
            if(classIds.size() > 0){
                String classStrIds =  classIds.toString();
                String classIdLists = String.join("','",classStrIds.substring(1,classStrIds.length()-1));
                sql = "SELECT c.class_id,c.level_id,c.subject_id,sub.subject_name,c.class_day,c.additional_lesson_id,"
                        + "c.combined,c.end_time,c.start_time,t.hourly_pay FROM "
                        + "class as c,subject as sub,tutor_hourly_rate as t WHERE "
                        + "c.class_id IN("+classIdLists+") AND c.branch_id = ? AND "
                        + "c.subject_id = sub.subject_id AND c.level_id = t.level_id AND"
                        + " c.subject_id = t.subject_id AND c.additional_lesson_id = t.additonal_level_id AND "
                        + "c.combined = t.combined_class AND t.tutor_id = ?";

                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, branchID);
                stmt.setInt(2,tutorID);

                ResultSet rs2 = stmt.executeQuery();
                while(rs2.next()){
                    int classId = rs2.getInt("class_id");
                    int levelId = rs2.getInt("level_id");
                    int subjectId = rs2.getInt("subject_id");
                    String subjectName = rs2.getString("subject_name");
                    String classDay = rs2.getString("class_day");
                    int combined = rs2.getInt("combined");
                    Time startTime = rs2.getTime("start_time");
                    Time endTime = rs2.getTime("end_time");
                    double pay = rs2.getDouble("hourly_pay");
                    double duration = (double) (endTime.getTime() - startTime.getTime()) /1000 /60 /60;
                 
                    String additionalLvl = "";
                    if(combined == 1){
                        String [] lvlIds = rs2.getString("additional_lesson_id").split(":");
                        for(String lvlId:lvlIds){
                            int lvlIdInt = Integer.parseInt(lvlId);
                            if(lvlIdInt < 7){
                                additionalLvl += "P "+lvlId+",";
                            }else{
                                additionalLvl += "S "+(lvlIdInt-6)+",";
                            }
                        }
                    }else{
                        int level_id = rs2.getInt("level_id");
                        if(level_id < 7){
                            additionalLvl += "P "+level_id+",";
                        }else{
                            additionalLvl += "S "+(level_id-6)+",";
                        }
                    }
                    
                    additionalLvl = additionalLvl.substring(0, additionalLvl.length() - 1);
                    int totalLessonsPerClass = totalLessonMaps.get(classId);
                    String className = startTime+"-"+endTime+"("+classDay+")"+"<br/>"+additionalLvl+"["+subjectName+"]";
                    double totalAmountByClass = totalLessonsPerClass*duration*pay;
                    TutorPay tutorPay = new TutorPay(classId, tutorID, className, totalLessonsPerClass, totalAmountByClass,subjectName,additionalLvl);
                    tutorReplacementPayList.add(tutorPay);
                }
                
            }
            
            
        }catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return tutorReplacementPayList;
    }

    public boolean checkForStudents(int classID){
        String sql = "select * from class_student_rel where class_id = ?";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public static boolean checkForBulkRegistrationType(int classID){
        String sql = "select * from class where class_id = ? and start_date < curdate();";
        
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);
            
            ResultSet rs = stmt.executeQuery();
            //System.out.println(rs);
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public static ArrayList<Class> getStudentEnrolledClass(int student_id, int branch_id) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class c, class_student_rel cs where c.class_id = cs.class_id and "
                    + "end_date > curdate() and student_id = ? and c.branch_id = ? and status = 0");
            stmt.setInt(1, student_id);
            stmt.setInt(2, branch_id);
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
                String combinedLevel = rs.getString("additional_lesson_id");
                Class cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type, combinedLevel);
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }
}
