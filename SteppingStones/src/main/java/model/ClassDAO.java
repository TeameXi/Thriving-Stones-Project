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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassDAO {

    public static ArrayList<Class> getClassesToEnrolled(int level_id, int student_id, int branch_id) {
        String level = LevelDAO.retrieveLevel(level_id);
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and level_id = ? and end_date > curdate() and "
                    + "class_id not in (select class_id from class_student_rel where student_id = ?) order by subject_id");
            stmt.setInt(1, branch_id);
            stmt.setInt(2, level_id);
            stmt.setInt(3, student_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                int term = rs.getInt("term");
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                Class cls = new Class(classID, level, subject, term, classTime, classDay, mthlyFees, startDate, endDate);
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }

    public static Class getClassByID(int classID) {
        Class cls = null;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class where class_id = ?");
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int levelID = rs.getInt("level_id");
                int subjectID = rs.getInt("subject_id");
                int term = rs.getInt("term");
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                cls = new Class(classID, level, subject, term, classTime, classDay, mthlyFees, startDate, endDate);
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
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                Class cls = new Class(classID, level, subject, term, classTime, classDay, mthlyFees, startDate, endDate);
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }

    public static ArrayList<Class> listAllClasses(int branchID) {
        ArrayList<Class> classList = new ArrayList();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from class where branch_id = ? and end_date > curdate()");
            stmt.setInt(1, branchID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int classID = rs.getInt("class_id");
                int subjectID = rs.getInt("subject_id");
                int levelID = rs.getInt("level_id");
                int term = rs.getInt("term");
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                Class cls = new Class(classID, level, subject, term, classTime, classDay, mthlyFees, startDate, endDate);
                classList.add(cls);
            }
        } catch (SQLException e) {
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
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                Class cls = new Class(classID, level, subject, term, classTime, classDay, mthlyFees, startDate, endDate);
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
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                Class cls = new Class(classID, level, subject, term, classTime, classDay, mthlyFees, startDate, endDate);
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
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                String level = LevelDAO.retrieveLevel(levelID);
                Class cls = new Class(classID, level, subject, term, classTime, classDay, mthlyFees, startDate, endDate);
                classList.add(cls);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return classList;
    }

    public int insertClass(int level, int subject, int term, int hasReminderForFees, int branch, String classTime, String classDay, double mthlyFees, String startDate, String endDate) {
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "INSERT into CLASS (level_id, subject_id, term, fees, has_reminder_for_fees, timing, class_day, start_date, end_date, branch_id)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, level);
            stmt.setInt(2, subject);
            stmt.setInt(3, term);
            stmt.setDouble(4, mthlyFees);
            stmt.setInt(5, hasReminderForFees);
            stmt.setString(6, classTime);
            stmt.setString(7, classDay);
            stmt.setString(8, startDate);
            stmt.setString(9, endDate);
            stmt.setInt(10, branch);
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

    public static int createClass(int level, int subject, int term, int year, double mthlyFees, int hasReminderForFees, String classTime, String classDay, String startDate, String endDate, int lessonNo, int branch, int tutorId) {
        try (Connection conn = ConnectionManager.getConnection();) {
            conn.setAutoCommit(false);
            String sql = "INSERT into CLASS (level_id, subject_id,term,year,fees,has_reminder_for_fees,timing, class_day, start_date, end_date,lesson_number,branch_id,tutor_id)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, level);
            stmt.setInt(2, subject);
            stmt.setInt(3, term);
            stmt.setInt(4, year);
            stmt.setDouble(5, mthlyFees);
            stmt.setInt(6, hasReminderForFees);
            stmt.setString(7, classTime);
            stmt.setString(8, classDay);
            stmt.setString(9, startDate);
            stmt.setString(10, endDate);
            stmt.setInt(11, lessonNo);
            stmt.setInt(12, branch);
            stmt.setInt(13, tutorId);
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

    public boolean updateClass(String level, String subject, String timing) {
        String sql = "update class set timing = ? where level_id = ? and subject_id = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, timing);
            stmt.setInt(2, Integer.parseInt(level));
            stmt.setInt(3, Integer.parseInt(subject));
            System.out.println(stmt);

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
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
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                Class cls = new Class(classID, level, subject, term, classTime, classDay, mthlyFees, startDate, endDate);
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
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                int tutorID = rs.getInt("tutor_id");
                Class cls = new Class(classID, subjectID, classTime, classDay, mthlyFees, startDate, endDate, tutorID);
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
                String classTime = rs.getString("timing");
                String classDay = rs.getString("class_day");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                int mthlyFees = rs.getInt("fees");
                String subject = SubjectDAO.retrieveSubject(subjectID);
                Class cls = new Class(classID, level, subject, term, classTime, classDay, mthlyFees, startDate, endDate);

                String key = classTime + "-" + classDay;
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
}
