package model;

import connection.ConnectionManager;
import entity.Tutor;import entity.Tutor_HourlyRate_Rel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TutorDAO {

    public static Tutor retrieveSpecificTutor(String tutorName) {
        String select_tutor = "SELECT * FROM tutor WHERE tutor_fullname = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(select_tutor)) {
            preparedStatement.setString(1, tutorName);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String nric = rs.getString(2);
                String fullname = rs.getString(3);
                int phone = rs.getInt(4);
                String address = rs.getString(5);
                String qualification = rs.getString(6);
                String birth_date = rs.getString(7);
                String gender = rs.getString(8);
                String email = rs.getString(9);
                int branch_id = rs.getInt(10);
                //double pay = rs.getDouble(11);
                Tutor t = new Tutor(id, nric, fullname, phone, address, qualification, birth_date, gender, email, branch_id);
                return t;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static ArrayList<Tutor> retrieveTutorsShortInfoByBranch(int branchId) {
        ArrayList<Tutor> tutorLists = new ArrayList<>();
        String select_tutor = "SELECT tutor_id,tutor_fullname FROM tutor WHERE branch_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(select_tutor)) {
            preparedStatement.setInt(1, branchId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String fullname = rs.getString(2);
                Tutor t = new Tutor(id, fullname);
                tutorLists.add(t);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tutorLists;
    }

    public Tutor retrieveSpecificTutorById(int tutorId) {
        String select_tutor = "SELECT tutor_id, tutor_nric, tutor_fullname, phone, address,highest_qualification, "
                + "birth_date, gender, email, branch_id FROM tutor WHERE tutor_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(select_tutor)) {
            preparedStatement.setInt(1, tutorId);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String nric = rs.getString(2);
                String fullname = rs.getString(3);
                int phone = rs.getInt(4);
                String address = rs.getString(5);
                String qualification = rs.getString(6);
                System.out.println(qualification + " HECK YEAAA");
                String birth_date = rs.getString(7);
                String gender = rs.getString(8);
                String email = rs.getString(9);
                int branch_id = rs.getInt(10);
                //double pay = rs.getDouble(11);
                Tutor t = new Tutor(id, nric, fullname, phone, address, qualification, birth_date, gender, email, branch_id/*, pay*/);
                return t;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public int addTutor(Tutor tutor) {
        String insert_Tutor = "INSERT INTO tutor(tutor_nric,tutor_fullname,phone,address,highest_qualification,birth_date,gender,email,branch_id) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(insert_Tutor)) {
            preparedStatement.setString(1, tutor.getNric());
            preparedStatement.setString(2, tutor.getName());
            preparedStatement.setInt(3, tutor.getPhone());
            preparedStatement.setString(4, tutor.getAddress());
            preparedStatement.setString(5, tutor.getQualification());
            preparedStatement.setString(6, tutor.getBirth_date());
            preparedStatement.setString(7, tutor.getGender());
            preparedStatement.setString(8, tutor.getEmail());
            preparedStatement.setInt(9, tutor.getBranch_id());

            int num = preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            return generatedKey;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public boolean updateTutor(int tutorID, String nric, int phone, String address, String highest_qualification, String dob, String gender, String email) {
        String update_Tutor = "UPDATE tutor SET tutor_nric=?,phone=?,address=?,highest_qualification=?,birth_date=?,gender=?,email=? WHERE tutor_id =? ";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(update_Tutor)) {
            preparedStatement.setString(1, nric);
            preparedStatement.setInt(2, phone);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, highest_qualification);
            preparedStatement.setString(5, dob);
            preparedStatement.setString(6, gender);
            preparedStatement.setString(7, email);
            preparedStatement.setInt(8, tutorID);

            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public ArrayList<Object> uploadTutor(ArrayList<String> tutorLists, ArrayList<String> tutorEmailLists) {
        ArrayList<Object> returnList = new ArrayList<>();
        ArrayList<Tutor> duplicatedTutors = new ArrayList<>();
        ArrayList<Tutor> insertedTutor = new ArrayList<>();
        if (tutorEmailLists.size() > 0) {
            String emailList = "'" + String.join("','", tutorEmailLists) + "'";
            String select_tutor = "SELECT tutor_id,tutor_fullname FROM tutor WHERE email IN (" + emailList + ")";

            ArrayList<Tutor> existingTutors = new ArrayList();
            try (Connection conn = ConnectionManager.getConnection();
                    PreparedStatement preparedStatement = conn.prepareStatement(select_tutor)) {

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String tutor_fullname = rs.getString(2);
                    existingTutors.add(new Tutor(id, tutor_fullname));
                }

                System.out.println(existingTutors.size());

                String tutorList = String.join(",", tutorLists);
                String[] col = {"tutor_id"};
                String insert_tutor = "INSERT IGNORE INTO tutor(tutor_nric,tutor_fullname,phone,address,birth_date,gender,email,highest_qualification,branch_id) VALUES " + tutorList;
                PreparedStatement insertStatement = conn.prepareStatement(insert_tutor, col);
                insertStatement.executeUpdate();

                ResultSet a = insertStatement.getGeneratedKeys();

                while (a.next()) {
                    int id = a.getInt(1);
                    insertedTutor.add(retrieveSpecificTutorById(id));
                }
                duplicatedTutors = existingTutors;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        returnList.add(duplicatedTutors);
        returnList.add(insertedTutor);
        return returnList;
    }

    public ArrayList<Tutor> retrieveAllTutorsByBranch(int branchId) {
        ArrayList<Tutor> tutorLists = new ArrayList<>();
        String select_tutor = "SELECT * FROM tutor WHERE branch_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(select_tutor)) {
            preparedStatement.setInt(1, branchId);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nric = rs.getString(2);
                String fullname = rs.getString(3);
                int phone = rs.getInt(4);
                String address = rs.getString(5);
                String highest_qualification = rs.getString(6);
                String birth_date = rs.getString(7);
                String gender = rs.getString(8);
                String email = rs.getString(9);
                int branch_id = rs.getInt(10);
                //double pay = rs.getDouble(11);
                Tutor t = new Tutor(id, nric, fullname, phone, address, highest_qualification, birth_date, gender, email, branch_id);
                tutorLists.add(t);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tutorLists;
    }

    public ArrayList<Tutor> retrieveAllTutors() {
        ArrayList<Tutor> tutorLists = new ArrayList<>();
        String select_tutor = "SELECT * FROM tutor";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(select_tutor)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nric = rs.getString(2);
                String fullname = rs.getString(3);
                int phone = rs.getInt(4);
                String address = rs.getString(5);
                String highest_qualification = rs.getString(6);
                String birth_date = rs.getString(7);
                String gender = rs.getString(8);
                String email = rs.getString(9);
                int branch_id = rs.getInt(10);
                //double pay = rs.getDouble(11);
                Tutor t = new Tutor(id, nric, fullname, phone, address, highest_qualification, birth_date, gender, email, branch_id);
                tutorLists.add(t);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tutorLists;
    }

    public ArrayList<Tutor> retrieveAllTutorsByLimit(int startingRow, int limit, int branchId) {
        ArrayList<Tutor> tutorLists = new ArrayList<>();
        String select_tutor = "SELECT * FROM tutor where branch_id = ? Limit ?,?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(select_tutor)) {
            preparedStatement.setInt(1, branchId);
            preparedStatement.setInt(2, startingRow);
            preparedStatement.setInt(3, limit);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nric = rs.getString(2);
                String fullname = rs.getString(3);
                int phone = rs.getInt(4);
                String address = rs.getString(5);
                String highest_qualification = rs.getString(6);
                String birth_date = rs.getString(7);
                String gender = rs.getString(8);
                String email = rs.getString(9);
                int branch_id = rs.getInt(10);
                //double pay = rs.getDouble(11);
                Tutor t = new Tutor(id, nric, fullname, phone, address, highest_qualification, birth_date, gender, email, branch_id);
                tutorLists.add(t);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tutorLists;
    }

    public boolean deleteTutor(int tutorId) {
        String delete_sql = "DELETE FROM tutor WHERE tutor_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(delete_sql)) {
            preparedStatement.setInt(1, tutorId);
            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public Tutor retrieveTutorByEmail(String email) {
        Tutor tutor = null;
        String sql = "select * from tutor where email = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String nric = rs.getString(2);
                String fullname = rs.getString(3);
                int phone = rs.getInt(4);
                String address = rs.getString(5);
                String highest_qualification = rs.getString(6);
                String birth_date = rs.getString(7);
                String gender = rs.getString(8);
                String email1 = rs.getString(9);
                int branch_id = rs.getInt(10);
                //double pay = rs.getDouble(11);
                Tutor t = new Tutor(id, nric, fullname, phone, address, highest_qualification, birth_date, gender, email1, branch_id);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return tutor;
    }

    public int retrieveNumberOfTutor() {
        int tutorCount = 0;
        String sql = "select COUNT(*) from tutor";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tutorCount = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return tutorCount;
    }

    public int retrieveNumberOfTutorByBranch(int branchId) {
        int tutorCount = 0;
        String sql = "select COUNT(*) from tutor where branch_id = ?";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tutorCount = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return tutorCount;
    }

    public boolean updateTutorPassword(int tutorID, String password) {
        String updateTutorPassword = "update users set users.password = MD5(?) where role = 'tutor' and user_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(updateTutorPassword)) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, tutorID);

            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public static int calculateLessonCount(int tutorID, int classID) {
        int count = 0;
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select count(*) from lesson where tutor_id = ? and class_id = ? and tutor_payment_status = 0 start_date < CURDATE()")) {
            stmt.setInt(1, tutorID);
            stmt.setInt(2, classID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (SQLException ex) {
            ex.printStackTrace();;
        }
        return count;
    }

    public static boolean updateTutorPayment(int tutorID, int classID) {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE table lesson set tutor_payment_status = 1 where tutor_id = ? and class_id = ?")) {
            stmt.setInt(1, tutorID);
            stmt.setInt(2, classID);

            stmt.executeQuery();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();;
        }
        return false;
    }
    
    
    public static ArrayList<Tutor_HourlyRate_Rel> tutorSubjectListsForSpecificLevel(int level_id,int branch_id){
        ArrayList<Tutor_HourlyRate_Rel> tutorSubLists = new ArrayList<>();
        String sql = "SELECT tutor_fullname,tutor.tutor_id,subject_id,hourly_pay FROM tutor_hourly_rate,tutor WHERE "
                + "tutor.tutor_id = tutor_hourly_rate.tutor_id AND level_id = ? AND tutor.branch_id =? ORDER BY subject_id";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,level_id);
            stmt.setInt(2, branch_id);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                String fullname = rs.getString(1);
                int tutor_id = rs.getInt(2);
                int subject_id = rs.getInt(3);
                double hourlyFee = rs.getDouble(4);
                String subjectName = SubjectDAO.retrieveSubject(subject_id);
                tutorSubLists.add(new Tutor_HourlyRate_Rel(tutor_id, fullname, subject_id, hourlyFee,level_id,subjectName));
            }
       
        }   catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tutorSubLists;
    }
    
    public static ArrayList<Tutor_HourlyRate_Rel> tutorSubjectListsForSpecificBranch(int branch_id){
        ArrayList<Tutor_HourlyRate_Rel> tutorSubLists = new ArrayList<>();
        String sql = "SELECT tutor_fullname,tutor.tutor_id,subject_id,hourly_pay,level_id FROM tutor_hourly_rate,tutor WHERE "
                + "tutor.tutor_id = tutor_hourly_rate.tutor_id AND tutor.branch_id =? ORDER BY subject_id";
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,branch_id);

            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                String fullname = rs.getString(1);
                int tutor_id = rs.getInt(2);
                int subject_id = rs.getInt(3);
                double hourlyFee = rs.getDouble(4);
                int level_id = rs.getInt(5);
                String subject_name = SubjectDAO.retrieveSubject(subject_id);
                tutorSubLists.add(new Tutor_HourlyRate_Rel(tutor_id, fullname, subject_id, hourlyFee,level_id,subject_name));
            }
       
        }   catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tutorSubLists;
    }

    public static double getHourlyPay(int tutorID, int levelID, int subjectID) {
        double pay = 0;
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select hourly_pay from tutor_hourly_rate where tutor_id = ? and level_id = ? and subject_id = ?")) {
            stmt.setInt(1, tutorID);
            stmt.setInt(2, levelID);
            stmt.setInt(3, subjectID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pay = rs.getDouble(1);
            }  
            return pay;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pay;

    }
}
