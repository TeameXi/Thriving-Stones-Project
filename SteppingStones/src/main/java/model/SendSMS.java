package model;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.HashMap;

public class SendSMS {

    // Find your Account Sid and Auth Token at twilio.com/console
    public static String ACCOUNT_SID = "";
    public static String AUTH_TOKEN = "";

    public static void sendingSMS(String phoneNum, String msg) {

        HashMap<String, String> cred = TwilioDAO.getCredentials();
        ACCOUNT_SID = cred.get("account_sid");
        AUTH_TOKEN = cred.get("auth_token");
        
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(phoneNum), // to
                        new PhoneNumber("+12393605275"), // from
                        msg)
                .create();

        System.out.println(message.getSid());
    }
}
