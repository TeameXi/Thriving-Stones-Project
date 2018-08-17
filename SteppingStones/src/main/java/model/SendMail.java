/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.sendgrid.*;
import java.io.IOException;

public class SendMail {

//    public static void main(String args[]) {
//        sendingEmail("violalwin@gmail.com", "TESTING", "https://google.xom");
//    }

    public static void sendingEmail(String toEmail, String subject, String text) {
        System.out.println("Coming here");
        Email from = new Email("teamexi2018@gmail.com");
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("");

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println(ex);
        }

//        //Get the session object
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");        
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");        
//        props.put("mail.smtp.port", "465");
//        props.put("mail.smtp.socketFactory.fallback", "false");
//        
//        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("teamexi2018@gmail.com","TeameXiSteppingStones");//Put your email id and password here
//            }
//        });
//        //compose message
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("teamexi2018@gmail.com"));//change accordingly
//            message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
//            message.setSubject(subject);
//            message.setText(text);
//            //send message
//            Transport.send(message);
//            System.out.println("message sent successfully");
//        } catch (MessagingException e) {
//             System.out.println("message sent not successfully");
//             e.printStackTrace(System.out);
//        }
    }
}
