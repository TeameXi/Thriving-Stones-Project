package model;

import entity.Class;
import connection.ConnectionManager;
import entity.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                    + "class_id not in (select class_id from class_student_rel where student_id = ?) order by subject_id");
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
            PreparedStatement stmt = conn.prepareStatement("select level_id, subject_id, term, start_time, end_time, class_day, fees, start_date, end_date, class_type, has_reminder_for_fees, additional_lesson_id from class where class_id = ?");
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int levelID = rs.getInt(1);
                int subjectID = rs.getInt(2);
                int term = rs.getInt(3);
                String startTime = rs.getString(4);
                String endTime = rs.getString(5);
                String classDay = rs.getString(6);
                String startDate = rs.getString(8);
                String endDate = rs.getString(9);
                System.out.println(startDate);
                int mthlyFees = rs.getInt(7);
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                String type = rs.getString(10);
                String combined = rs.getString("additional_lesson_id");
                cls = new Class(classID, level, subject, term, startTime, endTime, classDay, mthlyFees, startDate, endDate, type);
                cls.setHasReminderForFees(rs.getInt(11));
                cls.setSubjectID(subjectID);
                
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
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return cls;
    }

    public static ArrayList<Class> getStudentEnrolledClass(int student_id) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class c, class_student_rel cs where c.class_id = cs.class_id and end_date > curdate() and student_id = ?");
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
                String combinedLevel = rs.getString("combined_levels");
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
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = 1 and combined = 1 "
                    + "and class_id not in (select class_id from class_student_rel where student_id = ?) order by subject_id;");
            stmt.setInt(1, studentID);
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
                System.out.println("Combined" + level);
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

    public static ArrayList<Class> listAllClasses(int branchID) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and year(end_date) = year(curdate())");
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
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and tutor_id = ? and DATE_ADD(end_date, INTERVAL 3 MONTH) > curdate()");
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

            System.out.println(sql);
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
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, startTime);
            stmt.setString(2, endTime);
            stmt.setString(3, startDate);
            stmt.setString(4, endDate);
            stmt.setInt(5, tutorID);
            stmt.setInt(6, classID);
            System.out.println(stmt);

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
        String sql = "select student_id, student_name, phone, level_id from student where student_id in (select student_id from class_student_rel where class_id = ?)";

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String level = LevelDAO.retrieveLevel(rs.getInt(4));
                System.out.println(rs.getString(3));
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
        System.out.println(sql);
        try(Connection conn = ConnectionManager.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tutorID);
            stmt.setInt(2, day);
            stmt.setString(3, start);
            stmt.setString(4, end);
            stmt.setString(5, start);
            stmt.setInt(6, classID);
            System.out.println(stmt);
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
}
