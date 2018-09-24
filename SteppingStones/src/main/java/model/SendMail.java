/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {

    public static void sendingEmail(String toEmail, String subject, String text) {
        //Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("teamexi2018@gmail.com", "TeameXiSteppingStones");//Put your email id and password here
            }
        });
        //compose message
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("teamexi2018@gmail.com"));//change accordingly
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(text);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            System.out.println("message sent not successfully");
            e.printStackTrace(System.out);
        }
    }

    public static void sendingEmailUsingSendGrid(String toEmail, String subject, String username, String password) {
        Email from = new Email("teamexi2018@gmail.com");
        Email to = new Email(toEmail);
        Content content = new Content("text/html", "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "\n"
                + "  <meta charset=\"utf-8\">\n"
                + "  <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n"
                + "  <title>Email Confirmation</title>\n"
                + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                + "  <style type=\"text/css\">\n"
                + "  /**\n"
                + "   * Google webfonts. Recommended to include the .woff version for cross-client compatibility.\n"
                + "   */\n"
                + "  @media screen {\n"
                + "    @font-face {\n"
                + "      font-family: 'Source Sans Pro';\n"
                + "      font-style: normal;\n"
                + "      font-weight: 400;\n"
                + "      src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\n"
                + "    }\n"
                + "    @font-face {\n"
                + "      font-family: 'Source Sans Pro';\n"
                + "      font-style: normal;\n"
                + "      font-weight: 700;\n"
                + "      src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\n"
                + "    }\n"
                + "  }\n"
                + "  /**\n"
                + "   * Avoid browser level font resizing.\n"
                + "   * 1. Windows Mobile\n"
                + "   * 2. iOS / OSX\n"
                + "   */\n"
                + "  body,\n"
                + "  table,\n"
                + "  td,\n"
                + "  a {\n"
                + "    -ms-text-size-adjust: 100%; /* 1 */\n"
                + "    -webkit-text-size-adjust: 100%; /* 2 */\n"
                + "  }\n"
                + "  /**\n"
                + "   * Remove extra space added to tables and cells in Outlook.\n"
                + "   */\n"
                + "  table,\n"
                + "  td {\n"
                + "    mso-table-rspace: 0pt;\n"
                + "    mso-table-lspace: 0pt;\n"
                + "  }\n"
                + "  /**\n"
                + "   * Better fluid images in Internet Explorer.\n"
                + "   */\n"
                + "  img {\n"
                + "    -ms-interpolation-mode: bicubic;\n"
                + "  }\n"
                + "  /**\n"
                + "   * Remove blue links for iOS devices.\n"
                + "   */\n"
                + "  a[x-apple-data-detectors] {\n"
                + "    font-family: inherit !important;\n"
                + "    font-size: inherit !important;\n"
                + "    font-weight: inherit !important;\n"
                + "    line-height: inherit !important;\n"
                + "    color: inherit !important;\n"
                + "    text-decoration: none !important;\n"
                + "  }\n"
                + "  /**\n"
                + "   * Fix centering issues in Android 4.4.\n"
                + "   */\n"
                + "  div[style*=\"margin: 16px 0;\"] {\n"
                + "    margin: 0 !important;\n"
                + "  }\n"
                + "  body {\n"
                + "    width: 100% !important;\n"
                + "    height: 100% !important;\n"
                + "    padding: 0 !important;\n"
                + "    margin: 0 !important;\n"
                + "  }\n"
                + "  /**\n"
                + "   * Collapse table borders to avoid space between cells.\n"
                + "   */\n"
                + "  table {\n"
                + "    border-collapse: collapse !important;\n"
                + "  }\n"
                + "  a {\n"
                + "    color: #1a82e2;\n"
                + "  }\n"
                + "  img {\n"
                + "    height: auto;\n"
                + "    line-height: 100%;\n"
                + "    text-decoration: none;\n"
                + "    border: 0;\n"
                + "    outline: none;\n"
                + "  }\n"
                + "  </style>\n"
                + "\n"
                + "</head>\n"
                + "<body style=\"background-color: #e9ecef;\">\n"
                + "\n"
                + "  <!-- start preheader -->\n"
                + "  <div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\n"
                + "    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n"
                + "  </div>\n"
                + "  <!-- end preheader -->\n"
                + "\n"
                + "  <!-- start body -->\n"
                + "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                + "\n"
                + "    <!-- start logo -->\n"
                + "    <tr>\n"
                + "      <td align=\"center\" bgcolor=\"#e9ecef\">\n"
                + "        <!--[if (gte mso 9)|(IE)]>\n"
                + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                + "        <tr>\n"
                + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                + "        <![endif]-->\n"
                + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                + "          <tr>\n"
                + "            <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n"
                + "              <a href=\"https://sendgrid.com\" target=\"_blank\" style=\"display: inline-block;\">\n"
                + "                <img src=\"http://www.steppingstoneslc.com.sg/assets/images/logo/Stepping%20Stones%20Logo%20(Cropped).jpg\" alt=\"Logo\" border=\"0\" width=\"48\" style=\"display: block; width: 48px; max-width: 48px; min-width: 48px;\">\n"
                + "              </a>\n"
                + "            </td>\n"
                + "          </tr>\n"
                + "        </table>\n"
                + "        <!--[if (gte mso 9)|(IE)]>\n"
                + "        </td>\n"
                + "        </tr>\n"
                + "        </table>\n"
                + "        <![endif]-->\n"
                + "      </td>\n"
                + "    </tr>\n"
                + "    <!-- end logo -->\n"
                + "\n"
                + "    <!-- start hero -->\n"
                + "    <tr>\n"
                + "      <td align=\"center\" bgcolor=\"#e9ecef\">\n"
                + "        <!--[if (gte mso 9)|(IE)]>\n"
                + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                + "        <tr>\n"
                + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                + "        <![endif]-->\n"
                + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                + "          <tr>\n"
                + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\n"
                + "              <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Account Created Successfully!</h1>\n"
                + "            </td>\n"
                + "          </tr>\n"
                + "        </table>\n"
                + "        <!--[if (gte mso 9)|(IE)]>\n"
                + "        </td>\n"
                + "        </tr>\n"
                + "        </table>\n"
                + "        <![endif]-->\n"
                + "      </td>\n"
                + "    </tr>\n"
                + "    <!-- end hero -->\n"
                + "\n"
                + "    <!-- start copy block -->\n"
                + "    <tr>\n"
                + "      <td align=\"center\" bgcolor=\"#e9ecef\">\n"
                + "        <!--[if (gte mso 9)|(IE)]>\n"
                + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                + "        <tr>\n"
                + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                + "        <![endif]-->\n"
                + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                + "\n"
                + "          <!-- start copy -->\n"
                + "          <tr>\n"
                + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 10px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n"
                + "              <p style=\"margin: 0;\">Your account at <a href=\"steppingstones.ml\">Stepping Stones Learning Centre</a> has been created successfully with the following details:</p>\n"
                + "              <p style=\"text-align: center;\">Username: "+ username +"</p>\n"
                + "              <p style=\"text-align: center;\">Password: "+ password +"</p>\n"
                + "            </td>\n"
                + "          </tr>\n"
                + "          <!-- end copy -->\n"
                + "\n"
                + "          <!-- start button -->\n"
                + "          <tr>\n"
                + "            <td align=\"left\" bgcolor=\"#ffffff\">\n"
                + "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                + "                <tr>\n"
                + "                  <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 5px;\">\n"
                + "                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n"
                + "                      <tr>\n"
                + "                        <td align=\"center\" bgcolor=\"#1a82e2\" style=\"border-radius: 6px;\">\n"
                + "                          <a href=\"steppingstones.ml/Login.jsp\" target=\"_blank\" style=\"display: inline-block; padding: 16px 26px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 6px;\">Click here to visit Stepping Stones Learning Centre</a>\n"
                + "                        </td>\n"
                + "                      </tr>\n"
                + "                    </table>\n"
                + "                  </td>\n"
                + "                </tr>\n"
                + "              </table>\n"
                + "            </td>\n"
                + "          </tr>\n"
                + "          <!-- end button -->\n"
                + "\n"
                + "          <!-- start copy -->\n"
                + "          <tr>\n"
                + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n"
                + "              <p style=\"margin: 0;\">If that doesn't work, copy and paste the following link in your browser:</p>\n"
                + "              <p style=\"margin: 0;\"><a href=\"steppingstones.ml/Login.jsp\">steppingstones.ml</a></p>\n"
                + "            </td>\n"
                + "          </tr>\n"
                + "          <!-- end copy -->\n"
                + "\n"
                + "          <!-- start copy -->\n"
                + "          <tr>\n"
                + "            <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">\n"
                + "              <p style=\"margin: 0;\">Cheers,<br> Stepping Stones Learning Centre</p>\n"
                + "            </td>\n"
                + "          </tr>\n"
                + "          <!-- end copy -->\n"
                + "\n"
                + "        </table>\n"
                + "        <!--[if (gte mso 9)|(IE)]>\n"
                + "        </td>\n"
                + "        </tr>\n"
                + "        </table>\n"
                + "        <![endif]-->\n"
                + "      </td>\n"
                + "    </tr>\n"
                + "    <!-- end copy block -->\n"
                + "\n"
                + "    <!-- start footer -->\n"
                + "    <tr>\n"
                + "      <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 24px;\">\n"
                + "        <!--[if (gte mso 9)|(IE)]>\n"
                + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n"
                + "        <tr>\n"
                + "        <td align=\"center\" valign=\"top\" width=\"600\">\n"
                + "        <![endif]-->\n"
                + "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n"
                + "\n"
                + "          <!-- start permission -->\n"
                + "          <tr>\n"
                + "            <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n"
                + "              <p style=\"margin: 0;\">You received this email because we received a request for account creation for your account. If you did not create the account, you can safely delete this email.</p>\n"
                + "            </td>\n"
                + "          </tr>\n"
                + "          <!-- end permission -->\n"
                + "\n"
                + "        </table>\n"
                + "        <!--[if (gte mso 9)|(IE)]>\n"
                + "        </td>\n"
                + "        </tr>\n"
                + "        </table>\n"
                + "        <![endif]-->\n"
                + "      </td>\n"
                + "    </tr>\n"
                + "    <!-- end footer -->\n"
                + "\n"
                + "</table>");
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
    }
}
