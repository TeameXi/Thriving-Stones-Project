/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
/**
 *
 * @author DEYU
 */
public class SendSMS {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC2741a28050e3c17e2acf25055c1240f7";
    public static final String AUTH_TOKEN = "86ab2172ef12e290d763ae8e9f3d08f8";

    public static void sendingSMS(String phoneNum, String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(phoneNum), // to
                        new PhoneNumber("+12393605275"), // from
                        msg)
                .create();

        System.out.println(message.getSid());
    }
}
