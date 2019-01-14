package model;

import connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TwilioDAO {

    public static HashMap<String, String> getCredentials() {
        HashMap<String, String> creds = new HashMap<>();
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select account_sid, auth_token from twillio where branch_id = 1");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                creds.put("account_sid",rs.getString(1));
                creds.put("auth_token",rs.getString(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return creds;
    }
}
