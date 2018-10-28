package model;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSMS {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC993664c0999f367b68ed8db7a12b324a";
    public static final String AUTH_TOKEN = "b1c82983f5487c138d300f7e213b8043";

    public static void sendingSMS(String phoneNum, String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        
        Message message = Message
                .creator(new PhoneNumber(phoneNum), // to
                        new PhoneNumber("+2728081769"), // from
                        msg)
                .create();

        System.out.println(message.getSid());
    }
}