package model;

import connection.ConnectionManager;
import entity.Tutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TutorDAO {

    public Tutor retrieveSpecificTutor(String tutorName) {
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
                String image_url = rs.getString(6);
                String birth_date = rs.getString(7);
                String gender = rs.getString(8);
                String email = rs.getString(9);
                String password = rs.getString(10);
                int branch_id = rs.getInt(11);
                Tutor t = new Tutor(id, nric, fullname, phone, address, image_url, birth_date, gender, email, password, branch_id);
                return t;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Tutor retrieveSpecificTutorById(int tutorId) {
        String select_tutor = "SELECT * FROM tutor WHERE tutor_id = ?";
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
                String image_url = rs.getString(6);
                String birth_date = rs.getString(7);
                String gender = rs.getString(8);
                String email = rs.getString(9);
                String password = rs.getString(10);
                int branch_id = rs.getInt(11);
                Tutor t = new Tutor(id, nric, fullname, phone, address, image_url, birth_date, gender, email, password, branch_id);
                return t;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean addTutor(Tutor tutor) {
        String insert_Tutor = "INSERT INTO tutor(tutor_nric,tutor_fullname,phone,address,image_url,birth_date,gender,email,password,branch_id) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(insert_Tutor)) {
            preparedStatement.setString(1, tutor.getNric());
            preparedStatement.setString(2, tutor.getName());
            preparedStatement.setInt(3, tutor.getPhone());
            preparedStatement.setString(4, tutor.getAddress());
            preparedStatement.setString(5, tutor.getImage_url());
            preparedStatement.setString(6, tutor.getBirth_date());
            preparedStatement.setString(7, tutor.getGender());
            preparedStatement.setString(8, tutor.getEmail());
            preparedStatement.setString(9, tutor.getPassword());
            preparedStatement.setInt(10, tutor.getBranch_id());

            int num = preparedStatement.executeUpdate();
            if (num != 0) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public ArrayList<String> uploadTutor(ArrayList<String> tutorLists, ArrayList<String> tutorNameLists) {
        ArrayList<String> duplicatedTutors = new ArrayList<>();
        if (tutorNameLists.size() > 0) {
            String nameList = "'" + String.join("','", tutorNameLists) + "'";
            String select_tutor = "SELECT tutor_id,tutor_fullname FROM tutor WHERE external_user_id IN (" + nameList + ")";

            ArrayList<String> existingTutors = new ArrayList();
            try (Connection conn = ConnectionManager.getConnection();
                    PreparedStatement preparedStatement = conn.prepareStatement(select_tutor)) {

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String tutor_fullname = rs.getString(2);
                    existingTutors.add(tutor_fullname);
                }

                String tutorList = String.join(",", tutorLists);
                String insert_tutor = "INSERT IGNORE INTO tutor(tutor_nric,tutor_fullname,phone,address,image_url,birth_date,gender,email,password,branch_id) VALUES " + tutorList;
                PreparedStatement insertStatement = conn.prepareStatement(insert_tutor);
                int num = insertStatement.executeUpdate();
                if (num != tutorNameLists.size()) {
                    duplicatedTutors = existingTutors;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return duplicatedTutors;
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
                String image_url = rs.getString(6);
                String birth_date = rs.getString(7);
                String gender = rs.getString(8);
                String email = rs.getString(9);
                String password = rs.getString(10);
                int branch_id = rs.getInt(11);
                Tutor t = new Tutor(id, nric, fullname, phone, address, image_url, birth_date, gender, email, password, branch_id);
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
                String image_url = rs.getString(6);
                String birth_date = rs.getString(7);
                String gender = rs.getString(8);
                String email = rs.getString(9);
                String password = rs.getString(10);
                int branch_id = rs.getInt(11);
                Tutor t = new Tutor(id, nric, fullname, phone, address, image_url, birth_date, gender, email, password, branch_id);
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
            if(num != 0){
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    
    public Tutor retrieveTutorByEmail(String email){
        Tutor tutor = null;
        String sql = "select * from tutor where email = ?";
        System.out.println(sql);
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt(1);
                String nric = rs.getString(2);
                String fullname = rs.getString(3);
                int phone = rs.getInt(4);
                String address = rs.getString(5);
                String image_url = rs.getString(6);
                String birth_date = rs.getString(7);
                String gender = rs.getString(8);
                String email1 = rs.getString(9);
                String password = rs.getString(10);
                int branch_id = rs.getInt(11);
                Tutor t = new Tutor(id, nric, fullname, phone, address, image_url, birth_date, gender, email1, password, branch_id);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return tutor;
    }

}
