package model;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSMS {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC9921a3429a69cac5e2fb1eac3288f343";
    public static final String AUTH_TOKEN = "b9dd85c3c798d7005b56eb21dfa3775a";

    public static void sendingSMS(String phoneNum, String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        
        Message message = Message
                .creator(new PhoneNumber(phoneNum), // to
                        new PhoneNumber("+18484045071"), // from
                        msg)
                .create();

        System.out.println(message.getSid());
    }
}
