<%-- 
    Document   : ResetPassword
    Created on : 20 Jul, 2018, 2:35:27 AM
    Author     : Zang Yu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <h2>Change Password</h2>        
        <form method="post" action="ForgotPasswordServlet">
            <table>                
                <tr>
                    <td>Enter New password :</td>
                    <td><input type="password" name="newpswd"/></td>
                </tr>
                <tr>
                    <td>Enter Conform Password:  </td>
                    <td><input type="password" name="compswd" /></td>
                </tr>
                <tr>
                    <td><input type="submit" value="submit" />
                    <td><input type="reset" value="Cancel" />
                    </td>
                </tr>
            </table>
        </form>      
    </body>
</html>
