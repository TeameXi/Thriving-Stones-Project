package model;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SendSMS {

    // Find your Account Sid and Auth Token at twilio.com/console
    public static String ACCOUNT_SID = "";
    public static String AUTH_TOKEN = "";

    public static void sendingSMS(String phoneNum, String msg) {

        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select account_sid, auth_token from twillio where branch_id = 1");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ACCOUNT_SID = rs.getString(1);
                AUTH_TOKEN = rs.getString(2);
            }
        } catch (SQLException ex) {
            System.out.println("error in retrieveStudentIDWithPhone sql");
        }

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(phoneNum), // to
                        new PhoneNumber("+12393605275"), // from
                        msg)
                .create();

        System.out.println(message.getSid());
    }
}
